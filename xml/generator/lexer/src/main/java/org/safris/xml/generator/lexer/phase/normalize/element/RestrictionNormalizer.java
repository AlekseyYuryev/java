package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.ArrayList;
import java.util.Collection;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.model.AttributableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.MultiplicableModel;
import org.safris.xml.generator.lexer.phase.model.RestrictableModel;
import org.safris.xml.generator.lexer.phase.model.element.AttributeModel;
import org.safris.xml.generator.lexer.phase.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.ElementModel;
import org.safris.xml.generator.lexer.phase.model.element.ListModel;
import org.safris.xml.generator.lexer.phase.model.element.RedefineModel;
import org.safris.xml.generator.lexer.phase.model.element.RestrictionModel;
import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.UnionModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.module.phase.Nameable;

public class RestrictionNormalizer extends Normalizer<RestrictionModel>
{
	protected void stage1(RestrictionModel model)
	{
	}

	protected void stage2(RestrictionModel model)
	{
		if(model.getBase() == null)
			return;

		// First de-reference the base
		SimpleTypeModel base = null;
		if(model.getBase() instanceof SimpleTypeModel.Reference)
		{
			base = SimpleTypeNormalizer.parseSimpleType(model.getBase().getName());
			if(base == null)
				base = ComplexTypeNormalizer.parseComplexType(model.getBase().getName());

			if(base == null)
			{
				if(!BindingQName.XS.getNamespaceURI().equals(model.getBase().getName().getNamespaceURI()))
					throw new LexerError("base == null for " + model.getBase().getName());

				base = SimpleTypeModel.Undefined.parseSimpleType(model.getBase().getName());
			}
		}
		else if(model.getBase() instanceof ComplexTypeModel.Reference)
		{
			base = ComplexTypeNormalizer.parseComplexType(model.getBase().getName());
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
			if(base instanceof SimpleTypeModel)
			{
				if(parent instanceof ListModel)
				{
					((ListModel)parent).setItemType(base);
					break;
				}

				if(parent instanceof UnionModel)
				{
					((UnionModel)parent).getMemberTypes().add(base);
					break;
				}
			}

			if(parent instanceof Nameable && ((Nameable)parent).getName() != null)
			{
				if(parent instanceof ElementModel)
				{
					// We do not want to dereference nested elements because there are name collisions
					ElementModel element = (ElementModel)parent;
					if(element.getParent() instanceof SchemaModel)
						element = ElementNormalizer.parseElement(((Nameable)parent).getName());

					if(element == null)
						throw new LexerError("element == null");

					element.setSuperType(base);
					element.setRestriction(true);
				}
				else if(parent instanceof AttributeModel)
				{
					// We do not want to dereference nested attributes because there are name collisions
					AttributeModel attribute = (AttributeModel)parent;
					if(attribute.getParent() instanceof SchemaModel)
						attribute = AttributeNormalizer.parseAttribute(((Nameable)parent).getName());

					if(attribute == null)
						throw new LexerError("element == null");

					attribute.setSuperType(base);
					attribute.setRestriction(true);
				}
				else
				{
					SimpleTypeModel type = SimpleTypeNormalizer.parseSimpleType(((Nameable)parent).getName());
					if(type == null)
						type = ComplexTypeNormalizer.parseComplexType(((Nameable)parent).getName());

					if(type == null)
						throw new LexerError("type == null for " + ((Nameable)parent).getName());

					if(type instanceof ComplexTypeModel && type.getSuperType() != null)
						break;

					type.setSuperType(base);
					type.setRestriction(true);
				}

				break;
			}
		}
	}

	protected void stage3(RestrictionModel model)
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

				break;
			}
		}
	}

	protected void stage4(RestrictionModel model)
	{
	}

	protected void stage5(RestrictionModel model)
	{
		if(model.getBase() == null || BindingQName.XS.getNamespaceURI().equals(model.getBase().getName().getNamespaceURI()))
			return;

		// handle all attributes
		if(model.getBase() instanceof ComplexTypeModel)
		{
			Collection<AttributeModel> restrictionAttributes = findChildAttributes(model.getChildren());
			for(AttributeModel restrictionAttribute : restrictionAttributes)
			{
				RestrictionPair<AttributeModel> baseAttributePair = findBaseAttribute(restrictionAttribute.getName(), model.getBase());
				if(baseAttributePair == null)
					throw new LexerError("we should have found an attribute we're restricting! what's goin on?");

				restrictionAttribute.setRestriction(baseAttributePair.getModel());
				restrictionAttribute.setRestrictionOwner(baseAttributePair.getParent());
			}

			// find all elements declared in this restriction
			Collection<ElementModel> restrictionElements = new ArrayList<ElementModel>();
			findChildElements(restrictionElements, model.getChildren());
			for(ElementModel restrictionElement : restrictionElements)
			{
				RestrictionPair<ElementModel> baseElementPair = findBaseElement(restrictionElement.getName(), model.getBase());
				if(baseElementPair == null)
					throw new LexerError("we should have found an element we're restricting! what's goin on?");

				restrictionElement.setRestriction(baseElementPair.getModel());
				restrictionElement.setRestrictionOwner(baseElementPair.getParent());
			}
		}
	}

	protected void stage6(RestrictionModel model)
	{
	}

	private static Collection<AttributeModel> findChildAttributes(Collection<Model> children)
	{
		Collection<AttributeModel> attributes = new ArrayList<AttributeModel>();
		for(Model model : children)
			if(model instanceof AttributeModel)
				attributes.add((AttributeModel)model);

		return attributes;
	}

	private static RestrictionPair<AttributeModel> findBaseAttribute(BindingQName name, SimpleTypeModel typeModel)
	{
		if(name == null || typeModel == null || BindingQName.XS.getNamespaceURI().equals(typeModel.getName().getNamespaceURI()))
			return null;

		// FIXME: Can I equate on just the localPart of the QName???
		if(typeModel instanceof ComplexTypeModel)
			for(AttributeModel attribute : ((AttributableModel)typeModel).getAttributes())
				if(name.getLocalPart().equals(attribute.getName().getLocalPart()))
					return new RestrictionPair<AttributeModel>(attribute, typeModel);

		return findBaseAttribute(name, typeModel.getSuperType());
	}

	private static void findChildElements(Collection<ElementModel> elements, Collection<Model> children)
	{
		for(Model model : children)
		{
			if(model instanceof ElementModel)
			{
				elements.add((ElementModel)model);
				continue;
			}

			if(model instanceof MultiplicableModel)
				findChildElements(elements, model.getChildren());
		}
	}

	private static RestrictionPair<ElementModel> findBaseElement(BindingQName name, SimpleTypeModel typeModel)
	{
		if(name == null || typeModel == null || BindingQName.XS.getNamespaceURI().equals(typeModel.getName().getNamespaceURI()))
			return null;

		if(typeModel instanceof ComplexTypeModel)
		{
			Collection<ElementModel> elements = new ArrayList<ElementModel>();
			findChildElements(elements, typeModel.getChildren());

			// FIXME: Can I equate on just the localPart of the QName???
			for(ElementModel element : elements)
				if(name.getLocalPart().equals(element.getName().getLocalPart()))
					return new RestrictionPair<ElementModel>(element, typeModel);
		}

		return findBaseElement(name, typeModel.getSuperType());
	}

	private static class RestrictionPair<T extends RestrictableModel>
	{
		private final T model;
		private final SimpleTypeModel parent;

		public RestrictionPair(T model, SimpleTypeModel parent)
		{
			this.model = model;
			this.parent = parent;
		}

		public T getModel()
		{
			return model;
		}

		public SimpleTypeModel getParent()
		{
			return parent;
		}
	}
}
