package org.safris.xml.generator.lexer.phase.model.element;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.phase.Nameable;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.MultiplicableModel;
import org.safris.xml.generator.lexer.schema.attribute.Occurs;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ChoiceModel extends Model implements MultiplicableModel
{
	private final LinkedHashSet<MultiplicableModel> multiplicableModels = new LinkedHashSet<MultiplicableModel>();

	private Occurs maxOccurs = Occurs.parseOccurs("1");
	private Occurs minOccurs = Occurs.parseOccurs("1");

	protected ChoiceModel(Node node, Model parent)
	{
		super(node, parent);
		NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			Node attribute = attributes.item(i);
			if("maxOccurs".equals(attribute.getLocalName()))
				maxOccurs = Occurs.parseOccurs(attribute.getNodeValue());
			else if("minOccurs".equals(attribute.getLocalName()))
				minOccurs = Occurs.parseOccurs(attribute.getNodeValue());
		}
	}

	public final void addMultiplicableModel(MultiplicableModel multiplicableModel)
	{
		if(multiplicableModel instanceof Nameable && ((Nameable)multiplicableModel).getName() != null && ((Nameable)multiplicableModel).getName().getLocalPart().equals("inline"))
		{
			int i = 0;
		}
		if(!this.equals(multiplicableModel))
			this.multiplicableModels.add(multiplicableModel);
	}

	public final LinkedHashSet<MultiplicableModel> getMultiplicableModels()
	{
		return multiplicableModels;
	}

	public final Occurs getMaxOccurs()
	{
		return maxOccurs;
	}

	public final Occurs getMinOccurs()
	{
		return minOccurs;
	}

	public int hashCode()
	{
		return (getClass().getName() + toString()).hashCode();
	}

	public String toString()
	{
		return super.toString().replace(TO_STRING_DELIMITER, "maxOccurs=\"" + maxOccurs + "\" minOccurs=\"" + minOccurs + "\"");
	}
}
