package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.GroupModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class GroupPlan extends Plan<GroupModel>
{
	public GroupPlan(GroupModel model, Plan parent)
	{
		super(model, parent);
	}
}
