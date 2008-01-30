package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.ComplexContentPlan;
import org.safris.xml.generator.compiler.processor.write.Writer;

public class ComplexContentWriter extends Writer<ComplexContentPlan>
{
	protected void appendDeclaration(StringWriter writer, ComplexContentPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, ComplexContentPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, ComplexContentPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, ComplexContentPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, ComplexContentPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, ComplexContentPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, ComplexContentPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, ComplexContentPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, ComplexContentPlan plan, Plan parent)
	{
	}
}
