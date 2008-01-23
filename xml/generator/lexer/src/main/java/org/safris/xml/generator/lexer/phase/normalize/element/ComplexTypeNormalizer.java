package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.HashMap;
import java.util.Map;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.phase.model.element.RedefineModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.module.phase.StaticReferenceManager;

public class ComplexTypeNormalizer extends Normalizer<ComplexTypeModel>
{
	protected static final Map<BindingQName,ComplexTypeModel> all = StaticReferenceManager.manageMap(new HashMap<BindingQName,ComplexTypeModel>());

	public static ComplexTypeModel parseComplexType(BindingQName name)
	{
		return all.get(name);
	}

	public static void registerDefaultType(ComplexTypeModel model)
	{
		if(model.getName() == null)
			return;

		if(all.containsKey(model.getName()))
			throw new LexerError("duplicate entry attempted for: " + model.getName());

		all.put(model.getName(), model);
	}

	protected void stage1(ComplexTypeModel model)
	{
		if(model.getName() == null || model.getParent() instanceof RedefineModel)
			return;

		ComplexTypeModel complexTypeModel = ComplexTypeNormalizer.parseComplexType(model.getName());
		if(complexTypeModel == null)
			ComplexTypeNormalizer.all.put(model.getName(), model);
	}

	protected void stage2(ComplexTypeModel model)
	{
	}

	protected void stage3(ComplexTypeModel model)
	{
	}

	protected void stage4(ComplexTypeModel model)
	{
	}

	protected void stage5(ComplexTypeModel model)
	{
	}

	protected void stage6(ComplexTypeModel model)
	{
		if(model.getName() == null)
			return;

		if(model.getSuperType() == null)
			model.setSuperType(ComplexTypeModel.Undefined.parseComplexType(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anySimpleType")));
	}
}
