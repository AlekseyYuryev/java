package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.GroupPlan;

public class GroupWriter extends Writer<GroupPlan>
{
	protected void appendDeclaration(StringWriter writer, GroupPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, GroupPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, GroupPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, GroupPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, GroupPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, GroupPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, GroupPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, GroupPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, GroupPlan plan, Plan parent)
	{
	}
}
