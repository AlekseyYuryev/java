package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.KeyrefModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class KeyrefPlan extends Plan<KeyrefModel>
{
	public KeyrefPlan(KeyrefModel model, Plan parent)
	{
		super(model, parent);
	}
}
