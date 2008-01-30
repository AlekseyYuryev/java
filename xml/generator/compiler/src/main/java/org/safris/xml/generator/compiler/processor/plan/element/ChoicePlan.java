package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.ChoiceModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class ChoicePlan extends Plan<ChoiceModel>
{
	public ChoicePlan(ChoiceModel model, Plan parent)
	{
		super(model, parent);
	}
}
