package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.AttributeGroupModel;
import org.safris.xml.generator.compiler.phase.plan.NamedPlan;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class AttributeGroupPlan extends NamedPlan<AttributeGroupModel>
{
	public AttributeGroupPlan(AttributeGroupModel model, Plan parent)
	{
		super(model, parent);
	}
}
