package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.HashMap;
import java.util.Map;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.phase.model.element.NotationModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;

public class NotationNormalizer extends Normalizer<NotationModel>
{
	private static final Map<BindingQName,NotationModel> all = new HashMap<BindingQName,NotationModel>();

	public static final NotationModel parseNotation(BindingQName name)
	{
		return all.get(name);
	}

	protected void stage1(NotationModel model)
	{
		if(model.getName() == null)
			return;

		final NotationModel notationModel = NotationNormalizer.parseNotation(model.getName());
		if(notationModel == null)
			NotationNormalizer.all.put(model.getName(), model);
	}

	protected void stage2(NotationModel model)
	{
	}

	protected void stage3(NotationModel model)
	{
	}

	protected void stage4(NotationModel model)
	{
	}

	protected void stage5(NotationModel model)
	{
	}

	protected void stage6(NotationModel model)
	{
	}
}
