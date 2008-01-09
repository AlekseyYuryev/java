package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.UniqueModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class UniquePlan extends Plan<UniqueModel>
{
	public UniquePlan(UniqueModel model, Plan parent)
	{
		super(model, parent);
	}
}
