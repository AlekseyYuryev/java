package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.MaxLengthModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class MaxLengthPlan extends Plan<MaxLengthModel>
{
	public MaxLengthPlan(MaxLengthModel model, Plan parent)
	{
		super(model, parent);
	}
}