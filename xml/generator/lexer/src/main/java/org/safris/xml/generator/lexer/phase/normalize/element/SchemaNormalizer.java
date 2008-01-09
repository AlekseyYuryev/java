package org.safris.xml.generator.lexer.phase.normalize.element;

import java.io.File;
import org.safris.commons.util.Files;
import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;

public class SchemaNormalizer extends Normalizer<SchemaModel>
{
	private static final File CWD = Files.getCwd();

	protected void stage1(SchemaModel model)
	{
		if(model.getURL() == null)
			return;

		final String display = Files.relativePath(CWD.getAbsoluteFile(), new File(model.getURL().getFile()).getAbsoluteFile());
		logger().info("Lexing {" + model.getTargetNamespace() + "} from " + display);
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
