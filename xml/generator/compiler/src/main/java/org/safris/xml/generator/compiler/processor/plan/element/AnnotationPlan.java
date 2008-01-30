package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.AnnotationModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class AnnotationPlan extends Plan<AnnotationModel>
{
	public AnnotationPlan(AnnotationModel model, Plan parent)
	{
		super(model, parent);
	}
}
