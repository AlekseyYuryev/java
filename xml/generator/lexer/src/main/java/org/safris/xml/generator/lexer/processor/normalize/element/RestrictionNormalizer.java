/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.xml.generator.lexer.processor.normalize.element;

import java.util.ArrayList;
import java.util.Collection;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.AttributableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.MultiplicableModel;
import org.safris.xml.generator.lexer.processor.model.RestrictableModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.ListModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.model.element.RestrictionModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.UnionModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.lang.UniqueQName;

public class RestrictionNormalizer extends Normalizer<RestrictionModel>
{
	private final ElementNormalizer elementNormalizer = (ElementNormalizer)getDirectory().lookup(ElementModel.class);
	private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);
	private final AttributeNormalizer attributeNormalizer = (AttributeNormalizer)getDirectory().lookup(AttributeModel.class);
	private final ComplexTypeNormalizer complexTypeNormalizer = (ComplexTypeNormalizer)getDirectory().lookup(ComplexTypeModel.class);

	public RestrictionNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

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
			base = simpleTypeNormalizer.parseSimpleType(model.getBase().getName());
			if(base == null)
				base = complexTypeNormalizer.parseComplexType(model.getBase().getName());

			if(base == null)
			{
				if(!UniqueQName.XS.getNamespaceURI().equals(model.getBase().getName().getNamespaceURI()))
					throw new LexerError("base == null for " + model.getBase().getName());

				base = SimpleTypeModel.Undefined.parseSimpleType(model.getBase().getName());
			}
		}
		else if(model.getBase() instanceof ComplexTypeModel.Reference)
		{
			base = complexTypeNormalizer.parseComplexType(model.getBase().getName());
			if(base == null)
			{
				if(!UniqueQName.XS.getNamespaceURI().equals(model.getBase().getName().getNamespaceURI()))
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
						element = elementNormalizer.parseElement(((Nameable)parent).getName());

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
						attribute = attributeNormalizer.parseAttribute(((Nameable)parent).getName());

					if(attribute == null)
						throw new LexerError("attribute == null");

					attribute.setSuperType(base);
					attribute.setRestriction(true);
				}
				else if(parent instanceof SimpleTypeModel)
				{
					SimpleTypeModel type = simpleTypeNormalizer.parseSimpleType(((Nameable)parent).getName());
					if(type == null)
						type = complexTypeNormalizer.parseComplexType(((Nameable)parent).getName());

					if(type == null)
						throw new LexerError("type == null for " + ((Nameable)parent).getName());

					if(type instanceof ComplexTypeModel && type.getSuperType() != null)
						break;

					// Update the superType and restriction flag of the reference model
					type.setSuperType(base);
					type.setRestriction(true);

					// Update the superType and restriction flag of this model
					((SimpleTypeModel)parent).setSuperType(base);
					((SimpleTypeModel)parent).setRestriction(true);
				}
				else
					throw new LexerError(((Nameable)parent).getName().toString());

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
		if(model.getBase() == null || UniqueQName.XS.getNamespaceURI().equals(model.getBase().getName().getNamespaceURI()))
			return;

		if(!(model.getBase() instanceof ComplexTypeModel))
			return;

		// handle all attributes
		final Collection<AttributeModel> restrictionAttributes = findChildAttributes(model.getChildren());
		for(AttributeModel restrictionAttribute : restrictionAttributes)
		{
			final RestrictionPair<AttributeModel> baseAttributePair = findBaseAttribute(restrictionAttribute.getName(), model.getBase());
			if(baseAttributePair == null)
				throw new LexerError("we should have found an attribute we're restricting! what's goin on?");

			restrictionAttribute.setRestriction(baseAttributePair.getModel());
			restrictionAttribute.setRestrictionOwner(baseAttributePair.getParent());
		}

		// find all elements declared in this restriction
		final Collection<ElementModel> restrictionElements = new ArrayList<ElementModel>();
		findChildElements(restrictionElements, model.getChildren());
		for(ElementModel restrictionElement : restrictionElements)
		{
			final RestrictionPair<ElementModel> baseElementPair = findBaseElement(restrictionElement.getName(), model.getBase());
			if(baseElementPair == null)
				throw new LexerError("we should have found an element we're restricting! what's goin on?");

			restrictionElement.setRestriction(baseElementPair.getModel());
			restrictionElement.setRestrictionOwner(baseElementPair.getParent());
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

	private static RestrictionPair<AttributeModel> findBaseAttribute(UniqueQName name, SimpleTypeModel typeModel)
	{
		if(name == null || typeModel == null || UniqueQName.XS.getNamespaceURI().equals(typeModel.getName().getNamespaceURI()))
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

	private static RestrictionPair<ElementModel> findBaseElement(UniqueQName name, SimpleTypeModel typeModel)
	{
		if(name == null || typeModel == null || UniqueQName.XS.getNamespaceURI().equals(typeModel.getName().getNamespaceURI()))
			return null;

		if(typeModel instanceof ComplexTypeModel)
		{
			final Collection<ElementModel> elements = new ArrayList<ElementModel>();
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
