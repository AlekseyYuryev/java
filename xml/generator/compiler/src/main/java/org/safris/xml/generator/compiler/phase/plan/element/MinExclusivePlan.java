package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.MinExclusiveModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class MinExclusivePlan extends Plan<MinExclusiveModel>
{
	public MinExclusivePlan(MinExclusiveModel model, Plan parent)
	{
		super(model, parent);
	}
}
