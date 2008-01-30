package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.phase.model.AttributableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.phase.model.element.AttributeModel;
import org.safris.xml.generator.lexer.phase.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.phase.normalize.element.SimpleTypeNormalizer;
import org.safris.xml.generator.module.phase.BindingQName;
import org.safris.xml.generator.module.phase.HandlerDirectory;
import org.safris.xml.generator.module.phase.Nameable;

public class AttributeNormalizer extends Normalizer<AttributeModel>
{
	private final Map<BindingQName,AttributeModel> all = new HashMap<BindingQName,AttributeModel>();
	private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);

	public AttributeNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public final AttributeModel parseAttribute(BindingQName name)
	{
		return all.get(name);
	}

	protected void stage1(AttributeModel model)
	{
		if(model.getName() == null || !(model.getParent() instanceof SchemaModel))
			return;

		if(parseAttribute(model.getName()) == null)
			all.put(model.getName(), model);
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
			final AttributeModel ref = parseAttribute(model.getRef().getName());
			model.setRef(ref);
		}

		if(model.getSuperType() instanceof SimpleTypeModel.Reference)
		{
			SimpleTypeModel type = simpleTypeNormalizer.parseSimpleType(model.getSuperType().getName());
			if(type == null)
				type = SimpleTypeModel.Undefined.parseSimpleType(model.getSuperType().getName());

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
			final SimpleTypeModel type = ComplexTypeModel.Undefined.parseComplexType(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anySimpleType"));
			model.setSuperType(type);
			model.setItemTypes(Arrays.<SimpleTypeModel>asList(type));
		}
	}
}
