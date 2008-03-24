package org.safris.xml.generator.lexer.processor.normalize.element;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.processor.model.element.NotationModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.lang.UniqueQName;

public class NotationNormalizer extends Normalizer<NotationModel>
{
	private final Map<UniqueQName,NotationModel> all = new HashMap<UniqueQName,NotationModel>();

	public NotationNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	public NotationModel parseNotation(UniqueQName name)
	{
		return all.get(name);
	}

	protected void stage1(NotationModel model)
	{
		if(model.getName() == null)
			return;

		final NotationModel notationModel = parseNotation(model.getName());
		if(notationModel == null)
			all.put(model.getName(), model);
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
