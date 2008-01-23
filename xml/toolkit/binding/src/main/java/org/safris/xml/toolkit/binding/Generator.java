package org.safris.xml.toolkit.binding;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.xml.parsers.DocumentBuilder;
import org.safris.commons.util.Files;
import org.safris.commons.util.URLs;
import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.xml.DOMParsers;
import org.safris.commons.util.xml.SchemaDocument;
import org.safris.commons.util.xml.SchemaReference;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.lexer.phase.composite.SchemaComposite;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.module.phase.BindingContext;
import org.safris.xml.generator.module.phase.Pipeline;
import org.safris.xml.generator.module.phase.StaticReferenceManager;
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

		final BindingContext bindingContext = new BindingContext(destDir, explodeJars, overwrite);
		final Generator generator = new Generator(bindingContext, schemas);
		generator.generate();
	}

	private final BindingContext bindingContext;
	private final Collection<SchemaReference> schemas;

	public Generator(BindingContext bindingContext, Collection<SchemaReference> schemas)
	{
		this.bindingContext = bindingContext;
		this.schemas = schemas;
	}

	public Generator(BindingContext bindingContext, File basedir, Element bindingsElement, long lastModified, PropertyResolver resolver)
	{
		this.bindingContext = bindingContext;
		this.schemas = new HashSet<SchemaReference>();
		parseConfig(basedir, bindingsElement, lastModified, resolver);
	}

	public BindingContext getbindingContext()
	{
		return bindingContext;
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
			else if(bindingContext.getDestDir() == null && "destdir".equals(child.getLocalName()))
			{
				NodeList text = child.getChildNodes();
				for(int j = 0; j < text.getLength(); j++)
				{
					Node node = text.item(j);
					if(node.getNodeType() != Node.TEXT_NODE)
						continue;

					bindingContext.setDestDir(new File(resolver.resolve(node.getNodeValue())));
					final NamedNodeMap attributes = child.getAttributes();
					if(attributes != null)
					{
						for(int k = 0; k < attributes.getLength(); k++)
						{
							final Node attribute = attributes.item(k);
							if("explodeJars".equals(attribute.getLocalName()))
								bindingContext.setExplodeJars(IXSBoolean.parseBoolean(attribute.getNodeValue()));
							else if("overwrite".equals(attribute.getLocalName()))
								bindingContext.setOverwrite(IXSBoolean.parseBoolean(attribute.getNodeValue()));
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
					throw new ExitSevereError("There is an error in your binding xml. Please consult manifest.xsd for proper usage.");

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
				throw new ExitSevereError("There is an error in your binding xml. Please consult manifest.xsd for proper usage.");
		}

		if(hrefURL != null)
		{
			if(bindingContext.getDestDir() != null || schemas.size() != 0)
				throw new ExitSevereError("There is an error in your binding xml. Please consult manifest.xsd for proper usage.");

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
		final Pipeline pipeline = new Pipeline(bindingContext);

		// select the schemas to be generated and exit if no schemas need work
		final Collection<SchemaReference> schemaReferences = new ArrayList<SchemaReference>();
		pipeline.addPhase(schemas, schemaReferences, SchemaLocator.class);

		// prepare the schemas to be worked on and build the dependency map
		final Collection<SchemaDocument> schemaDocuments = new ArrayList<SchemaDocument>();
		pipeline.addPhase(schemaReferences, schemaDocuments, SchemaResolver.class);

		// this translation is necessary to bridge the dependency structure
		// within the framework
		final Collection<SchemaComposite> schemaComposites = new ArrayList<SchemaComposite>();
		pipeline.addPhase(schemaDocuments, schemaComposites, Translation.class);

		// model the schema elements using Model objects
		final Collection<Model> models = new ArrayList<Model>();
		pipeline.addPhase(schemaComposites, models, Model.class);

		// normalize the models
		pipeline.addPhase(models, null, Normalizer.class);

		// plan the schema elements using Plan objects, and write to files
		final Collection<Plan> plans = new ArrayList<Plan>();
		pipeline.addPhase(models, plans, Plan.class);

		// write the plans to source
		pipeline.addPhase(plans, null, Writer.class);

		// compile and jar the bindings
		final Collection<Bundle> bundles = new ArrayList<Bundle>();
		pipeline.addPhase(schemaComposites, bundles, Bundle.class);

		// start the pipeline
		pipeline.begin();

		// FIXME: Intention is obvious, but the implementation is not perfect.
		StaticReferenceManager.clearAll();

		return bundles;
	}
}
