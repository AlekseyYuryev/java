package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.ListPlan;

public class ListWriter extends Writer<ListPlan>
{
	protected void appendDeclaration(StringWriter writer, ListPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, ListPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, ListPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, ListPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, ListPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, ListPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, ListPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, ListPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, ListPlan plan, Plan parent)
	{
	}
}
