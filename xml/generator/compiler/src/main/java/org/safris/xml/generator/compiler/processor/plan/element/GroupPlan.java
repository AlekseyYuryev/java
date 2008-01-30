package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.GroupModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class GroupPlan extends Plan<GroupModel>
{
	public GroupPlan(GroupModel model, Plan parent)
	{
		super(model, parent);
	}
}
