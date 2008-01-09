package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.RedefinePlan;

public class RedefineWriter extends Writer<RedefinePlan>
{
	protected void appendDeclaration(StringWriter writer, RedefinePlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, RedefinePlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, RedefinePlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, RedefinePlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, RedefinePlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, RedefinePlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, RedefinePlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, RedefinePlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, RedefinePlan plan, Plan parent)
	{
	}
}
