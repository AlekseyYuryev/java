package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.HashMap;
import java.util.Map;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.phase.model.element.RedefineModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;

public class SimpleTypeNormalizer extends Normalizer<SimpleTypeModel>
{
	private static final Map<BindingQName,SimpleTypeModel> all = new HashMap<BindingQName,SimpleTypeModel>();

	public static SimpleTypeModel parseSimpleType(BindingQName name)
	{
		return all.get(name);
	}

	protected void stage1(SimpleTypeModel model)
	{
		if(model.getName() == null || model.getParent() instanceof RedefineModel)
			return;

		if(SimpleTypeNormalizer.parseSimpleType(model.getName()) == null)
			SimpleTypeNormalizer.all.put(model.getName(), model);
	}

	protected void stage2(SimpleTypeModel model)
	{
	}

	protected void stage3(SimpleTypeModel model)
	{
	}

	protected void stage4(SimpleTypeModel model)
	{
	}

	protected void stage5(SimpleTypeModel model)
	{
	}

	protected void stage6(SimpleTypeModel model)
	{
	}
}
