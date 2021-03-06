/* Copyright (c) 2008 lib4j
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.libx4j.xsb.generator.processor.bundle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.lib4j.io.Files;
import org.lib4j.jci.CompilationException;
import org.lib4j.jci.JavaCompiler;
import org.lib4j.lang.ClassLoaders;
import org.lib4j.lang.Paths;
import org.lib4j.lang.Resources;
import org.lib4j.net.URLs;
import org.lib4j.pipeline.PipelineDirectory;
import org.lib4j.pipeline.PipelineEntity;
import org.lib4j.pipeline.PipelineProcessor;
import org.lib4j.util.jar.Jar;
import org.lib4j.xml.NamespaceBinding;
import org.lib4j.xml.NamespaceURI;
import org.lib4j.xml.dom.DOMParsers;
import org.lib4j.xml.dom.DOMStyle;
import org.lib4j.xml.dom.DOMs;
import org.libx4j.xsb.compiler.processor.GeneratorContext;
import org.libx4j.xsb.compiler.processor.composite.SchemaComposite;
import org.libx4j.xsb.compiler.processor.composite.SchemaModelComposite;
import org.libx4j.xsb.runtime.Binding;
import org.libx4j.xsb.runtime.BindingError;
import org.libx4j.xsb.runtime.CompilerFailureException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class BundleProcessor implements PipelineEntity, PipelineProcessor<GeneratorContext,SchemaComposite,Bundle> {
  private static void compile(final File destDir, final Set<File> sourcePath) throws CompilationException, IOException, URISyntaxException {
    final Collection<File> classpath = sourcePath != null ? sourcePath : new ArrayList<File>(2);
    final File bindingLocationBase = Resources.getLocationBase(Binding.class);
    if (bindingLocationBase != null)
      classpath.add(bindingLocationBase);

    // FIXME: Make this more explicit.
    final File namespaceBindingBase = Resources.getLocationBase(NamespaceBinding.class);
    if (namespaceBindingBase != null)
      classpath.add(namespaceBindingBase);

    for (final URL url : ClassLoaders.getClassPath())
      classpath.add(new File(url.toURI()));

    new JavaCompiler(destDir, classpath).compile(destDir);
  }

  private static Collection<File> jar(final File destDir, final Collection<SchemaComposite> schemaComposites, final Set<NamespaceURI> includes, final Set<NamespaceURI> excludes) throws IOException, SAXException {
    final Set<NamespaceURI> namespaceURIsAdded = new HashSet<NamespaceURI>();
    final Collection<File> jarFiles = new HashSet<File>();

    for (final SchemaComposite schemaComposite : schemaComposites) {
      final SchemaModelComposite schemaModelComposite = (SchemaModelComposite)schemaComposite;
      final NamespaceURI namespaceURI = schemaModelComposite.getSchemaDocument().getSchemaReference().getNamespaceURI();
      if ((includes == null || includes.contains(namespaceURI)) && (excludes == null || !excludes.contains(namespaceURI))) {
        final String pkg = namespaceURI.getPackage();
        final File jarFile = new File(destDir, pkg + ".jar");
        if (jarFile.exists())
          if (!jarFile.delete())
            throw new BindingError("Unable to delete the existing jar: " + jarFile.getAbsolutePath());

        final Jar jar = new Jar(jarFile);
        jarFiles.add(jarFile);

        final String packagePath = pkg.replace('.', '/');
        if (!namespaceURIsAdded.contains(namespaceURI)) {
          namespaceURIsAdded.add(namespaceURI);

          final Collection<File> list = Files.listAll(new File(destDir, packagePath));
          if (list != null)
            for (final File file : list)
              if (!file.isDirectory() && (file.getName().endsWith(".java") || file.getName().endsWith(".class")))
                jar.addEntry(Files.relativePath(destDir.getAbsoluteFile(), file.getAbsoluteFile()), Files.getBytes(file));
        }

        final URL url = schemaModelComposite.getSchemaDocument().getSchemaReference().getURL();
        final String xsdName = packagePath + '/' + pkg;
        if (!schemaModelComposite.getSchemaDocument().getSchemaReference().isInclude())
          addXSDs(url, xsdName + ".xsd", jar, destDir, 0);

        jar.close();
      }
    }

    return jarFiles;
  }

  private static void addXSDs(final URL url, final String filePath, final Jar jar, final File destDir, int includeCount) throws IOException, SAXException {
    final String baseDir = Paths.getParent(filePath);
    String relativeRootPath = null;
    final Element element = DOMParsers.newDocumentBuilder().parse(url.openStream()).getDocumentElement();
    final NodeList children = element.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      final Node node = children.item(i);
      if (!"http://www.w3.org/2001/XMLSchema".equals(node.getNamespaceURI()))
        continue;

      if ("import".equals(node.getLocalName())) {
        final NamedNodeMap attributes = node.getAttributes();
        Node namespace = null;
        Node schemaLocation = null;
        for (int j = 0; j < attributes.getLength(); j++) {
          final Node attribute = attributes.item(j);
          if ("namespace".equals(attribute.getLocalName()))
            namespace = attribute;
          else if ("schemaLocation".equals(attribute.getLocalName()))
            schemaLocation = attribute;

          if (namespace != null && schemaLocation != null) {
            final String packageName = NamespaceBinding.getPackageFromNamespace(namespace.getNodeValue());
            final String packagePath = packageName.replace('.', '/');
            final String schemaPath;
            if (baseDir.equals(packagePath)) {
              schemaPath = baseDir;
            }
            else {
              if (relativeRootPath == null) {
                relativeRootPath = "";
                for (int k = 0; k != -1; k = baseDir.indexOf('/', k + 1))
                  relativeRootPath += "../";
              }

              schemaPath = Paths.canonicalize(relativeRootPath + packagePath);
            }

            schemaLocation.setNodeValue(schemaPath + "/" + packageName + ".xsd");
            namespace = null;
            schemaLocation = null;
            break;
          }
        }
      }
      else if ("include".equals(node.getLocalName())) {
        final NamedNodeMap attributes = node.getAttributes();
        for (int j = 0; j < attributes.getLength(); j++) {
          final Node attribute = attributes.item(j);
          if ("schemaLocation".equals(attribute.getLocalName())) {
            final String schemaLocation = attribute.getNodeValue();
            final URL includeURL = URLs.isAbsolute(schemaLocation) ? new URL(schemaLocation) : Paths.isAbsolute(schemaLocation) ? URLs.makeUrlFromPath(schemaLocation) : URLs.makeUrlFromPath(URLs.getParent(url), schemaLocation);
            final String includePath = filePath.replace(".xsd", "-" + ++includeCount + ".xsd");
            attribute.setNodeValue(Paths.getName(includePath));
            addXSDs(includeURL, includePath, jar, destDir, includeCount);
          }
        }
      }
    }

    final byte[] bytes = DOMs.domToString(element, DOMStyle.INDENT).getBytes();
    jar.addEntry(filePath, bytes);

    final File tempFile = new File(destDir, filePath);
    Files.writeFile(tempFile, bytes);
  }

  private final Set<File> sourcePath;

  public BundleProcessor(final Set<File> sourcePath) {
    this.sourcePath = sourcePath;
  }

  @Override
  public Collection<Bundle> process(final GeneratorContext pipelineContext, final Collection<SchemaComposite> documents, final PipelineDirectory<GeneratorContext,SchemaComposite,Bundle> directory) {
    try {
      if (pipelineContext.getCompile())
        BundleProcessor.compile(pipelineContext.getDestdir(), sourcePath);

      final Collection<Bundle> bundles = new ArrayList<Bundle>();
      if (pipelineContext.getPackage()) {
        final Collection<File> jarFiles = BundleProcessor.jar(pipelineContext.getDestdir(), documents, pipelineContext.getIncludes(), pipelineContext.getExcludes());
        for (final File jarFile : jarFiles)
          bundles.add(new Bundle(jarFile));
      }
      else {
        for (final SchemaComposite schemaComposite : documents) {
          final SchemaModelComposite schemaModelComposite = (SchemaModelComposite)schemaComposite;
          final NamespaceURI namespaceURI = schemaModelComposite.getSchemaDocument().getSchemaReference().getNamespaceURI();
          if ((pipelineContext.getIncludes() == null || pipelineContext.getIncludes().contains(namespaceURI)) && (pipelineContext.getExcludes() == null || !pipelineContext.getExcludes().contains(namespaceURI)))
            bundles.add(new Bundle(new File(pipelineContext.getDestdir(), namespaceURI.getPackage())));
        }
      }

      return bundles;
    }
    catch (final CompilationException | IOException | SAXException | URISyntaxException e) {
      throw new CompilerFailureException(e);
    }
  }
}