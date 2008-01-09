package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.AnyAttributeModel;
import org.safris.xml.generator.compiler.phase.plan.AnyablePlan;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.AttributePlan;

public class AnyAttributePlan extends AttributePlan implements AnyablePlan
{
	public AnyAttributePlan(AnyAttributeModel model, Plan parent)
	{
		super(model, parent);
	}
}
