package org.safris.xml.generator.lexer.processor.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.safris.commons.logging.Logger;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.xml.NamespaceURI;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.lang.LexerLoggerName;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class Model implements PipelineEntity<Model>
{
	protected static final Logger logger = Logger.getLogger(LexerLoggerName.MODEL);
	protected static final String TO_STRING_DELIMITER = "TO_STRING_DELIMITER";

	private final Collection<Model> children = new ArrayList<Model>();
	private Map<NamespaceURI,URL> schemaReferences = null;

	private Model parent = null;
	private Model previous = null;
	private Model next = null;

	private String id = null;

	private NamespaceURI targetNamespace = null;
	private SchemaModel schema = null;

	protected Model(Node node, Model parent)
	{
		if(node != null)
		{
			final NamedNodeMap attributes = node.getAttributes();
			for(int i = 0; i < attributes.getLength(); i++)
			{
				final Node attribute = attributes.item(i);
				if("id".equals(attribute.getLocalName()))
					id = attribute.getNodeValue();
			}
		}

		if(parent == null)
			return;

		this.parent = parent;
		parent.children.add(this);
	}

	protected final void registerSchemaLocation(NamespaceURI namespaceURI, URL schemaReference)
	{
		if(getParent() != null)
		{
			logger.fine("registering schema location \"" + namespaceURI + "\" to \"" + schemaReference.toExternalForm() + "\"");
			getParent().registerSchemaLocation(namespaceURI, schemaReference);
			return;
		}

		if(schemaReferences == null)
			schemaReferences = new HashMap<NamespaceURI,URL>();

		if(schemaReferences.containsKey(namespaceURI))
			return;

		schemaReferences.put(namespaceURI, schemaReference);
	}

	protected final URL lookupSchemaLocation(NamespaceURI namespaceURI)
	{
		if(getParent() != null)
			return getParent().lookupSchemaLocation(namespaceURI);

		return schemaReferences != null ? schemaReferences.get(namespaceURI) : null;
	}

	public final String getId()
	{
		return id;
	}

	protected final void setPrevious(Model previous)
	{
		this.previous = previous;
	}

	public final Model getPrevious()
	{
		return previous;
	}

	protected final void setNext(Model next)
	{
		this.next = next;
	}

	public final Model getNext()
	{
		return next;
	}

	public final Collection<Model> getChildren()
	{
		return children;
	}

	public final Model getParent()
	{
		return parent;
	}

	public final SchemaModel getSchema()
	{
		if(schema != null)
			return schema;

		Model model = this;
		while(!(model instanceof SchemaModel))
			model = model.getParent();

		return schema = (SchemaModel)model;
	}

	public NamespaceURI getTargetNamespace()
	{
		if(targetNamespace != null)
			return targetNamespace;

		Model handler = this;
		while((handler = handler.getParent()) != null)
			if(handler instanceof SchemaModel)
				return targetNamespace = handler.getTargetNamespace();

		throw new LexerError("should have found a schema! what's going on?");
	}

	public final QName parseQNameValue(String nodeValue, Node parent)
	{
		int i = nodeValue.indexOf(":");
		if(i == -1)
		{
			Node xs = null;
			do
			{
				if(parent.getAttributes() == null)
					return new QName(getTargetNamespace().toString(), nodeValue);

				xs = parent.getAttributes().getNamedItem(UniqueQName.XMLNS.getPrefix().toString());
				if(xs == null)
					parent = parent.getParentNode();
				else
					break;
			}
			while(parent != null);

			if(xs == null)
				throw new LexerError("Namespace problem");

			return new QName(xs.getNodeValue(), nodeValue);
		}

		final String prefix = nodeValue.substring(0, i);
		final NamespaceURI namespaceURI = NamespaceURI.getInstance(parent.lookupNamespaceURI(prefix));
		return new QName(namespaceURI.toString(), nodeValue.substring(i + 1, nodeValue.length()), prefix);
	}

	public String toString()
	{
		return "<" + getClass().getSimpleName() + " " + TO_STRING_DELIMITER + "/>";
	}
}
