package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.MinLengthPlan;

public class MinLengthWriter extends Writer<MinLengthPlan>
{
	protected void appendDeclaration(StringWriter writer, MinLengthPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, MinLengthPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, MinLengthPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, MinLengthPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, MinLengthPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, MinLengthPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, MinLengthPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, MinLengthPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, MinLengthPlan plan, Plan parent)
	{
	}
}
