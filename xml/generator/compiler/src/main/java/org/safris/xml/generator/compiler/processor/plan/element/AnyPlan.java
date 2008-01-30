package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.AnyModel;
import org.safris.xml.generator.compiler.processor.plan.AnyablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class AnyPlan extends ElementPlan implements AnyablePlan
{
	public AnyPlan(AnyModel model, Plan parent)
	{
		super(model, parent);
	}
}
