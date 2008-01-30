package org.safris.xml.generator.compiler.processor.plan.element;

import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.EnumerablePlan;
import org.safris.xml.generator.compiler.processor.plan.ExtensiblePlan;
import org.safris.xml.generator.compiler.processor.plan.NativeablePlan;
import org.safris.xml.generator.compiler.processor.plan.NestablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.RestrictablePlan;
import org.safris.xml.generator.compiler.processor.plan.element.SimpleTypePlan;
import org.safris.xml.generator.compiler.runtime.SimpleType;
import org.safris.xml.generator.lexer.processor.model.AnyableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.RestrictableModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;
import org.safris.xml.generator.processor.BindingQName;

public class AttributePlan extends SimpleTypePlan<AttributeModel> implements EnumerablePlan, ExtensiblePlan, NativeablePlan, NestablePlan, RestrictablePlan
{
	private final AttributeModel attribute;
	private final boolean ref;
	private final boolean restriction;
	private final boolean fixed;
	private final QName _default;
	private final Use use;

	private String thisClassNameWithType = null;
	private String declarationRestrictionGeneric = null;
	private String declarationRestrictionSimpleName = null;

	private String superClassNameWithType = null;
	private String superClassNameWithoutType = null;

	private boolean nested = false;
	private Form formDefault = null;

	public AttributePlan(AttributeModel model, Plan parent)
	{
		super(model, parent);
		ref = (attribute = getModel()) != model;
		restriction = model.getRestrictionOwner() != null;
		fixed = model.getFixed() != null;
		_default = fixed ? model.getFixed() : model.getDefault();
		use = model.getUse();
		if(model instanceof AnyableModel)
			return;

		if(isRestriction())
			superClassNameWithoutType = AliasPlan.getClassName(attribute.getRestrictionOwner(), null) + "." + JavaBinding.getClassSimpleName((SimpleTypeModel)getModel().getRestriction());
		else
			superClassNameWithoutType = AliasPlan.getClassName(attribute.getSuperType(), attribute.getSuperType().getParent());

		if(!(attribute.getSuperType() instanceof AttributeModel) && !isRestriction())
			superClassNameWithType = superClassNameWithoutType + "<" + SimpleType.class.getName() + ">";
		else
			superClassNameWithType = superClassNameWithoutType;

		if(!ref && attribute.getParent() instanceof SchemaModel)
			thisClassNameWithType = superClassNameWithType;
		else
			thisClassNameWithType = null;

		nested = ref || !(attribute.getParent() instanceof SchemaModel);
		if(!ref)
			formDefault = attribute.getAttributeFormDefault();
		else
			formDefault = Form.QUALIFIED;
	}

	public final Use getUse()
	{
		return use;
	}

	public final boolean isFixed()
	{
		return fixed;
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

	public final String getFixedInstanceCall(Plan parent)
	{
		if(!fixed)
			return "";

		String defaultInstance = getDefaultInstance(parent);
		if(isRestriction())
			return "super.set" + getDeclarationRestrictionSimpleName() + "(" + defaultInstance + ");\n";
		else
			return getInstanceName() + ".setValue(" + defaultInstance + ");\n";
	}

	public final QName getDefault()
	{
		return _default;
	}

	public final boolean isRestriction()
	{
		return restriction;
	}

	public final AttributeModel getModel()
	{
		return super.getModel().getRef() != null ? super.getModel().getRef() : super.getModel();
	}

	public final String getSuperClassNameWithoutType()
	{
		return superClassNameWithoutType;
	}

	public final boolean isRef()
	{
		return ref;
	}

	public final String getThisClassNameWithType(Plan parent)
	{
		if(thisClassNameWithType != null)
			return thisClassNameWithType;

		if(!BindingQName.XS.getNamespaceURI().equals(getModel().getSuperType().getName().getNamespaceURI()))
			return AliasPlan.getClassName(getModel().getSuperType(), parent.getModel());
		else
			return AliasPlan.getClassName(getModel(), parent.getModel());
	}

	public final String getDeclarationRestrictionGeneric(Plan parent)
	{
		if(!isRestriction())
			return getThisClassNameWithType(parent);

		if(declarationRestrictionGeneric != null)
			return declarationRestrictionGeneric;

		RestrictableModel first = null;
		RestrictableModel prior = getModel();
		while(prior.getRestriction() != null)
		{
			first = prior;
			prior = prior.getRestriction();
		}

		return declarationRestrictionGeneric = AliasPlan.getClassName(first.getRestrictionOwner(), null) + "." + JavaBinding.getClassSimpleName((Model)prior);
	}

	public final String getDeclarationRestrictionSimpleName()
	{
		if(!isRestriction())
			return getClassSimpleName();

		if(declarationRestrictionSimpleName != null)
			return declarationRestrictionSimpleName;

		RestrictableModel prior = getModel();
		while(prior.getRestriction() != null)
			prior = prior.getRestriction();

		return declarationRestrictionSimpleName = JavaBinding.getClassSimpleName((Model)prior);
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
