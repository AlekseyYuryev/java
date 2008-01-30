package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.AttributeGroupModel;
import org.safris.xml.generator.compiler.processor.plan.NamedPlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class AttributeGroupPlan extends NamedPlan<AttributeGroupModel>
{
	public AttributeGroupPlan(AttributeGroupModel model, Plan parent)
	{
		super(model, parent);
	}
}
