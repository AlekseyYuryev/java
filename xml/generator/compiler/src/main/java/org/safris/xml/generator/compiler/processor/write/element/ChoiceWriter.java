package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.ChoicePlan;
import org.safris.xml.generator.compiler.processor.write.Writer;

public class ChoiceWriter extends Writer<ChoicePlan>
{
	protected void appendDeclaration(StringWriter writer, ChoicePlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, ChoicePlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, ChoicePlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, ChoicePlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, ChoicePlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, ChoicePlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, ChoicePlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, ChoicePlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, ChoicePlan plan, Plan parent)
	{
	}
}
