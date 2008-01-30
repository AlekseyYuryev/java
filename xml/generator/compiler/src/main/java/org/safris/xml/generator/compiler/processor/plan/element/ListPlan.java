package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.ListModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class ListPlan extends Plan<ListModel>
{
	public ListPlan(ListModel model, Plan parent)
	{
		super(model, parent);
	}
}
