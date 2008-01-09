package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.SequenceModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class SequencePlan extends Plan<SequenceModel>
{
	public SequencePlan(SequenceModel model, Plan parent)
	{
		super(model, parent);
	}
}
