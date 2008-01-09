package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.ListModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class ListPlan extends Plan<ListModel>
{
	public ListPlan(ListModel model, Plan parent)
	{
		super(model, parent);
	}
}
