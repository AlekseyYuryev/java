/*  Copyright Safris Software 2008
 *
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.xml.toolkit.processor.bundle;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.safris.commons.io.Files;
import org.safris.commons.io.Streams;
import org.safris.commons.jci.JavaCompiler;
import org.safris.commons.lang.ClassLoaders;
import org.safris.commons.lang.Resources;
import org.safris.commons.net.URLs;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.util.jar.Jar;
import org.safris.commons.xml.NamespaceBinding;
import org.safris.commons.xml.NamespaceURI;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingError;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.composite.SchemaModelComposite;
import org.safris.xml.toolkit.processor.bundle.Bundle;

public final class BundleProcessor implements PipelineEntity<Bundle>, PipelineProcessor<GeneratorContext,SchemaComposite,Bundle> {
  private static void compile(File destDir) throws Throwable {
    final Collection<File> javaFiles = Files.listAll(destDir);
    final Collection<File> javaSources = new ArrayList<File>();
    for (File javaFile : javaFiles) {
      if (javaFile.isDirectory() || !javaFile.getName().endsWith(".java"))
        continue;

      javaSources.add(javaFile);
    }

    final Collection<File> classpath = new ArrayList<File>(2);
    final File bindingLocationBase = Resources.getLocationBase(Binding.class);
    if (bindingLocationBase != null)
      classpath.add(bindingLocationBase);

    // FIXME: Make this more explicit.
    final File namespaceBindingBase = Resources.getLocationBase(NamespaceBinding.class);
    if (namespaceBindingBase != null)
      classpath.add(namespaceBindingBase);


    for (final URL url : ClassLoaders.getClassPath())
      classpath.add(new File(url.toURI()));

    new JavaCompiler(destDir, classpath).compile(javaSources);
  }

  private static Collection<File> jar(File destDir, Collection<SchemaComposite> schemaComposites) throws Exception {
    final Map<String,Jar> packageToJar = new HashMap<String,Jar>();
    final Map<NamespaceURI,SchemaModelComposite> namespaceToSchemaComposite = new HashMap<NamespaceURI,SchemaModelComposite>();
    final Collection<String> packagePaths = new ArrayList<String>();

    for (final SchemaComposite schemaComposite : schemaComposites) {
      final SchemaModelComposite schemaModelComposite = (SchemaModelComposite)schemaComposite;
      // The order of the schemas in schemaDocuments is specific! With it we know
      // which schemas originate the targetNamespace. This is important because
      // included schemas have had their targetNamespace changed to the particular
      // namespace for which they are included.
      if (namespaceToSchemaComposite.containsKey(schemaModelComposite.getSchemaDocument().getSchemaReference().getNamespaceURI()))
        continue;

      namespaceToSchemaComposite.put(schemaModelComposite.getSchemaDocument().getSchemaReference().getNamespaceURI(), schemaModelComposite);
      packagePaths.add(schemaModelComposite.getSchemaDocument().getSchemaReference().getNamespaceURI().getPackageName().toString().replace('.', File.separatorChar));
    }

    final Collection<File> files = new ArrayList<File>();
    for (final String packagePath : packagePaths) {
      final Collection<File> list = Files.listAll(new File(destDir, packagePath));
      if (list != null)
        files.addAll(list);
    }

    final Collection<File> jars = new HashSet<File>();
    for (File file : files) {
      if (file.isDirectory() || (!file.getName().endsWith(".java") && !file.getName().endsWith(".class")))
        continue;

      final String relativePath = Files.relativePath(destDir.getAbsoluteFile(), file.getAbsoluteFile());
      final String pkgDir = relativePath.substring(0, relativePath.lastIndexOf(File.separator));
      final String pkg = pkgDir.replace(File.separatorChar, '.');

      Jar jar = packageToJar.get(pkg);
      if (jar == null) {
        SchemaModelComposite schemaComposite = null;
        NamespaceURI namespaceURI = null;
        for (final Map.Entry<NamespaceURI,SchemaModelComposite> entry : namespaceToSchemaComposite.entrySet()) {
          if (pkg.equals(entry.getKey().getPackageName().toString())) {
            namespaceURI = entry.getKey();
            schemaComposite = entry.getValue();
            break;
          }
        }

        if (schemaComposite == null)
          throw new CompilerError("Cant resolve url by its namespaceURI: {" + namespaceURI + "}. This really shouldn't happen!");

        final URL url = schemaComposite.getSchemaDocument().getSchemaReference().getURL();

        final String packageName = Files.relativePath(destDir, file.getParentFile()).replace(File.separatorChar, '.');
        final String jarName = packageName + ".jar";
        final String xsdName = pkgDir + File.separator + packageName + ".xsd";

        final File jarFile = new File(destDir, jarName);
        if (jarFile.exists())
          if (!jarFile.delete())
            throw new BindingError("Unable to delete the existing jar: " + jarFile.getAbsolutePath());

        jars.add(jarFile);

        jar = new Jar(jarFile);
        packageToJar.put(pkg, jar);

        // first we include the schema that was used to create the source
        byte[] bytes = Streams.getBytes(url.openStream());

        jar.addEntry(xsdName, bytes);

        // Write the schema to disk
        Files.writeFile(new File(destDir, xsdName), bytes);

        final Collection<URL> includes = schemaComposite.getSchemaDocument().getIncludes();
        if (includes != null) {
          for (final URL include : includes) {
            final String includeXsdName = pkgDir + File.separator + URLs.getName(include);
            bytes = Streams.getBytes(include.openStream());
            jar.addEntry(includeXsdName, bytes);

            // Write the schema to disk too
            Files.writeFile(new File(destDir, includeXsdName), bytes);
          }
        }
      }

      final String fileName = Files.relativePath(destDir.getAbsoluteFile(), file.getAbsoluteFile());
      final byte[] bytes = Files.getBytes(file);
      jar.addEntry(fileName, bytes);
    }

    // Finalize the jar files.
    for (final Jar jar : packageToJar.values())
      jar.close();

    return jars;
  }

  protected BundleProcessor() {
  }

  public Collection<Bundle> process(GeneratorContext pipelineContext, Collection<SchemaComposite> documents, PipelineDirectory<GeneratorContext,SchemaComposite,Bundle> directory) {
    try {
      BundleProcessor.compile(pipelineContext.getDestDir());
      final Collection<File> jarFiles = BundleProcessor.jar(pipelineContext.getDestDir(), documents);
      final Collection<Bundle> bundles = new ArrayList<Bundle>(jarFiles.size());
      for (File jarFile : jarFiles)
        bundles.add(new Bundle(jarFile));

      // If we dont care about the exploded files,
      // then delete all of the files only leaving the jars.
      if (!pipelineContext.getExplodeJars()) {
        final FileFilter jarFilter = new FileFilter()
        {
          public boolean accept(File pathname) {
            return !pathname.getName().endsWith(".jar");
          }
        };

        final Collection<File> files = Files.listAll(pipelineContext.getDestDir(), jarFilter);
        for (File file : files)
          Files.deleteAllOnExit(file);
      }

      return bundles;
    }
    catch (Throwable e) {
      throw new CompilerError(e);
    }
  }
}
