package org.safris.xml.toolkit.binding;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.xml.parsers.DocumentBuilder;
import org.safris.commons.io.Files;
import org.safris.commons.net.URLs;
import org.safris.commons.xml.DOMParsers;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.PlanDirectory;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.processor.write.WriterDirectory;
import org.safris.xml.generator.compiler.runtime.BindingError;
import org.safris.xml.generator.lexer.document.SchemaDocumentDirectory;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.composite.SchemaCompositeDirectory;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.ModelDirectory;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.safris.xml.generator.lexer.processor.reference.SchemaReferenceDirectory;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.Pipeline;
import org.w3.x2001.xmlschema.IXSBoolean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Generator extends AbstractGenerator
{
	private static void usage()
	{
		System.err.println("Usage: Generator [OPTIONS] <-d <destDir>> <schema.xsd>");
		System.err.println("");
		System.err.println("Mandatory arguments:");
		System.err.println("  -d <destDir>    Specify the destination directory.");
		System.err.println("");
		System.err.println("Optional arguments:");
		System.err.println("  --explodeJars   Explode generated jars into the destination directory.");
		System.err.println("  --overwrite     Overwrite all existing generated classes.");
		System.exit(1);
	}

	public static void main(String[] args)
	{
		if(args.length == 0 || args[0] == null || args[0].length() == 0)
			usage();

		boolean explodeJars = false;
		boolean overwrite = false;
		File destDir = null;
		final Collection<SchemaReference> schemas = new HashSet<SchemaReference>();
		for(int i = 0; i < args.length; i++)
		{
			if("--explodeJars".equals(args[i]))
				explodeJars = true;
			else if("--overwrite".equals(args[i]))
				overwrite = true;
			else if("-d".equals(args[i]) && i < args.length)
				destDir = new File(args[++i]);
			else
				schemas.add(new SchemaReference(args[i]));
		}

		if(destDir == null)
			destDir = Files.getCwd();

		final GeneratorContext generatorContext = new GeneratorContext(destDir, explodeJars, overwrite);
		final Generator generator = new Generator(generatorContext, schemas);
		generator.generate();
	}

	private final GeneratorContext generatorContext;
	private final Collection<SchemaReference> schemas;

	public Generator(GeneratorContext generatorContext, Collection<SchemaReference> schemas)
	{
		this.generatorContext = generatorContext;
		this.schemas = schemas;
	}

	public Generator(GeneratorContext generatorContext, File basedir, Element bindingsElement, long lastModified, PropertyResolver resolver)
	{
		this.generatorContext = generatorContext;
		this.schemas = new HashSet<SchemaReference>();
		parseConfig(basedir, bindingsElement, lastModified, resolver);
	}

	public GeneratorContext getgeneratorContext()
	{
		return generatorContext;
	}

	public Collection<SchemaReference> getSchemas()
	{
		return schemas;
	}

	public void parseConfig(File basedir, Element bindingsElement, long lastModified, PropertyResolver resolver)
	{
		if(!"manifest".equals(bindingsElement.getNodeName()))
			throw new IllegalArgumentException("Invalid manifest element!");

		URL hrefURL = null;
		NodeList list = bindingsElement.getChildNodes();
		for(int i = 0; i < list.getLength(); i++)
		{
			String schemaReference = null;
			Node child = list.item(i);
			if("schemas".equals(child.getNodeName()))
			{
				NodeList schemaNodes = child.getChildNodes();
				for(int j = 0; j < schemaNodes.getLength(); j++)
				{
					Node schemaNode = schemaNodes.item(j);
					if(!"schema".equals(schemaNode.getLocalName()))
						continue;

					NodeList text = schemaNode.getChildNodes();
					for(int k = 0; k < text.getLength(); k++)
					{
						Node node = text.item(k);
						if(node.getNodeType() != Node.TEXT_NODE)
							continue;

						schemaReference = resolver.resolve(node.getNodeValue());
						break;
					}

					if(schemaReference.length() != 0 && schemaReference.charAt(0) != File.separatorChar)
						schemaReference = basedir.getAbsolutePath() + File.separator + schemaReference;

					schemas.add(new SchemaReference(resolver.resolve(schemaReference)));
				}
			}
			else if(generatorContext.getDestDir() == null && "destdir".equals(child.getLocalName()))
			{
				NodeList text = child.getChildNodes();
				for(int j = 0; j < text.getLength(); j++)
				{
					Node node = text.item(j);
					if(node.getNodeType() != Node.TEXT_NODE)
						continue;

					generatorContext.setDestDir(new File(resolver.resolve(node.getNodeValue())));
					final NamedNodeMap attributes = child.getAttributes();
					if(attributes != null)
					{
						for(int k = 0; k < attributes.getLength(); k++)
						{
							final Node attribute = attributes.item(k);
							if("explodeJars".equals(attribute.getLocalName()))
								generatorContext.setExplodeJars(IXSBoolean.parseBoolean(attribute.getNodeValue()));
							else if("overwrite".equals(attribute.getLocalName()))
								generatorContext.setOverwrite(IXSBoolean.parseBoolean(attribute.getNodeValue()));
						}
					}

					break;
				}
			}
			else if(hrefURL == null && "link".equals(child.getLocalName()))
			{
				NamedNodeMap attributes = child.getAttributes();
				Node hrefNode = null;
				if(attributes == null || (hrefNode = attributes.getNamedItemNS("http://www.w3.org/1999/xlink", "href")) == null || hrefNode.getNodeValue().length() == 0)
					throw new BindingError("There is an error in your binding xml. Please consult manifest.xsd for proper usage.");

				String href = resolver.resolve(hrefNode.getNodeValue());
				try
				{
					if(basedir != null)
						hrefURL = URLs.makeUrlFromPath(basedir.getAbsolutePath(), href);
					else
						hrefURL = URLs.makeUrlFromPath(href);
				}
				catch(MalformedURLException e)
				{
					throw new CompilerError(e);
				}
			}
			else if(child.getNodeType() == Node.ELEMENT_NODE)
				throw new BindingError("There is an error in your binding xml. Please consult manifest.xsd for proper usage.");
		}

		if(hrefURL != null)
		{
			if(generatorContext.getDestDir() != null || schemas.size() != 0)
				throw new BindingError("There is an error in your binding xml. Please consult manifest.xsd for proper usage.");

			long modified = 0;
			final Document document;
			try
			{
				final DocumentBuilder documentBuilder = DOMParsers.newDocumentBuilder();
				final URLConnection connection = hrefURL.openConnection();
				modified = connection.getLastModified();
				document = documentBuilder.parse(connection.getInputStream());
			}
			catch(Exception e)
			{
				throw new CompilerError(e);
			}

			parseConfig(basedir, document.getDocumentElement(), modified, resolver);
		}
	}

	public Collection<Bundle> generate()
	{
		final Pipeline pipeline = new Pipeline(generatorContext);

		// select the schemas to be generated and exit if no schemas need work
		final Collection<SchemaReference> schemaReferences = new ArrayList<SchemaReference>();
		pipeline.<SchemaReference,SchemaReference>addProcessor(schemas, schemaReferences, new SchemaReferenceDirectory());

		// prepare the schemas to be worked on and build the dependency map
		final Collection<SchemaDocument> schemaDocuments = new ArrayList<SchemaDocument>();
		pipeline.<SchemaReference,SchemaDocument>addProcessor(schemaReferences, schemaDocuments, new SchemaDocumentDirectory());

		// this translation is necessary to bridge the dependency structure
		// within the framework
		final Collection<SchemaComposite> schemaComposites = new ArrayList<SchemaComposite>();
		pipeline.<SchemaDocument,SchemaComposite>addProcessor(schemaDocuments, schemaComposites, new SchemaCompositeDirectory());

		// model the schema elements using Model objects
		final Collection<Model> models = new ArrayList<Model>();
		pipeline.<SchemaComposite,Model>addProcessor(schemaComposites, models, new ModelDirectory());

		// normalize the models
		pipeline.<Model,Normalizer>addProcessor(models, null, new NormalizerDirectory());

		// plan the schema elements using Plan objects, and write to files
		final Collection<Plan> plans = new ArrayList<Plan>();
		pipeline.<Model,Plan>addProcessor(models, plans, new PlanDirectory());

		// write the plans to source
		pipeline.<Plan,Writer>addProcessor(plans, null, new WriterDirectory());

		// compile and jar the bindings
		final Collection<Bundle> bundles = new ArrayList<Bundle>();
		pipeline.<SchemaComposite,Bundle>addProcessor(schemaComposites, bundles, new BundleDirectory());

		// start the pipeline
		pipeline.begin();

		return bundles;
	}
}
