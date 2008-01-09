package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.SelectorPlan;

public class SelectorWriter extends Writer<SelectorPlan>
{
	protected void appendDeclaration(StringWriter writer, SelectorPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, SelectorPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, SelectorPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, SelectorPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, SelectorPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, SelectorPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, SelectorPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, SelectorPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, SelectorPlan plan, Plan parent)
	{
	}
}
