package org.safris.xml.generator.lexer.processor.normalize.element;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.lang.UniqueQName;

public class ComplexTypeNormalizer extends Normalizer<ComplexTypeModel>
{
	protected final Map<UniqueQName,ComplexTypeModel> all = new HashMap<UniqueQName,ComplexTypeModel>();

	public ComplexTypeNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public ComplexTypeModel parseComplexType(UniqueQName name)
	{
		return all.get(name);
	}

	protected void stage1(ComplexTypeModel model)
	{
		if(model.getName() == null || model.getParent() instanceof RedefineModel)
			return;

		ComplexTypeModel complexTypeModel = parseComplexType(model.getName());
		if(complexTypeModel == null)
			all.put(model.getName(), model);
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
			model.setSuperType(ComplexTypeModel.Undefined.parseComplexType(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType")));
	}
}
