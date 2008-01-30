package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.MinLengthModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class MinLengthPlan extends Plan<MinLengthModel>
{
	public MinLengthPlan(MinLengthModel model, Plan parent)
	{
		super(model, parent);
	}
}
