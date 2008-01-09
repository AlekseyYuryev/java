package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.RestrictionModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class RestrictionPlan extends Plan<RestrictionModel>
{
	public RestrictionPlan(RestrictionModel model, Plan parent)
	{
		super(model, parent);
	}
}
