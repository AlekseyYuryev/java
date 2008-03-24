package org.safris.xml.generator.lexer.processor.normalize.element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.model.ElementableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.lang.UniqueQName;

public class ElementNormalizer extends Normalizer<ElementModel>
{
	private final Map<UniqueQName,ElementModel> all = new HashMap<UniqueQName,ElementModel>();
	private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);
	private final ComplexTypeNormalizer complexTypeNormalizer = (ComplexTypeNormalizer)getDirectory().lookup(ComplexTypeModel.class);

	public ElementNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public final ElementModel parseElement(UniqueQName name)
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
					if(!UniqueQName.XS.getNamespaceURI().equals(model.getSuperType().getName().getNamespaceURI()))
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
				model.setSuperType(ComplexTypeModel.Undefined.parseComplexType(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType")));
			else
			{
				final SimpleTypeModel type = ComplexTypeModel.Undefined.parseComplexType(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anyType"));
				model.setSuperType(type);
				model.setItemTypes(Arrays.<SimpleTypeModel>asList(type));
			}
		}
	}
}
