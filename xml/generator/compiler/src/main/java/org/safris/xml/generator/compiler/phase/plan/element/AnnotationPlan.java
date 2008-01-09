package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.AnnotationModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class AnnotationPlan extends Plan<AnnotationModel>
{
	public AnnotationPlan(AnnotationModel model, Plan parent)
	{
		super(model, parent);
	}
}
