package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.UniquePlan;

public class UniqueWriter extends Writer<UniquePlan>
{
	protected void appendDeclaration(StringWriter writer, UniquePlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, UniquePlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, UniquePlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, UniquePlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, UniquePlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, UniquePlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, UniquePlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, UniquePlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, UniquePlan plan, Plan parent)
	{
	}
}
