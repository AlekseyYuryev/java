package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.RedefineModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class RedefinePlan extends Plan<RedefineModel>
{
	public RedefinePlan(RedefineModel model, Plan parent)
	{
		super(model, parent);
	}
}
