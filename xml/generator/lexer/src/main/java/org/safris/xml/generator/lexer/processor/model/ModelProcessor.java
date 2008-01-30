package org.safris.xml.generator.lexer.processor.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.xml.NamespaceURI;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.composite.SchemaModelComposite;
import org.safris.xml.generator.lexer.processor.composite.SchemaNodeComposite;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModelProcessor extends ModuleProcessor<SchemaComposite,Model>
{
	private Model root;

	public Collection<Model> process(Collection<SchemaComposite> documents, GeneratorContext generatorContext, ProcessorDirectory<SchemaComposite, Model> directory)
	{
		root = new Model(null, null){};
		// Then we parse all of the schemas that have been included and imported
		final Collection<Model> schemaModels = new ArrayList<Model>();

		for(SchemaComposite schemaComposite : documents)
		{
			final SchemaModelComposite SchemaModelComposite = (SchemaModelComposite)schemaComposite;
			final SchemaDocument schemaDocument = SchemaModelComposite.getSchemaDocument();
			final SchemaModel model = recurse(root, schemaDocument.getSchemaReference().getNamespaceURI(), schemaDocument.getDocument().getChildNodes(), schemaDocument.getSchemaReference().getURL(), directory);
			if(model == null)
				throw new ExitSevereError("We should have found a schema!");

			SchemaModelComposite.setSchemaModel(model);
			schemaModels.add(model);
		}

		return schemaModels;
	}

	private final SchemaModel recurse(Model model, NamespaceURI targetNamespace, NodeList children, URL url, ProcessorDirectory<SchemaComposite,Model> directory)
	{
		if(children == null || children.getLength() == 0)
			return null;

		// FIXME: This looks ugly!
		SchemaModel schema = null;
		if(model instanceof SchemaModel)
		{
			schema = (SchemaModel)model;
			if(model.getTargetNamespace() == null)
			{
				// This means that this is an included schema
				schema.setTargetNamespace(targetNamespace);
				URL schemaReference = model.lookupSchemaLocation(targetNamespace);
				if(schemaReference == null)
					model.registerSchemaLocation(targetNamespace, schemaReference = url);

				schema.setTargetNamespaceSchemaLocation(schemaReference);
			}
			else
			{
				model.registerSchemaLocation(targetNamespace, url);
				schema.setTargetNamespaceSchemaLocation(url);
			}

			schema.setURL(url);
			url = null;
		}

		Model current = null;
		for(int i = 0; i < children.getLength(); i++)
		{
			final Node child = children.item(i);
			if(child.getLocalName() == null)
				continue;

			final SchemaNodeComposite nodeComposite = new SchemaNodeComposite(child);
			final Model handler = (Model)directory.lookup(nodeComposite, model);
			if(current != null)
			{
				handler.setPrevious(current);
				current.setNext(handler);
			}

			current = handler;

			final SchemaModel temp = recurse(handler, targetNamespace, child.getChildNodes(), url, directory);
			if(temp != null)
				schema = temp;
		}

		return schema;
	}
}
