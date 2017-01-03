/* Copyright (c) 2006 Seva Safris
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

package org.safris.ant.task.xsb;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicElement;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.UnknownElement;
import org.safris.commons.util.Translator;
import org.safris.commons.xml.NamespaceURI;
import org.safris.commons.xml.dom.DOMParsers;
import org.safris.xsb.compiler.processor.GeneratorContext;
import org.safris.xsb.compiler.processor.reference.SchemaReference;
import org.safris.xsb.generator.Generator;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeneratorTask extends Task implements DynamicElement {
  public static void main(final String[] args) throws BuildException {
    // FIXME: Finish implementing this!
    if (args.length != 1)
      trapPrintUsage();

    final File buildFile = new File(args[0]);
    if (!buildFile.exists())
      throw new BuildException("File does not exist: " + buildFile.getAbsolutePath());

    final Collection<String> targets = parse(buildFile);
    if (targets.size() == 0)
      return;

    for (String target : targets) {
      AntLauncher.main(new String[] {
        "-f",
        buildFile.getAbsolutePath(),
        target
      });
    }
  }

  private static Collection<String> parse(File buildFile) throws BuildException {
    Document document = null;
    try {
      final DocumentBuilder documentBuilder = DOMParsers.newDocumentBuilder();
      document = documentBuilder.parse(buildFile);
    }
    catch (Exception e) {
      throw new BuildException(e);
    }

    String taskName = null;
    final NodeList taskdefs = document.getElementsByTagName("taskdef");
    for (int i = 0; i < taskdefs.getLength(); i++) {
      final Node node = taskdefs.item(i);
      final NamedNodeMap attributes = node.getAttributes();
      if (attributes == null)
        continue;

      String tempName = null;
      boolean classNameFound = false;
      for (int j = 0; j < attributes.getLength(); j++) {
        final Node attribute = attributes.item(j);
        if (tempName == null && "name".equals(attribute.getNodeName()))
          tempName = attribute.getNodeValue();
        else if (!classNameFound && "classname".equals(attribute.getNodeName()) && GeneratorTask.class.getName().equals(attribute.getNodeValue()))
          classNameFound = true;
      }

      if (!classNameFound || tempName != null) {
        taskName = tempName;
        break;
      }
    }

    if (taskName == null)
      throw new BuildException("There is no taskdef that defines the GeneratorTask to a task name.");

    final Collection<String> targets = new ArrayList<String>();
    final NodeList bindings = document.getElementsByTagName(taskName);
    for (int i = 0; i < bindings.getLength(); i++) {
      Node node = bindings.item(i);
      Node parent = node.getParentNode();
      while (parent != null && !"target".equals(parent.getNodeName())) {
        node = parent;
        parent = node.getParentNode();
      }

      if (parent == null)
        throw new BuildException("Error parsing build.xml. Cant determine which target uses binding task.");

      String target = null;
      final NamedNodeMap attributes = parent.getAttributes();
      for (int j = 0; j < attributes.getLength(); j++) {
        final Node attribute = attributes.item(j);
        if ("name".equals(attribute.getNodeName())) {
          target = attribute.getNodeValue();
          break;
        }
      }

      if (target != null)
        targets.add(target);
    }

    return targets;
  }

  private static void trapPrintUsage() {
    System.err.println("Usage: GeneratorTask <build.xml>");
    System.exit(1);
  }

  private Manifest manifest = null;

  @Override
  public Object createDynamicElement(String p1) throws BuildException {
    if (!"manifest".equals(p1))
      throw new BuildException(getClass().getSimpleName() + " doesn't support the nested \"" + p1 + "\" element.");

    final Enumeration<?> enumeration = this.getWrapper().getChildren();
    while (enumeration.hasMoreElements()) {
      final RuntimeConfigurable element = (RuntimeConfigurable)enumeration.nextElement();
      final Object proxy = element.getProxy();
      if (proxy instanceof UnknownElement) {
        final UnknownElement unknownElement = (UnknownElement)proxy;
        if ("manifest".equals(unknownElement.getTaskName()) && !"http://maven.safris.org/common/manifest.xsd".equals(unknownElement.getNamespace()))
          throw new BuildException("Expected http://maven.safris.org/common/manifest.xsd namespace, but got: " + unknownElement.getNamespace());
      }
    }

    return manifest = new Manifest();
  }

  @Override
  public void execute() throws BuildException {
    final Translator<String> translator = new AntPropertyTranslator(getProject());
    final String buildLocation = getLocation().getFileName();
    final File buildFile = new File(buildLocation);
    if (!buildFile.exists())
      throw new BuildException("Mysteriously cannot find the buildfile: " + buildLocation);

    String destDir = null;
    Generator generator = null;
    if (manifest.getLink() != null) {
      String href = null;
      Enumeration<?> tags = getWrapper().getChildren();
TOP:
      while (tags.hasMoreElements()) {
        final Object tag = tags.nextElement();
        if (!(tag instanceof RuntimeConfigurable))
          continue;

        final RuntimeConfigurable manifestRuntime = (RuntimeConfigurable)tag;
        if (!"manifest".equals(manifestRuntime.getElementTag()))
          continue;

        final Enumeration<?> children = manifestRuntime.getChildren();
        while (children.hasMoreElements()) {
          final Object child = children.nextElement();
          if (!(child instanceof RuntimeConfigurable))
            continue;

          final RuntimeConfigurable linkRuntime = (RuntimeConfigurable)child;
          if (!"link".equals(linkRuntime.getElementTag()))
            continue;

          final Hashtable<String,Object> attributes = linkRuntime.getAttributeMap();
          href = (String)attributes.get("http://www.w3.org/1999/xlink:xlink:href");
          break TOP;
        }
      }

      href = translator.translate(href);
      final File hrefFile = new File(href);
      Document document = null;
      try {
        final DocumentBuilder documentBuilder = DOMParsers.newDocumentBuilder();
        document = documentBuilder.parse(hrefFile);
      }
      catch (Exception e) {
        throw new BuildException(e.getMessage(), e);
      }

      generator = new Generator(buildFile.getParentFile(), document.getDocumentElement(), hrefFile.lastModified(), translator, null);
    }
    else {
      if (manifest.getDestdir() == null || (destDir = manifest.getDestdir().getText()) == null)
        throw new BuildException("link element not provided, so destdir element must be speficied");

      if (manifest.getSchemas() == null)
        throw new BuildException("link element not provided, schemas element must be speficied");

      final File destDirFile = new File(translator.translate(destDir));

      final Collection<Manifest.Schemas.Schema> schemas;
      if ((schemas = manifest.getSchemas().getSchemas()) == null || manifest.getSchemas().getSchemas().size() == 0) {
        getProject().log(this, "No schemas defined for binding.", Project.MSG_ERR);
        return;
      }

      final Collection<SchemaReference> schemaReferences = new ArrayList<SchemaReference>(schemas.size());
      for (final Manifest.Schemas.Schema schema : schemas) {
        final String deref = translator.translate(schema.getText());
        if (deref != null)
          schemaReferences.add(new SchemaReference(buildFile.getParent(), deref, false));
      }

      final Set<NamespaceURI> excludes = new HashSet<NamespaceURI>();
      if (manifest.getExcludes() != null) {
        for (final Manifest.Excludes.Exclude exclude : manifest.getExcludes().getExcludes()) {
          excludes.add(NamespaceURI.getInstance(exclude.getText()));
        }
      }

      final GeneratorContext generatorContext = new GeneratorContext(buildFile.lastModified(), destDirFile, manifest.getDestdir().getExplodeJars(), manifest.getDestdir().getOverwrite());
      generator = new Generator(generatorContext, schemaReferences, excludes, null);
    }

    generator.generate();
  }

  public class Manifest {
    private Link link = null;
    private Destdir destdir = null;
    private Schemas schemas = null;
    private Excludes excludes = null;

    public Link getLink() {
      return link;
    }

    public Destdir getDestdir() {
      return destdir;
    }

    public Schemas getSchemas() {
      return schemas;
    }

    public Excludes getExcludes() {
      return excludes;
    }

    public Link createLink() {
      return link = new Link();
    }

    public Destdir createDestdir() {
      return destdir = new Destdir();
    }

    public Schemas createSchemas() {
      return schemas = new Schemas();
    }

    public class Link {
      private String href = "";

      public void setHreffinal (String href) {
        this.href = href;
      }

      public String getHref() {
        return href;
      }
    }

    public class Destdir {
      private String text = "";
      private boolean explodeJars = false;
      private boolean overwrite = false;

      public void setExplodeJars(final boolean explodeJars) {
        this.explodeJars = explodeJars;
      }

      public Boolean getExplodeJars() {
        return explodeJars;
      }

      public void setOverwrite(final boolean overwrite) {
        this.overwrite = overwrite;
      }

      public Boolean getOverwrite() {
        return overwrite;
      }

      public void addText(final String text) {
        this.text += text;
      }

      public String getText() {
        return text;
      }
    }

    public class Schemas {
      private Collection<Schema> schemas = null;

      public Collection<Schema> getSchemas() {
        return schemas;
      }

      public Schema createSchema() {
        if (schemas == null)
          schemas = new ArrayList<Schema>();

        final Schema schema = new Schema();
        schemas.add(schema);
        return schema;
      }

      public class Schema {
        private String text = "";

        public void addText(final String text) {
          this.text += text;
        }

        public String getText() {
          return text;
        }
      }
    }

    public class Excludes {
      private Collection<Exclude> excludes = null;

      public Collection<Exclude> getExcludes() {
        return excludes;
      }

      public Exclude createSchema() {
        if (excludes == null)
          excludes = new ArrayList<Exclude>();

        final Exclude exclude = new Exclude();
        excludes.add(exclude);
        return exclude;
      }

      public class Exclude {
        private String text = "";

        public void addText(final String text) {
          this.text += text;
        }

        public String getText() {
          return text;
        }
      }
    }
  }
}