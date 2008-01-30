package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.PatternModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class PatternPlan extends Plan<PatternModel>
{
	private final String value;

	public PatternPlan(PatternModel model, Plan parent)
	{
		super(model, parent);
		this.value = model.getValue();
	}

	public String getValue()
	{
		return value;
	}
}
