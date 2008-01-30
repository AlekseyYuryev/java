package org.safris.xml.generator.lexer.processor.normalize.element;

import java.io.File;
import org.safris.commons.util.Files;
import org.safris.commons.util.logging.Logger;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class SchemaNormalizer extends Normalizer<SchemaModel>
{
	private static File CWD = null;

	public SchemaNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(SchemaModel model)
	{
		if(CWD == null)
			CWD = Files.getCwd();

		if(model.getURL() == null)
			return;

		final String display = Files.relativePath(CWD.getAbsoluteFile(), new File(model.getURL().getFile()).getAbsoluteFile());
		// FIXME: Fix the logger.
		Logger.getLogger("").logger().info("Lexing {" + model.getTargetNamespace() + "} from " + display);
	}

	protected void stage2(SchemaModel model)
	{
	}

	protected void stage3(SchemaModel model)
	{
	}

	protected void stage4(SchemaModel model)
	{
	}

	protected void stage5(SchemaModel model)
	{
	}

	protected void stage6(SchemaModel model)
	{
	}
}
