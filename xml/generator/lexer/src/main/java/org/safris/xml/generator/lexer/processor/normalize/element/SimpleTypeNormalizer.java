package org.safris.xml.generator.lexer.processor.normalize.element;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.processor.BindingQName;

public class SimpleTypeNormalizer extends Normalizer<SimpleTypeModel>
{
	private final Map<BindingQName,SimpleTypeModel> all = new HashMap<BindingQName,SimpleTypeModel>();

	public SimpleTypeNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public SimpleTypeModel parseSimpleType(BindingQName name)
	{
		return all.get(name);
	}

	protected void stage1(SimpleTypeModel model)
	{
		if(model.getName() == null || model.getParent() instanceof RedefineModel)
			return;

		if(parseSimpleType(model.getName()) == null)
			all.put(model.getName(), model);
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
