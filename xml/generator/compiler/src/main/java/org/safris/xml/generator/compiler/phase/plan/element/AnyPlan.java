package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.AnyModel;
import org.safris.xml.generator.compiler.phase.plan.AnyablePlan;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class AnyPlan extends ElementPlan implements AnyablePlan
{
	public AnyPlan(AnyModel model, Plan parent)
	{
		super(model, parent);
	}
}
