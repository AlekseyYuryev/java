package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.PatternPlan;

public class PatternWriter extends Writer<PatternPlan>
{
	protected void appendDeclaration(StringWriter writer, PatternPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, PatternPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, PatternPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, PatternPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, PatternPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, PatternPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, PatternPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, PatternPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, PatternPlan plan, Plan parent)
	{
	}
}
