/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.maven.plugin.xml.binding;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.safris.commons.lang.Paths;
import org.safris.commons.net.URLs;
import org.safris.commons.util.zip.Zips;
import org.safris.commons.xml.dom.DOMParsers;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.safris.xml.toolkit.binding.Generator;
import org.safris.xml.toolkit.binding.PropertyResolver;
import org.safris.xml.toolkit.processor.bundle.Bundle;
import org.w3.x2001.xmlschema.$xs_boolean;
import org.w3c.dom.Document;

/**
 * @goal generate
 */
public class GeneratorMojo extends AbstractMojo {
    public static void main(String[] args) throws MojoFailureException {
        if (args.length != 1)
            usage();

        final File pomFile = new File(args[0]);
        if (!pomFile.exists())
            throw new MojoFailureException("File does not exist: " + pomFile.getAbsolutePath());

        MavenLauncher.main(new String[]{"-e", "-f", pomFile.getAbsolutePath(), "org.safris.maven.plugin.xml:binding:generate"});
    }

    private static void usage() {
        System.err.println("Usage: GeneratorMojo <pom.xml>");
        System.exit(1);
    }

    private static final FileFilter classesFilter = new FileFilter()
    {
        public boolean accept(File pathname) {
            final String name = pathname.getName();
            return name != null && !name.endsWith(".class") && !name.endsWith(".java");
        }
    };

    /**
     * @parameter default-value="${project}"
     * @required
     */
    private MavenProject project = null;

    /**
     * @parameter default-value="${maven.test.skip}"
     */
    private Boolean mavenTestSkip = null;

    /**
     * @parameter default-value="${basedir}"
     */
    private String basedir = null;

    /**
     * @parameter
     */
    private Manifest manifest;

    private PropertyResolver resolver = null;

    public void execute() throws MojoExecutionException, MojoFailureException {
        String href = null;
        boolean explodeJars = false;
        boolean overwrite = false;
        if (project == null)
            throw new MojoFailureException("project == null");

        final Build build = project.getBuild();
        if (build != null && build.getPlugins() != null) {
            resolver = new MavenPropertyResolver(project);
            for (Plugin plugin : (List<Plugin>)build.getPlugins()) {
                if (!"binding".equals(plugin.getArtifactId()))
                    continue;

                plugin.flushExecutionMap();
                final Xpp3Dom configuration = (Xpp3Dom)plugin.getConfiguration();
                if (configuration == null) {
                    getLog().info("No configuration specified.");
                    continue;
                }

                for (int i = 0; i < configuration.getChildCount(); i++) {
                    final Xpp3Dom bindings = configuration.getChild(i);
                    if ("manifest".equals(bindings.getName())) {
                        for (int j = 0; j < bindings.getChildCount(); j++) {
                            final Xpp3Dom link = bindings.getChild(j);
                            if ("link".equals(link.getName())) {
                                String attributeName = null;
                                final String[] names = link.getAttributeNames();
                                for (String name : names) {
                                    if (name.endsWith("href")) {
                                        attributeName = name;
                                        break;
                                    }
                                }

                                if (attributeName == null)
                                    throw new MojoFailureException("There is an error in your manifest xml. Please consult the manifest.xsd for proper usage.");

                                href = link.getAttribute(attributeName);
                                break;
                            }
                            else if ("destdir".equals(link.getName())) {
                                String explodeJarsName = null;
                                String overwriteName = null;
                                final String[] names = link.getAttributeNames();
                                for (String name : names) {
                                    if (name.endsWith("explodeJars"))
                                        explodeJarsName = name;
                                    else if (name.endsWith("overwrite"))
                                        overwriteName = name;
                                }

                                if (explodeJarsName != null)
                                    explodeJars = $xs_boolean.parseBoolean(link.getAttribute(explodeJarsName));

                                if (overwriteName != null)
                                    overwrite = $xs_boolean.parseBoolean(link.getAttribute(explodeJarsName));

                                break;
                            }
                        }

                        break;
                    }
                }

                final Object defaultExecution = plugin.getExecutionsAsMap().get("default");
                if (defaultExecution == null || !(defaultExecution instanceof PluginExecution))
                    break;

                final String phase = ((PluginExecution)defaultExecution).getPhase();
                if (phase != null && !phase.contains("test"))
                    break;

                if (mavenTestSkip == null || !mavenTestSkip)
                    break;

                return;
            }
        }

        if (href != null) {
            final File hrefFile;
            if (Paths.isAbsolute(href))
                hrefFile = new File(href);
            else if (basedir != null)
                hrefFile = new File(basedir, href);
            else
                hrefFile = new File(href);

            if (!hrefFile.exists())
                throw new MojoFailureException("href=\"" + hrefFile.getAbsolutePath() + "\" does not exist.");
            else if (!hrefFile.isFile())
                throw new MojoFailureException("href=\"" + hrefFile.getAbsolutePath() + "\" is not a file.");

            Document document = null;
            try {
                document = DOMParsers.newDocumentBuilder().parse(hrefFile);
            }
            catch (Exception e) {
                throw new MojoExecutionException(e.getMessage(), e);
            }

            final Generator generator = new Generator(new File(basedir), document.getDocumentElement(), hrefFile.lastModified(), resolver);
            final Collection<Bundle> bundles = generator.generate();
            addCompileSourceRoot(generator.getGeneratorContext().getDestDir().getAbsolutePath(), bundles);
            return;
        }

        if (manifest == null || manifest.getDestdir() == null || manifest.getSchemas() == null)
            return;

        final String destDir = manifest.getDestdir();
        final Collection<SchemaReference> generatorBindings = new ArrayList<SchemaReference>(7);

        for (String schema : manifest.getSchemas()) {
            if (URLs.isAbsolute(schema))
                generatorBindings.add(new SchemaReference(schema));
            else
                generatorBindings.add(new SchemaReference(project.getFile().getParentFile().getAbsolutePath(), schema));
        }

        if (destDir == null || destDir.length() == 0)
            throw new MojoFailureException("<destdir> is null or empty!");

        if (generatorBindings.size() == 0)
            return;

        final File destDirFile = new File(destDir);
        final Generator generator = new Generator(new GeneratorContext(project.getFile().lastModified(), destDirFile, explodeJars, overwrite), generatorBindings);
        final Collection<Bundle> bundles = generator.generate();
        addCompileSourceRoot(generator.getGeneratorContext().getDestDir().getAbsolutePath(), bundles);
    }

    private void addCompileSourceRoot(String path, Collection<Bundle> bundles) throws MojoExecutionException {
        if (bundles == null || path == null || project == null || !(project instanceof MavenProject))
            return;

        try {
            for (Bundle bundle : bundles) {
                for (String element : (List<String>)project.getTestClasspathElements()) {
                    final File elementFile = new File(element);
                    if (!elementFile.isFile())
                        Zips.unzip(bundle.getFile(), classesFilter, elementFile);
                }

                for (String element : (List<String>)project.getCompileClasspathElements()) {
                    final File elementFile = new File(element);
                    if (!elementFile.isFile())
                        Zips.unzip(bundle.getFile(), classesFilter, elementFile);
                }
            }
        }
        catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

        // add to both compile and test-compile classpaths so that the generated classes
        // can be used for the main and test source.
        project.addTestCompileSourceRoot(path);
        project.addCompileSourceRoot(path);
    }
}
