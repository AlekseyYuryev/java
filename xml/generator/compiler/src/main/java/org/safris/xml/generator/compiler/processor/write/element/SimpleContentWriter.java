package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.SimpleContentPlan;

public class SimpleContentWriter extends Writer<SimpleContentPlan>
{
	protected void appendDeclaration(StringWriter writer, SimpleContentPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, SimpleContentPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, SimpleContentPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, SimpleContentPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, SimpleContentPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, SimpleContentPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, SimpleContentPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, SimpleContentPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, SimpleContentPlan plan, Plan parent)
	{
	}
}
