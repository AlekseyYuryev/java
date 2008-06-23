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

import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.EnumerablePlan;
import org.safris.xml.generator.compiler.processor.plan.ExtensiblePlan;
import org.safris.xml.generator.compiler.processor.plan.NativeablePlan;
import org.safris.xml.generator.compiler.processor.plan.NestablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.RestrictablePlan;
import org.safris.xml.generator.compiler.runtime.ComplexType;
import org.safris.xml.generator.compiler.runtime.SimpleType;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Formable;
import org.safris.xml.generator.lexer.processor.model.AliasModel;
import org.safris.xml.generator.lexer.processor.model.AnyableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.RestrictableModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.schema.attribute.Form;

public class ElementPlan extends ComplexTypePlan<ElementModel> implements EnumerablePlan, ExtensiblePlan, Formable<Plan>, NativeablePlan, NestablePlan, RestrictablePlan
{
	private final ElementModel element;
	private final boolean ref;
	private final boolean _abstract;
	private final boolean restriction;
	private final boolean fixed;
	private final QName _default;
	private final boolean nillable;
	private final UniqueQName substitutionGroup;

	private UniqueQName typeName;

	private String superClassNameWithType;
	private String superClassNameWithoutType;
	private String declarationGeneric;
	private String declarationRestrictionGeneric = null;

	private boolean nested;
	private boolean isComplexType = false;
	private boolean repeatedExtensionRun = false;
	private ElementPlan repeatedExtension = null;
	private Form formDefault;

	private int minOccurs = 1;
	private int maxOccurs = 1;

	public ElementPlan(ElementModel model, Plan parent)
	{
		super(model, parent);
		ref = (element = getModel()) != model;
		_abstract = model.getAbstract();
		restriction = model.getRestrictionOwner() != null;
		fixed = model.getFixed() != null;
		_default = fixed ? model.getFixed() : model.getDefault();
		nillable = getModel().getNillable() != null && getModel().getNillable();
		substitutionGroup = model.getSubstitutionGroup();
		if(model instanceof AnyableModel)
			return;

		if(element.getSuperType() == null)
			throw new CompilerError("element with no type?");

		typeName = element.getSuperType().getName();
		if(isRestriction())
			superClassNameWithoutType = AliasPlan.getClassName(element.getRestrictionOwner(), null) + "." + JavaBinding.getClassSimpleName((SimpleTypeModel)getModel().getRestriction());
		else
			superClassNameWithoutType = AliasPlan.getClassName(element.getSuperType(), element.getSuperType().getParent());

		superClassNameWithType = superClassNameWithoutType;
		// If we are directly inheriting from another element via the substitutionGroup, then dont add the type
		if(substitutionGroup == null || !substitutionGroup.equals(element.getSuperType().getName()))
		{
			if(isComplexType(element.getSuperType()))
			{
				isComplexType = true;
				superClassNameWithType += "<" + ComplexType.class.getName() + ">";
			}
			else
			{
				isComplexType = false;
				superClassNameWithType += "<" + SimpleType.class.getName() + ">";
			}
		}
		else if(substitutionGroup != null && isComplexType(element.getSuperType()))
			isComplexType = true;
		else
			isComplexType = false;

		if(!ref && element.getParent() instanceof SchemaModel)
			declarationGeneric = superClassNameWithType;
		else
			declarationGeneric = null;

		nested = ref || !(element.getParent() instanceof SchemaModel);

		if(!ref)
			formDefault = element.getFormDefault();
		else
			formDefault = Form.QUALIFIED;
	}

	private boolean isComplexType(SimpleTypeModel simpleType)
	{
		return XSTypeDirectory.ANYTYPE.getNativeBinding().getName().equals(simpleType.getName()) || (simpleType instanceof ComplexTypeModel && !isRestriction());
	}

	/**
	 * States whether this element is a duplicate of an element in the
	 * inheritance hierarchy of the owning parent element or complexType. This
	 * information means that since the element is being repeated twice its
	 * methods will mask those of the first occurance in the parent type.
	 *
	 * @return <code>true</code> if the name of this element exists in the
	 * hierarchy of the parent element or complexType.
	 */
	public final ElementPlan getRepeatedExtension()
	{
		if(repeatedExtensionRun)
			return repeatedExtension;

		repeatedExtension = getParent().getSuperType() != null ? getParent().getSuperType().elementRefExistsInParent(getName()) : null;
		repeatedExtensionRun = true;
		return repeatedExtension;
	}

	public final UniqueQName getSubstitutionGroup()
	{
		return substitutionGroup;
	}

	public final boolean isAbstract()
	{
		return _abstract;
	}

	public final void setMinOccurs(int minOccurs)
	{
		this.minOccurs = minOccurs;
	}

	public final int getMinOccurs()
	{
		return minOccurs;
	}

	public final boolean isNillable()
	{
		return nillable;
	}

	public final QName getDefault()
	{
		return _default;
	}

	public final String getDefaultInstance(Plan parent)
	{
		if(getDefault() == null)
			return null;

		String _default = XSTypeDirectory.QNAME.getNativeBinding().getName().equals(getBaseXSItemTypeName()) ? getDefault().toString() : getDefault().getLocalPart();
		if(hasEnumerations())
			_default = getClassName(parent) + "." + EnumerationPlan.getDeclarationName(getDefault());
		else
			_default = "\"" + _default + "\"";

		String defaultInstance = "new " + getClassName(parent) + "(";
		if(!hasEnumerations() && getNativeFactory() != null)
			defaultInstance += getNativeFactory() + "(" + _default + "))";
		else
			defaultInstance += "" + _default + ")";

		return defaultInstance;
	}

	public final boolean isComplexType()
	{
		return isComplexType;
	}

	public final boolean isRestriction()
	{
		return restriction;
	}

	public final ElementModel getModel()
	{
		return super.getModel().getRef() != null ? super.getModel().getRef() : super.getModel();
	}

	public final void getSuperClassNameWithoutType(String superClassNameWithoutType)
	{
		this.superClassNameWithoutType = superClassNameWithoutType;
	}

	public final String getSuperClassNameWithoutType()
	{
		return superClassNameWithoutType;
	}

	public final void setMaxOccurs(int maxOccurs)
	{
		this.maxOccurs = maxOccurs;
	}

	public final int getMaxOccurs()
	{
		return maxOccurs;
	}

	public final boolean isRef()
	{
		return ref;
	}

	public final boolean writeNativeConstructor()
	{
		if(!isComplexType())
			return true;

		if(getMixed() == null)
			return getMixedType();
		else
			return getMixed();
	}

	public final UniqueQName getTypeName()
	{
		return typeName;
	}

	public final String getDeclarationGeneric(Plan parent)
	{
		if(declarationGeneric != null)
			return declarationGeneric;

		final AliasModel model;
		if(!UniqueQName.XS.getNamespaceURI().equals(getModel().getSuperType().getName().getNamespaceURI()))
			model = getModel().getSuperType();
		else
			model = getModel();

		return AliasPlan.getClassName(model, parent.getModel());
	}

	public final String getDeclarationGenericWithInconvertible(Plan parent)
	{
		if(declarationGeneric != null)
			return declarationGeneric;

		final AliasModel model;
		if(!UniqueQName.XS.getNamespaceURI().equals(getModel().getSuperType().getName().getNamespaceURI()))
			model = getModel().getSuperType();
		else
			model = getModel();

		return AliasPlan.getClassNameWithInconvertible(model, parent.getModel());
	}

	public final String getDeclarationRestrictionGeneric(Plan parent)
	{
		if(!isRestriction())
			return getDeclarationGenericWithInconvertible(parent);

		if(declarationRestrictionGeneric != null)
			return declarationRestrictionGeneric;

		RestrictableModel first = null;
		RestrictableModel prior = getModel();
		while(prior.getRestriction() != null)
		{
			first = prior;
			prior = prior.getRestriction();
		}

		if(AliasPlan.getClassNameWithInconvertible(first.getRestrictionOwner(), null).contains("$pv_addressType"))
		{
			int i = 0;
		}

		return declarationRestrictionGeneric = AliasPlan.getClassNameWithInconvertible(first.getRestrictionOwner(), null) + JavaBinding.getClassSimpleName((Model)first);
	}

	public final String getSuperClassNameWithType()
	{
		return superClassNameWithType;
	}

	public final String getCopyClassName(Plan parent)
	{
		if(!getModel().getSuperType().getName().equals(XSTypeDirectory.ANYSIMPLETYPE.getNativeBinding().getName()))
			return AliasPlan.getClassName(getModel().getSuperType(), null);
		else
		{
			if(getModel().getRef() != null && getModel().getRef().getName() != null)
				return AliasPlan.getClassName(getModel().getRef(), parent != null ? parent.getModel() : null);
			else
				return AliasPlan.getClassName(getModel(), parent != null ? parent.getModel() : null);
		}
	}

	public final boolean isNested()
	{
		return nested;
	}

	public final Form getFormDefault()
	{
		return formDefault;
	}
}
