package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.IncludePlan;

public class IncludeWriter extends Writer<IncludePlan>
{
	protected void appendDeclaration(StringWriter writer, IncludePlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, IncludePlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, IncludePlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, IncludePlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, IncludePlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, IncludePlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, IncludePlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, IncludePlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, IncludePlan plan, Plan parent)
	{
	}
}
