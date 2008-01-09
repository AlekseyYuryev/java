package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.FieldModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class FieldPlan extends Plan<FieldModel>
{
	public FieldPlan(FieldModel model, Plan parent)
	{
		super(model, parent);
	}
}
