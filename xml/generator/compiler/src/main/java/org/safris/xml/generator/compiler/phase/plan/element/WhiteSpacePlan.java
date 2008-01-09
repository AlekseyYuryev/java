package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.WhiteSpaceModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class WhiteSpacePlan extends Plan<WhiteSpaceModel>
{
	public WhiteSpacePlan(WhiteSpaceModel model, Plan parent)
	{
		super(model, parent);
	}
}
