package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.ChoiceModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class ChoicePlan extends Plan<ChoiceModel>
{
	public ChoicePlan(ChoiceModel model, Plan parent)
	{
		super(model, parent);
	}
}
