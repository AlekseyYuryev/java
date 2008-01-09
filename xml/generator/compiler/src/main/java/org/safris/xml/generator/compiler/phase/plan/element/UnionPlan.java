package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.UnionModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class UnionPlan extends Plan<UnionModel>
{
	public UnionPlan(UnionModel model, Plan parent)
	{
		super(model, parent);
	}
}
