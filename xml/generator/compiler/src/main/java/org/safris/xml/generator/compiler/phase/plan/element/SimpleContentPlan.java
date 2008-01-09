package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.SimpleContentModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class SimpleContentPlan extends Plan<SimpleContentModel>
{
	public SimpleContentPlan(SimpleContentModel model, Plan parent)
	{
		super(model, parent);
	}
}
