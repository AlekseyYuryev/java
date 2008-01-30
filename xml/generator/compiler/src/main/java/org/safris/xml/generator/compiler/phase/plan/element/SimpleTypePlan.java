package org.safris.xml.generator.compiler.phase.plan.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.phase.plan.AliasPlan;
import org.safris.xml.generator.compiler.phase.plan.EnumerablePlan;
import org.safris.xml.generator.compiler.phase.plan.ExtensiblePlan;
import org.safris.xml.generator.compiler.phase.plan.NativeablePlan;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.EnumerationPlan;
import org.safris.xml.generator.compiler.phase.plan.element.PatternPlan;
import org.safris.xml.generator.lexer.phase.model.AnyableModel;
import org.safris.xml.generator.lexer.phase.model.EnumerableModel;
import org.safris.xml.generator.lexer.phase.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.processor.BindingQName;

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

		BindingQName name1;
		BindingQName name2;

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

			if(BindingQName.XS.getNamespaceURI().equals(itemType.getName().getNamespaceURI()))
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
			if(type.getName() != null && BindingQName.XS.getNamespaceURI().equals(type.getName().getNamespaceURI()))
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
	private boolean list;
	private String baseNonXSTypeClassName = null;

	private BindingQName baseNonXSTypeName = null;
	private BindingQName baseXSTypeName = null;
	private BindingQName baseXSItemTypeName = null;

	public SimpleTypePlan(T model, Plan parent)
	{
		super(model.getRedefine() != null ? (T)model.getRedefine() : model, parent);
		this.list = digList(getModel());
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

		superClassNameWithoutType = AliasPlan.getClassName(model.getSuperType(), null);
		superClassNameWithType = superClassNameWithoutType + "<T>";

		final XSTypeDirectory baseXSItemTypeDirectory = XSTypeDirectory.parseType(getBaseXSItemTypeName());
		if(baseXSItemTypeDirectory == null)
			throw new CompilerError("Should always be able to resolve the type for name: " + getName());

		nativeItemClassName = baseXSItemTypeDirectory.getNativeBinding().getNativeClass().getCls().getName();
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

	public final BindingQName getBaseXSTypeName()
	{
		return baseXSTypeName;
	}

	public final BindingQName getBaseXSItemTypeName()
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
}
