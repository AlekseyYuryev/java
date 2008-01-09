package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.HasPropertyModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class HasPropertyPlan extends Plan<HasPropertyModel>
{
	public HasPropertyPlan(HasPropertyModel model, Plan parent)
	{
		super(model, parent);
	}
}
