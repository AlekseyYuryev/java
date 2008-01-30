package org.safris.xml.generator.lexer.processor.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.xml.NamespaceURI;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.processor.BindingQName;
import org.safris.xml.generator.processor.ElementModule;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class Model implements ElementModule<Model>
{
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

		// FIXME: This looks ugly!
		if(parent == null || (this instanceof SchemaModel && parent instanceof SchemaModel))
			return;

		this.parent = parent;
		parent.children.add(this);
	}

	protected final void registerSchemaLocation(NamespaceURI namespaceURI, URL schemaReference)
	{
		if(getParent() != null)
		{
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

		Model handler = this;
		while(!(handler instanceof SchemaModel))
			handler = handler.getParent();

		return schema = (SchemaModel)handler;
	}

	public NamespaceURI getTargetNamespace()
	{
		if(targetNamespace != null)
			return targetNamespace;

		Model handler = this;
		while((handler = handler.getParent()) != null)
			if(handler instanceof SchemaModel)
				return targetNamespace = handler.getTargetNamespace();

		throw new ExitSevereError("should have found a schema! what's going on?");
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

				xs = parent.getAttributes().getNamedItem(BindingQName.XMLNS.getPrefix().toString());
				if(xs == null)
					parent = parent.getParentNode();
				else
					break;
			}
			while(parent != null);

			if(xs == null)
				throw new ExitSevereError("Namespace problem");

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
