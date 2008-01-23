package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.Collection;
import java.util.HashSet;
import org.safris.xml.generator.lexer.phase.model.element.IncludeModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;

public class IncludeNormalizer extends Normalizer<IncludeModel>
{
	private final Collection<String> messages = new HashSet<String>();

	protected void stage1(IncludeModel model)
	{
	}

	protected void stage2(IncludeModel model)
	{
		final String message = "Including " + model.getSchemaLocation() + " for {" + model.getTargetNamespace() + "}";
		if(messages.contains(message))
			return;

		messages.add(message);
		logger().info(message);
	}

	protected void stage3(IncludeModel model)
	{
	}

	protected void stage4(IncludeModel model)
	{
	}

	protected void stage5(IncludeModel model)
	{
	}

	protected void stage6(IncludeModel model)
	{
	}
}
