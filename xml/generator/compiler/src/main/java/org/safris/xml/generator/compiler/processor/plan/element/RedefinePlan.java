package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class RedefinePlan extends Plan<RedefineModel>
{
	public RedefinePlan(RedefineModel model, Plan parent)
	{
		super(model, parent);
	}
}
