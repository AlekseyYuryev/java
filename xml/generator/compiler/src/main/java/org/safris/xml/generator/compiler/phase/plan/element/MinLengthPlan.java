package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.MinLengthModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class MinLengthPlan extends Plan<MinLengthModel>
{
	public MinLengthPlan(MinLengthModel model, Plan parent)
	{
		super(model, parent);
	}
}
