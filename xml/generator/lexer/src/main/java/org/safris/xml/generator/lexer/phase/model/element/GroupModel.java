package org.safris.xml.generator.lexer.phase.model.element;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import org.safris.xml.generator.lexer.phase.Referenceable;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.MultiplicableModel;
import org.safris.xml.generator.lexer.phase.model.NamedModel;
import org.safris.xml.generator.lexer.phase.model.RedefineableModel;
import org.safris.xml.generator.lexer.phase.model.ReferableModel;
import org.safris.xml.generator.lexer.schema.attribute.Occurs;
import org.safris.xml.generator.module.phase.BindingQName;
import org.safris.xml.generator.module.phase.Nameable;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class GroupModel extends NamedModel implements MultiplicableModel, Nameable<Model>, RedefineableModel<GroupModel>, ReferableModel<GroupModel>
{
	private final LinkedHashSet<MultiplicableModel> multiplicableModels = new LinkedHashSet<MultiplicableModel>();
	private Occurs maxOccurs = Occurs.parseOccurs("1");
	private Occurs minOccurs = Occurs.parseOccurs("1");
	private GroupModel ref = null;
	private GroupModel redefine = null;

	protected GroupModel(Node node, Model parent)
	{
		super(node, parent);
		if(node == null)
			return;

		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("maxOccurs".equals(attribute.getLocalName()))
				maxOccurs = Occurs.parseOccurs(attribute.getNodeValue());
			else if("minOccurs".equals(attribute.getLocalName()))
				minOccurs = Occurs.parseOccurs(attribute.getNodeValue());
			else if("ref".equals(attribute.getLocalName()))
				ref = GroupModel.Reference.parseGroup(BindingQName.getInstance(parseQNameValue(attribute.getNodeValue(), node)));
		}
	}

	public final void setRedefine(GroupModel redefine)
	{
		this.redefine = redefine;
	}

	public final GroupModel getRedefine()
	{
		return redefine;
	}

	public final void addMultiplicableModel(MultiplicableModel multiplicableModel)
	{
		if(multiplicableModel instanceof ChoiceModel && getName().getLocalPart().equals("inline"))
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

	public final void setRef(GroupModel _ref)
	{
		this.ref = _ref;
	}

	public final GroupModel getRef()
	{
		return ref;
	}

	public int hashCode()
	{
		return toString().hashCode();
	}

	public String toString()
	{
		return super.toString().replace(TO_STRING_DELIMITER, "maxOccurs=\"" + maxOccurs + "\" minOccurs=\"" + minOccurs + "\" ref=\"" + ref + "\"");
	}

	public static class Reference extends GroupModel implements Referenceable
	{
		private static final Map<BindingQName,Reference> all = new HashMap<BindingQName,Reference>();

		protected Reference(Model parent)
		{
			super(null, parent);
		}

		public static Reference parseGroup(BindingQName name)
		{
			Reference type = all.get(name);
			if(type != null)
				return type;

			type = new Reference(null);
			type.setName(name);
			Reference.all.put(name, type);
			return type;
		}
	}
}
