package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.FieldPlan;
import org.safris.xml.generator.compiler.processor.write.Writer;

public class FieldWriter extends Writer<FieldPlan>
{
	protected void appendDeclaration(StringWriter writer, FieldPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, FieldPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, FieldPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, FieldPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, FieldPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, FieldPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, FieldPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, FieldPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, FieldPlan plan, Plan parent)
	{
	}
}
