package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AnnotationPlan;
import org.safris.xml.generator.compiler.processor.write.Writer;

public class AnnotationWriter extends Writer<AnnotationPlan>
{
	protected void appendDeclaration(StringWriter writer, AnnotationPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, AnnotationPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, AnnotationPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, AnnotationPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, AnnotationPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, AnnotationPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, AnnotationPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, AnnotationPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, AnnotationPlan plan, Plan parent)
	{
	}
}
