package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.AnyAttributeModel;
import org.safris.xml.generator.compiler.processor.plan.AnyablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AttributePlan;

public class AnyAttributePlan extends AttributePlan implements AnyablePlan
{
	public AnyAttributePlan(AnyAttributeModel model, Plan parent)
	{
		super(model, parent);
	}
}
