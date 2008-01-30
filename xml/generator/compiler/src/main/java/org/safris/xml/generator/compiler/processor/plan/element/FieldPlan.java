package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.FieldModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class FieldPlan extends Plan<FieldModel>
{
	public FieldPlan(FieldModel model, Plan parent)
	{
		super(model, parent);
	}
}
