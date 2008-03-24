package org.safris.xml.generator.lexer.processor.model.element;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import org.safris.xml.generator.lexer.processor.Referenceable;
import org.safris.xml.generator.lexer.processor.Undefineable;
import org.safris.xml.generator.lexer.processor.model.AttributableModel;
import org.safris.xml.generator.lexer.processor.model.ElementableModel;
import org.safris.xml.generator.lexer.processor.model.MixableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.MultiplicableModel;
import org.safris.xml.generator.lexer.schema.attribute.Block;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ComplexTypeModel<T extends SimpleTypeModel> extends SimpleTypeModel<T> implements AttributableModel, ElementableModel, MixableModel
{
	private final LinkedHashSet<AttributeModel> attributes = new LinkedHashSet<AttributeModel>();
	private final LinkedHashSet<MultiplicableModel> multiplicableModels = new LinkedHashSet<MultiplicableModel>();
	private Boolean _abstract = false;
	private Block block = null;
	private Boolean mixed = null;

	protected ComplexTypeModel(Node node, Model parent)
	{
		super(node, parent);
		if(node == null)
			return;

		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("abstract".equals(attribute.getLocalName()))
				_abstract = Boolean.parseBoolean(attribute.getNodeValue());
			else if("block".equals(attribute.getLocalName()))
				block = Block.parseBlock(attribute.getNodeValue());
			else if("mixed".equals(attribute.getLocalName()))
				mixed = Boolean.parseBoolean(attribute.getNodeValue());
		}
	}

	public final void addMultiplicableModel(MultiplicableModel multiplicableModel)
	{
		if(!this.equals(multiplicableModel))
			this.multiplicableModels.add(multiplicableModel);
	}

	public final LinkedHashSet<MultiplicableModel> getMultiplicableModels()
	{
		return multiplicableModels;
	}

	public Boolean getAbstract()
	{
		return _abstract;
	}

	public final Block getBlock()
	{
		return block;
	}

	public final Boolean getMixed()
	{
		for(Model model : getChildren())
			if(model instanceof ComplexContentModel && ((ComplexContentModel)model).getMixed() != null)
				return ((ComplexContentModel)model).getMixed();

		return mixed;
	}

	public final void addAttribute(AttributeModel attribute)
	{
		attributes.add(attribute);
	}

	public final LinkedHashSet<AttributeModel> getAttributes()
	{
		return attributes;
	}

	public static class Reference extends ComplexTypeModel implements Referenceable
	{
		private static final Map<UniqueQName,Reference> all = new HashMap<UniqueQName,Reference>();

		protected Reference(Model parent)
		{
			super(null, parent);
		}

		public static Reference parseComplexType(UniqueQName name)
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

	public static class Undefined extends ComplexTypeModel implements Undefineable
	{
		private static final Map<UniqueQName,Undefined> all = new HashMap<UniqueQName,Undefined>();

		protected Undefined(Model parent)
		{
			super(null, parent);
		}

		public static Undefined parseComplexType(UniqueQName name)
		{
			if(name == null)
				return null;

			Undefined type = all.get(name);
			if(type != null)
				return type;

			type = new Undefined(null);
			type.setName(name);
			Undefined.all.put(name, type);
			return type;
		}
	}
}
