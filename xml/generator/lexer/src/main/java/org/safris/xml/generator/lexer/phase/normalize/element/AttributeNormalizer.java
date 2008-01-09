package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.model.AttributableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.phase.model.element.AttributeModel;
import org.safris.xml.generator.lexer.phase.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.element.AttributeNormalizer;
import org.safris.xml.generator.lexer.phase.normalize.element.SimpleTypeNormalizer;
import org.safris.xml.generator.module.phase.Nameable;

public class AttributeNormalizer extends Normalizer<AttributeModel>
{
	private static final Map<BindingQName,AttributeModel> all = new HashMap<BindingQName,AttributeModel>();

	public static final AttributeModel parseAttribute(BindingQName name)
	{
		return all.get(name);
	}

	protected void stage1(AttributeModel model)
	{
		if(model.getName() == null || !(model.getParent() instanceof SchemaModel))
			return;

		if(AttributeNormalizer.parseAttribute(model.getName()) == null)
			AttributeNormalizer.all.put(model.getName(), model);
	}

	protected void stage2(AttributeModel model)
	{
		// First set the attributeFormDefault value
		Model schema = model.getParent();
		if(schema != null)
			while(!((schema = schema.getParent()) instanceof SchemaModel) && schema != null);

		if(schema != null)
			model.setAttributeFormDefault(((SchemaModel)schema).getAttributeFormDefault());

		if(model.getRef() instanceof AttributeModel.Reference)
		{
			final AttributeModel ref = AttributeNormalizer.parseAttribute(model.getRef().getName());
			if(ref == null)
			{
				final Map<BindingQName,AttributeModel> all = AttributeNormalizer.all;
				throw new LexerError("ref == null for " + model.getRef().getName());
			}

			model.setRef(ref);
		}

		if(model.getSuperType() instanceof SimpleTypeModel.Reference)
		{
			SimpleTypeModel type = SimpleTypeNormalizer.parseSimpleType(model.getSuperType().getName());
			if(type == null)
			{
				if(!BindingQName.XS.getNamespaceURI().equals(model.getSuperType().getName().getNamespaceURI()))
				{
					// FIXME: DEBUG
					type = SimpleTypeNormalizer.parseSimpleType(model.getSuperType().getName());
					throw new LexerError("type == null for " + model.getSuperType().getName());
				}

				type = SimpleTypeModel.Undefined.parseSimpleType(model.getSuperType().getName());
			}

			model.setSuperType(type);
		}

		// Add the attribute to the attributeGroup
		if(model.getParent() instanceof AttributeGroupModel)
			((AttributeGroupModel)model.getParent()).addAttribute(model);
	}

	protected void stage3(AttributeModel model)
	{
	}

	protected void stage4(AttributeModel model)
	{
		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof AttributableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null)
			{
				((AttributableModel)parent).addAttribute(model);
				break;
			}
		}

		if(model.getFixed() != null)
			model.addEnumeration(new EnumerationModel(model.getFixed()));
	}

	protected void stage5(AttributeModel model)
	{
	}

	protected void stage6(AttributeModel model)
	{
		if(model.getName() == null)
			return;

		if(model.getSuperType() == null)
		{
			SimpleTypeModel type = ComplexTypeModel.Undefined.parseComplexType(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anySimpleType"));
			model.setSuperType(type);
			model.setItemTypes(Arrays.<SimpleTypeModel>asList(type));
		}
	}
}
