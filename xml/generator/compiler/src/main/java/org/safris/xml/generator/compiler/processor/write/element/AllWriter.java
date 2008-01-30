package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AllPlan;
import org.safris.xml.generator.compiler.processor.write.Writer;

public class AllWriter extends Writer<AllPlan>
{
	protected void appendDeclaration(StringWriter writer, AllPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, AllPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, AllPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, AllPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, AllPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, AllPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, AllPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, AllPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, AllPlan plan, Plan parent)
	{
	}
}
