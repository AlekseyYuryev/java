package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.EnumerationPlan;
import org.safris.xml.generator.compiler.phase.write.Writer;

public class EnumerationWriter extends Writer<EnumerationPlan>
{
	protected void appendDeclaration(StringWriter writer, EnumerationPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, EnumerationPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, EnumerationPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, EnumerationPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, EnumerationPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, EnumerationPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, EnumerationPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, EnumerationPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, EnumerationPlan plan, Plan parent)
	{
	}
}
