package org.safris.xml.generator.lexer.phase.normalize.element;

import java.util.Collection;
import java.util.HashSet;
import org.safris.xml.generator.lexer.phase.model.element.ImportModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;

public class ImportNormalizer extends Normalizer<ImportModel>
{
	private static final Collection<String> messages = new HashSet<String>();

	protected void stage1(ImportModel model)
	{
	}

	protected void stage2(ImportModel model)
	{
		final String message = "Importing " + model.getSchemaLocation() + " for {" + model.getTargetNamespace() + "}";
		if(messages.contains(message))
			return;

		messages.add(message);
		logger().info(message);
	}

	protected void stage3(ImportModel model)
	{
	}

	protected void stage4(ImportModel model)
	{
	}

	protected void stage5(ImportModel model)
	{
	}

	protected void stage6(ImportModel model)
	{
	}
}
