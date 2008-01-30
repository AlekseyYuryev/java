package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.SequenceModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class SequencePlan extends Plan<SequenceModel>
{
	public SequencePlan(SequenceModel model, Plan parent)
	{
		super(model, parent);
	}
}
