package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.MaxLengthModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class MaxLengthPlan extends Plan<MaxLengthModel>
{
	public MaxLengthPlan(MaxLengthModel model, Plan parent)
	{
		super(model, parent);
	}
}
