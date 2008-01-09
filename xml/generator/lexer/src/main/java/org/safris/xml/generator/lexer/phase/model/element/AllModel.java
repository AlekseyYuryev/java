package org.safris.xml.generator.lexer.phase.model.element;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.MultiplicableModel;
import org.safris.xml.generator.lexer.schema.attribute.Occurs;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AllModel extends Model implements MultiplicableModel
{
	private final LinkedHashSet<MultiplicableModel> multiplicableModels = new LinkedHashSet<MultiplicableModel>();

	private Occurs maxOccurs = Occurs.parseOccurs("1");
	private Occurs minOccurs = Occurs.parseOccurs("1");

	protected AllModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("maxOccurs".equals(attribute.getLocalName()))
				maxOccurs = Occurs.parseOccurs(attribute.getNodeValue());
			else if("minOccurs".equals(attribute.getLocalName()))
				minOccurs = Occurs.parseOccurs(attribute.getNodeValue());
		}
	}

	public void addMultiplicableModel(MultiplicableModel multiplicableModel)
	{
		if(!this.equals(multiplicableModel))
			this.multiplicableModels.add(multiplicableModel);
	}

	public LinkedHashSet<MultiplicableModel> getMultiplicableModels()
	{
		return multiplicableModels;
	}

	public Occurs getMaxOccurs()
	{
		return maxOccurs;
	}

	public Occurs getMinOccurs()
	{
		return minOccurs;
	}

	public int hashCode()
	{
		return toString().hashCode();
	}

	public String toString()
	{
		return super.toString().replace(TO_STRING_DELIMITER, "maxOccurs=\"" + maxOccurs + "\" minOccurs=\"" + minOccurs + "\"");
	}
}
