package org.safris.xml.generator.lexer.processor.model.element;

import java.io.File;
import java.net.URL;
import org.safris.commons.util.Files;
import org.safris.commons.util.URLs;
import org.safris.commons.util.xml.NamespaceURI;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.schema.attribute.BlockDefault;
import org.safris.xml.generator.lexer.schema.attribute.FinalDefault;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.processor.BindingQName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SchemaModel extends Model
{
	private Form attributeFormDefault = Form.UNQUALIFIED;
	private BlockDefault blockDefault = null;
	private Form elementFormDefault = Form.UNQUALIFIED;
	private FinalDefault finalDefault = null;
	private String lang = null;
	private NamespaceURI targetNamespace = null;
	private String version = null;
	private URL url = null;
	private String targetNamespaceSchemaLocationName = null;

	protected SchemaModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("attributeFormDefault".equals(attribute.getLocalName()))
				attributeFormDefault = Form.parseForm(attribute.getNodeValue());
			else if("blockDefault".equals(attribute.getLocalName()))
				blockDefault = BlockDefault.parseBlockDefault(attribute.getNodeValue());
			else if("elementFormDefault".equals(attribute.getLocalName()))
				elementFormDefault = Form.parseForm(attribute.getNodeValue());
			else if("finalDefault".equals(attribute.getLocalName()))
				finalDefault = FinalDefault.parseFinalDefault(attribute.getNodeValue());
			else if("lang".equals(attribute.getLocalName()))
				lang = attribute.getNodeValue();
			else if("targetNamespace".equals(attribute.getLocalName()))
				setTargetNamespace(NamespaceURI.getInstance(attribute.getNodeValue()));
			else if("version".equals(attribute.getLocalName()))
				version = attribute.getNodeValue();
		}
	}

	public final void setTargetNamespaceSchemaLocation(URL targetNamespaceSchemaLocation)
	{
		this.targetNamespaceSchemaLocationName = URLs.getName(targetNamespaceSchemaLocation);
	}

	public final String getTargetNamespaceSchemaLocationName()
	{
		return targetNamespaceSchemaLocationName;
	}

	public final void setURL(URL url)
	{
		this.url = url;
		final String display = Files.relativePath(Files.getCwd().getAbsoluteFile(), new File(url.getFile()).getAbsoluteFile());
		logger().info("Scanning {" + getTargetNamespace() + "} from " + display);
	}

	public final URL getURL()
	{
		return url;
	}

	public final Form getAttributeFormDefault()
	{
		return attributeFormDefault;
	}

	public final BlockDefault getBlockDefault()
	{
		return blockDefault;
	}

	public final Form getElementFormDefault()
	{
		return elementFormDefault;
	}

	public final FinalDefault getFinalDefault()
	{
		return finalDefault;
	}

	public final String getLang()
	{
		return lang;
	}

	public final void setTargetNamespace(NamespaceURI targetNamespace)
	{
		if(targetNamespace == null)
			throw new Error("NULL targetNamespace");

		this.targetNamespace = targetNamespace;
	}

	public final NamespaceURI getTargetNamespace()
	{
		return targetNamespace;
	}

	public final String getVersion()
	{
		return version;
	}

	public String toString()
	{
		return BindingQName.XS.getNamespaceURI() + " " + targetNamespace;
	}
}
