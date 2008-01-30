package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.UniqueModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class UniquePlan extends Plan<UniqueModel>
{
	public UniquePlan(UniqueModel model, Plan parent)
	{
		super(model, parent);
	}
}
