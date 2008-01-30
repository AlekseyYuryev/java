package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.UnionModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class UnionPlan extends Plan<UnionModel>
{
	public UnionPlan(UnionModel model, Plan parent)
	{
		super(model, parent);
	}
}
