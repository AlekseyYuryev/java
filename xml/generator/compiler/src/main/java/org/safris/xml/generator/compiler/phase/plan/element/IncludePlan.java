package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.IncludeModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class IncludePlan extends Plan<IncludeModel>
{
	public IncludePlan(IncludeModel model, Plan parent)
	{
		super(model, parent);
	}
}
