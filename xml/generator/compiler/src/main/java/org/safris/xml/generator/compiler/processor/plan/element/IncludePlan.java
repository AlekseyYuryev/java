package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.IncludeModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class IncludePlan extends Plan<IncludeModel>
{
	public IncludePlan(IncludeModel model, Plan parent)
	{
		super(model, parent);
	}
}
