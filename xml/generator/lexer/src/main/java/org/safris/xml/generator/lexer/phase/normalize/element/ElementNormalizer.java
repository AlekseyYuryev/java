package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.model.ElementableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.ElementModel;
import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;
import org.safris.xml.generator.module.phase.BindingQName;
import org.safris.xml.generator.module.phase.ProcessorDirectory;

public class ElementNormalizer extends Normalizer<ElementModel>
{
	private final Map<BindingQName,ElementModel> all = new HashMap<BindingQName,ElementModel>();
	private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);
	private final ComplexTypeNormalizer complexTypeNormalizer = (ComplexTypeNormalizer)getDirectory().lookup(ComplexTypeModel.class);

	public ElementNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public final ElementModel parseElement(BindingQName name)
	{
		return all.get(name);
	}

	protected void stage1(ElementModel model)
	{
		if(model.getName() == null || !(model.getParent() instanceof SchemaModel))
			return;

		if(parseElement(model.getName()) == null)
			all.put(model.getName(), model);
	}

	protected void stage2(ElementModel model)
	{
		// First set the elementFormDefault
		Model schema = model.getParent();
		if(schema != null)
			while(!((schema = schema.getParent()) instanceof SchemaModel) && schema != null);

		if(schema != null)
			model.setElementFormDefault(((SchemaModel)schema).getElementFormDefault());

		if(model.getRef() instanceof ElementModel.Reference)
		{
			final ElementModel ref = parseElement(model.getRef().getName());
			if(ref == null)
				throw new LexerError("ref == null for " + model.getRef().getName());

			model.setRef(ref);
		}
		else if(model.getName() != null)
		{
			if(model.getSuperType() instanceof ComplexTypeModel.Reference)
			{
				SimpleTypeModel type = complexTypeNormalizer.parseComplexType(model.getSuperType().getName());
				if(type == null)
					type = simpleTypeNormalizer.parseSimpleType(model.getSuperType().getName());

				if(type == null)
				{
					if(!BindingQName.XS.getNamespaceURI().equals(model.getSuperType().getName().getNamespaceURI()))
						throw new LexerError("type == null for " + model.getSuperType().getName());

					type = SimpleTypeModel.Undefined.parseSimpleType(model.getSuperType().getName());
				}

				model.setSuperType(type);
			}
		}
		else
			throw new LexerError("element type not handled");
	}

	protected void stage3(ElementModel model)
	{
	}

	protected void stage4(ElementModel model)
	{
		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof ElementableModel)
			{
				((ElementableModel)parent).addMultiplicableModel(model);
				break;
			}
		}
	}

	protected void stage5(ElementModel model)
	{
	}

	protected void stage6(ElementModel model)
	{
		if(model.getName() == null || model.getRef() != null)
			return;

		if(model.getSuperType() == null)
		{
			if(model.getSubstitutionGroup() != null)
			{
				model.setSuperType(parseElement(model.getSubstitutionGroup()));
				return;
			}

			boolean def = false;
			for(Model child : model.getChildren())
			{
				if(child instanceof ComplexTypeModel || child instanceof SimpleTypeModel)
				{
					def = true;
					break;
				}
			}

			if(def)
				model.setSuperType(ComplexTypeModel.Undefined.parseComplexType(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anySimpleType")));
			else
			{
				final SimpleTypeModel type = ComplexTypeModel.Undefined.parseComplexType(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anyType"));
				model.setSuperType(type);
				model.setItemTypes(Arrays.<SimpleTypeModel>asList(type));
			}
		}
	}
}
