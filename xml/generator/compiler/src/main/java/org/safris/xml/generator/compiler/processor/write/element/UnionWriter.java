package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.UnionPlan;

public class UnionWriter extends Writer<UnionPlan>
{
	protected void appendDeclaration(StringWriter writer, UnionPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, UnionPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, UnionPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, UnionPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, UnionPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, UnionPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, UnionPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, UnionPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, UnionPlan plan, Plan parent)
	{
	}
}
