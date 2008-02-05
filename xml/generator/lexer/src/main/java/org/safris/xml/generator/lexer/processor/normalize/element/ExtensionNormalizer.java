package org.safris.xml.generator.lexer.processor.normalize.element;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.ExtensionModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.processor.BindingQName;

public class ExtensionNormalizer extends Normalizer<ExtensionModel>
{
	private final ElementNormalizer elementNormalizer = (ElementNormalizer)getDirectory().lookup(ElementModel.class);
	private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);
	private final ComplexTypeNormalizer complexTypeNormalizer = (ComplexTypeNormalizer)getDirectory().lookup(ComplexTypeModel.class);

	public ExtensionNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(ExtensionModel model)
	{
	}

	protected void stage2(ExtensionModel model)
	{
		// First de-reference the base
		SimpleTypeModel base = null;
		if(model.getBase() instanceof SimpleTypeModel.Reference)
		{
			base = simpleTypeNormalizer.parseSimpleType(model.getBase().getName());
			if(base == null)
				base = complexTypeNormalizer.parseComplexType(model.getBase().getName());

			if(base == null)
			{
				if(!BindingQName.XS.getNamespaceURI().equals(model.getBase().getName().getNamespaceURI()))
					throw new LexerError("base == null for " + model.getBase().getName());

				base = SimpleTypeModel.Undefined.parseSimpleType(model.getBase().getName());
			}
		}
		else if(model.getBase() instanceof ComplexTypeModel.Reference)
		{
			base = complexTypeNormalizer.parseComplexType(model.getBase().getName());
			if(base == null)
			{
				if(!BindingQName.XS.getNamespaceURI().equals(model.getBase().getName().getNamespaceURI()))
					throw new LexerError("base == null for " + model.getBase().getName());

				base = ComplexTypeModel.Undefined.parseComplexType(model.getBase().getName());
			}
		}
		else
			throw new LexerError(getClass().getName());

		model.setBase(base);

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof Nameable && ((Nameable)parent).getName() != null)
			{
				if(parent instanceof ElementModel)
				{
					// We do not want to dereference nested elements because there are name collisions
					ElementModel element = (ElementModel)parent;
					if(element.getParent() instanceof SchemaModel)
						element = elementNormalizer.parseElement(((Nameable)parent).getName());

					if(element == null)
						throw new LexerError("element == null");

					element.setSuperType(base);
				}
				else
				{
					SimpleTypeModel type = simpleTypeNormalizer.parseSimpleType(((Nameable)parent).getName());
					if(type == null)
						type = complexTypeNormalizer.parseComplexType(((Nameable)parent).getName());

					if(type == null)
						throw new LexerError("type == null for " + ((Nameable)parent).getName());

					// NOTE: This occurs when we're doing a <redefine/>
					if(type == base)
						break;

					type.setSuperType(base);
				}

				break;
			}
		}
	}

	protected void stage3(ExtensionModel model)
	{
		if(model.getBase() == null || model.getBase().getName() == null)
			return;

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof SimpleTypeModel && model.getBase().getName().equals(((Nameable)parent).getName()) && parent.getParent() instanceof RedefineModel)
			{
				model.getBase().setRedefine((SimpleTypeModel)parent);

				if(parent instanceof SimpleTypeModel)
				{
					SimpleTypeModel redefine = (SimpleTypeModel)parent;
					redefine.setSuperType(model.getBase().getSuperType());
				}
			}
		}
	}

	protected void stage4(ExtensionModel model)
	{
	}

	protected void stage5(ExtensionModel model)
	{
		if(model.getBase() == null || model.getBase().getName() == null)
			return;

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof SimpleTypeModel && model.getBase().getName().equals(((Nameable)parent).getName()) && parent.getParent() instanceof RedefineModel)
			{
				if(parent instanceof ComplexTypeModel)
				{
					if(!(model.getBase() instanceof ComplexTypeModel))
						throw new LexerError("complexType redefinition done by something other than a complexType");

					ComplexTypeModel redefine = (ComplexTypeModel)parent;
					if(redefine.getAttributes().size() != 0)
					{
						final LinkedHashSet attributes = (LinkedHashSet)((ComplexTypeModel)model.getBase()).getAttributes().clone();
						attributes.addAll(redefine.getAttributes());
						redefine.getAttributes().clear();
						redefine.getAttributes().addAll(attributes);
					}
					else
						redefine.getAttributes().addAll(((ComplexTypeModel)model.getBase()).getAttributes());

					if(redefine.getMultiplicableModels().size() != 0)
					{
						final LinkedHashSet multiplicableModels = (LinkedHashSet)((ComplexTypeModel)model.getBase()).getMultiplicableModels().clone();
						multiplicableModels.addAll(redefine.getMultiplicableModels());
						redefine.getMultiplicableModels().clear();
						redefine.getMultiplicableModels().addAll(multiplicableModels);
					}
					else
						redefine.getMultiplicableModels().addAll(((ComplexTypeModel)model.getBase()).getMultiplicableModels());
				}

				break;
			}
		}
	}

	protected void stage6(ExtensionModel model)
	{
	}
}