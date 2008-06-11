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

package org.safris.xml.generator.compiler.processor.plan.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.EnumerablePlan;
import org.safris.xml.generator.compiler.processor.plan.ExtensiblePlan;
import org.safris.xml.generator.compiler.processor.plan.NamedPlan;
import org.safris.xml.generator.compiler.processor.plan.NativeablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.AnyableModel;
import org.safris.xml.generator.lexer.processor.model.EnumerableModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;

public class SimpleTypePlan<T extends SimpleTypeModel> extends AliasPlan<T> implements EnumerablePlan, ExtensiblePlan, NativeablePlan
{
	private static SimpleTypeModel getGreatestCommonType(Collection<SimpleTypeModel> types)
	{
		if(types == null || types.size() == 0)
			return null;

		if(types.size() == 1)
			return types.iterator().next();

		final Iterator<SimpleTypeModel> iterator = types.iterator();
		SimpleTypeModel gct = getGreatestCommonType(iterator.next(), iterator.next());
		while(iterator.hasNext() && gct != null)
			gct = getGreatestCommonType(gct, iterator.next());

		return gct;
	}

	private static SimpleTypeModel getGreatestCommonType(SimpleTypeModel model1, SimpleTypeModel model2)
	{
		if(model1 == null || model2 == null)
			return null;

		UniqueQName name1;
		UniqueQName name2;

		// First try to see if we can match using the knowledge of type inheritance
		SimpleTypeModel type1 = model1;
		do
		{
			name1 = type1.getName();
			SimpleTypeModel type2 = model2;
			do
			{
				name2 = type2.getName();
				if(type1.getName().equals(type2.getName()))
					return type1;
			}
			while((type2 = type2.getSuperType()) != null || (type2 = ComplexTypeModel.Undefined.parseComplexType(XSTypeDirectory.lookupSuperType(name2))) != null);
		}
		while((type1 = type1.getSuperType()) != null || (type1 = ComplexTypeModel.Undefined.parseComplexType(XSTypeDirectory.lookupSuperType(name1))) != null);

		return null;
	}

	private static boolean digList(SimpleTypeModel model)
	{
		boolean list = model.isList();
		if(!list)
		{
			SimpleTypeModel type = model;
			while((type = type.getSuperType()) != null)
				if(list = type.isList())
					break;
		}

		return list;
	}

	private static SimpleTypeModel digBaseXSItemTypeName(SimpleTypeModel model)
	{
		SimpleTypeModel itemType;
		SimpleTypeModel type = model;
		do
		{
			itemType = getGreatestCommonType(type.getItemTypes());
			if(itemType == null)
				continue;

			if(UniqueQName.XS.getNamespaceURI().equals(itemType.getName().getNamespaceURI()))
				return itemType;
		}
		while((type = type.getSuperType()) != null);

		return null;
	}

	private static SimpleTypeModel digBaseNonXSType(SimpleTypeModel model)
	{
		SimpleTypeModel type = model;
		SimpleTypeModel retval = null;
		do
		{
			if(type.getName() != null && UniqueQName.XS.getNamespaceURI().equals(type.getName().getNamespaceURI()))
				break;

			retval = type;
		}
		while((type = type.getSuperType()) != null);

		return retval;
	}

	private String superClassNameWithType;
	private String superClassNameWithoutType;

	private LinkedHashSet<PatternPlan> patterns = null;
	private LinkedHashSet<EnumerationPlan> enumerations = null;
	private Boolean hasEnumerations;
	private Boolean hasSuperEnumerations;

	private String nativeItemClassName = null;
	private String nativeInterface = null;
	private String nativeImplementation = null;
	private String nativeFactory = null;
	private boolean list = false;
	private String baseNonXSTypeClassName = null;

	private UniqueQName superTypeName = null;
	private UniqueQName baseNonXSTypeName = null;
	private UniqueQName baseXSTypeName = null;
	private UniqueQName baseXSItemTypeName = null;

	private boolean parsedSuperType = false;
	private NamedPlan superType = null;

	public SimpleTypePlan(T model, Plan parent)
	{
		super(model.getRedefine() != null ? (T)model.getRedefine() : model, parent);
		if(model instanceof AnyableModel)
			return;

		// Gets the XS pre-simpleType name of the type
		final SimpleTypeModel baseNonXSType = digBaseNonXSType(model);
		if(baseNonXSType != null)
			baseNonXSTypeName = baseNonXSType.getName();

		// Gets the XS simpleType name of the itemType
		final SimpleTypeModel baseXSItemType = digBaseXSItemTypeName(getModel());
		if(baseXSItemType != null)
			baseXSItemTypeName = baseXSItemType.getName();
		else
			baseXSItemTypeName = baseNonXSType.getSuperType().getName();

		baseNonXSTypeClassName = JavaBinding.getClassName(baseNonXSType);

		superTypeName = model.getSuperType().getName();
		superClassNameWithoutType = AliasPlan.getClassName(model.getSuperType(), null);
		superClassNameWithType = superClassNameWithoutType + "<T>";

		final XSTypeDirectory baseXSItemTypeDirectory = XSTypeDirectory.parseType(getBaseXSItemTypeName());
		if(baseXSItemTypeDirectory == null)
			throw new CompilerError("Should always be able to resolve the type for name: " + getName());

		if(this.list = baseXSItemTypeDirectory.getNativeBinding().isList())
			nativeItemClassName = baseXSItemTypeDirectory.getNativeBinding().getNativeClass().getType().getName();
		else
		{
			this.list = digList(getModel());
			nativeItemClassName = baseXSItemTypeDirectory.getNativeBinding().getNativeClass().getCls().getName();
		}

		nativeFactory = baseXSItemTypeDirectory.getNativeFactory();
		if(isList())
		{
			nativeInterface = List.class.getName() + "<" + nativeItemClassName + ">";
			nativeImplementation = ArrayList.class.getName() + "<" + nativeItemClassName + ">";
		}
		else
		{
			nativeInterface = nativeItemClassName;
			nativeImplementation = nativeItemClassName;
		}
	}

	public final String getBaseNonXSTypeClassName()
	{
		return baseNonXSTypeClassName;
	}

	public final UniqueQName getBaseXSTypeName()
	{
		return baseXSTypeName;
	}

	public final UniqueQName getBaseXSItemTypeName()
	{
		return baseXSItemTypeName;
	}

	public final boolean isList()
	{
		return list;
	}

	public final String getNativeItemClassNameInterface()
	{
		return nativeInterface;
	}

	public final String getNativeItemClassNameImplementation()
	{
		return nativeImplementation;
	}

	public final String getNativeFactory()
	{
		return nativeFactory;
	}

	public String getSuperClassNameWithoutType()
	{
		return superClassNameWithoutType;
	}

	public final LinkedHashSet<EnumerationPlan> getEnumerations()
	{
		if(enumerations != null)
			return enumerations;

		return enumerations = Plan.<EnumerationPlan>analyze(getModel().getEnumerations(), this);
	}

	public final String getNativeItemClassName()
	{
		return nativeItemClassName;
	}

	public final boolean hasEnumerations()
	{
		if(hasEnumerations != null)
			return hasEnumerations;

		return hasEnumerations = hasEnumerations(getModel());
	}

	public final boolean hasSuperEnumerations()
	{
		if(hasSuperEnumerations != null)
			return hasSuperEnumerations;

		return hasSuperEnumerations = getModel().getSuperType() instanceof EnumerableModel ? hasEnumerations((EnumerableModel)getModel().getSuperType()) : false;
	}

	public final Collection<PatternPlan> getPatterns()
	{
		if(patterns != null)
			return patterns;

		return patterns = Plan.<PatternPlan>analyze(getModel().getPatterns(), this);
	}

	public String getSuperClassNameWithType()
	{
		return superClassNameWithType;
	}

	public Plan getSuperType()
	{
		if(parsedSuperType)
			return superType;

		superType = parseNamedPlan(superTypeName);
		if(superType == this)
			return superType = null;

		parsedSuperType = true;
		return superType;
	}
}
