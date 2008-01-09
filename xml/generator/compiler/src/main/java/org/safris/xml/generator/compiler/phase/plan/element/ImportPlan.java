package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.lexer.phase.model.element.ImportModel;

public class ImportPlan extends Plan<ImportModel>
{
	public ImportPlan(ImportModel model, Plan parent)
	{
		super(model, parent);
	}
}
