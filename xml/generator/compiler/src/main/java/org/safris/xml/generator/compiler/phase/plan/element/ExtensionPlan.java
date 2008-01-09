package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.ExtensionModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class ExtensionPlan extends Plan<ExtensionModel>
{
	public ExtensionPlan(ExtensionModel model, Plan parent)
	{
		super(model, parent);
	}
}
