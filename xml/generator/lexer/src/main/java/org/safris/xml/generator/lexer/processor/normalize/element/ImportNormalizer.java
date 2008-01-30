package org.safris.xml.generator.lexer.processor.normalize.element;

import java.util.Collection;
import java.util.HashSet;
import org.safris.commons.util.logging.Logger;
import org.safris.xml.generator.lexer.processor.model.element.ImportModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class ImportNormalizer extends Normalizer<ImportModel>
{
	private final Collection<String> messages = new HashSet<String>();

	public ImportNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(ImportModel model)
	{
	}

	protected void stage2(ImportModel model)
	{
		final String message = "Importing " + model.getSchemaLocation() + " for {" + model.getTargetNamespace() + "}";
		if(messages.contains(message))
			return;

		messages.add(message);
		// FIXME: Fix the logger.
		Logger.getLogger("").logger().info(message);
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
