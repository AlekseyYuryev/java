package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.processor.model.element.ImportModel;

public class ImportPlan extends Plan<ImportModel>
{
	public ImportPlan(ImportModel model, Plan parent)
	{
		super(model, parent);
	}
}
