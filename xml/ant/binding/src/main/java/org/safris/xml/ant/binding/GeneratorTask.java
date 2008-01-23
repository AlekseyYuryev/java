package org.safris.xml.ant.binding;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import javax.xml.parsers.DocumentBuilder;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicElement;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.UnknownElement;
import org.safris.commons.util.xml.DOMParsers;
import org.safris.commons.util.xml.SchemaReference;
import org.safris.xml.generator.module.phase.BindingContext;
import org.safris.xml.toolkit.binding.Generator;
import org.safris.xml.toolkit.binding.PropertyResolver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class GeneratorTask extends Task implements DynamicElement
{
	public static void main(String[] args) throws BuildException
	{
		// FIXME: Finish implementing this!
		if(args.length != 1)
			usage();

		final File buildFile = new File(args[0]);
		if(!buildFile.exists())
			throw new BuildException("File does not exist: " + buildFile.getAbsolutePath());

		AntLauncher.main(new String[]
		{
			"-f",
			buildFile.getAbsolutePath()
		});
	}

	private static void parse(File buildFile) throws BuildException
	{
		Document document = null;
		try
		{
			final DocumentBuilder documentBuilder = DOMParsers.newDocumentBuilder();
			document = documentBuilder.parse(buildFile);
		}
		catch(Exception e)
		{
			throw new BuildException(e);
		}

		NodeList taskdefs = document.getElementsByTagName("taskdef");
	}

	private static void usage()
	{
		System.err.println("Usage: GeneratorTask <build.xml>");
		System.exit(1);
	}

	private Manifest manifest = null;

	public Object createDynamicElement(String p1) throws BuildException
	{
		if(!"manifest".equals(p1))
			throw new BuildException(getClass().getSimpleName() + " doesn't support the nested \"" + p1 + "\" element.");

		final Enumeration enumeration = this.getWrapper().getChildren();
		while(enumeration.hasMoreElements())
		{
			final RuntimeConfigurable element = (RuntimeConfigurable)enumeration.nextElement();
			final Object proxy = element.getProxy();
			if(proxy instanceof UnknownElement)
			{
				final UnknownElement unknownElement = (UnknownElement)proxy;
				if("manifest".equals(unknownElement.getTaskName()) && !"http://xml.safris.org/toolkit/application/binding/manifest.xsd".equals(unknownElement.getNamespace()))
					throw new BuildException("Expected http://xml.safris.org/toolkit/application/binding/manifest.xsd namespace, but got: " + unknownElement.getNamespace());
			}
		}

		return manifest = new Manifest();
	}

	public void execute() throws BuildException
	{
		final String destDir;
		if(manifest.getDestdir() == null || (destDir = manifest.getDestdir().getText()) == null)
			throw new BuildException("destdir element must be speficied");

		if(manifest.getSchemas() == null)
			throw new BuildException("schemas element must be speficied");

		final PropertyResolver resolver = new AntPropertyResolver(getProject());
		final File destDirFile = new File(resolver.resolve(destDir));
		System.out.println(destDirFile);

		final Collection<Manifest.Schemas.Schema> schemas;
		if((schemas = manifest.getSchemas().getSchemas()) == null || manifest.getSchemas().getSchemas().size() == 0)
		{
			System.out.println("No schemas defined for binding.");
			return;
		}

		final Collection<SchemaReference> schemaReferences = new ArrayList<SchemaReference>(schemas.size());
		for(Manifest.Schemas.Schema schema : schemas)
		{
			final String deref = resolver.resolve(schema.getText());
			if(deref != null)
				schemaReferences.add(new SchemaReference(deref));
		}

		final BindingContext bindingContext = new BindingContext();
		bindingContext.setDestDir(destDirFile);
		bindingContext.setOverwrite(manifest.getDestdir().getOverwrite());
		bindingContext.setExplodeJars(manifest.getDestdir().getExplodeJars());

		final Generator generator = new Generator(bindingContext, schemaReferences);
		generator.generate();
	}

	public class Manifest
	{
		private Destdir destdir = null;
		private Schemas schemas = null;

		public Destdir getDestdir()
		{
			return destdir;
		}

		public Schemas getSchemas()
		{
			return schemas;
		}

		public Destdir createDestdir()
		{
			return destdir = new Destdir();
		}

		public Schemas createSchemas()
		{
			return schemas = new Schemas();
		}

		public class Destdir
		{
			private String text = "";
			private boolean explodeJars = false;
			private boolean overwrite = false;

			public void setExplodeJars(boolean explodeJars)
			{
				this.explodeJars = explodeJars;
			}

			public Boolean getExplodeJars()
			{
				return explodeJars;
			}

			public void setOverwrite(boolean overwrite)
			{
				this.overwrite = overwrite;
			}

			public Boolean getOverwrite()
			{
				return overwrite;
			}

			public void addText(String text)
			{
				this.text += text;
			}

			public String getText()
			{
				return text;
			}
		}

		public class Schemas
		{
			private Collection<Schema> schemas = null;

			public Collection<Schema> getSchemas()
			{
				return schemas;
			}

			public Schema createSchema()
			{
				if(schemas == null)
					schemas = new ArrayList<Schema>();

				final Schema schema = new Schema();
				schemas.add(schema);
				return schema;
			}

			public class Schema
			{
				private String text = "";

				public void addText(String text)
				{
					this.text += text;
				}

				public String getText()
				{
					return text;
				}
			}
		}
	}
}
