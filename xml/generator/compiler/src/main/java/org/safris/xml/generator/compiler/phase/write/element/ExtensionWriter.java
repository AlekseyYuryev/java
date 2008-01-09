package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.ExtensionPlan;
import org.safris.xml.generator.compiler.phase.write.Writer;

public class ExtensionWriter extends Writer<ExtensionPlan>
{
	protected void appendDeclaration(StringWriter writer, ExtensionPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, ExtensionPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, ExtensionPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, ExtensionPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, ExtensionPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, ExtensionPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, ExtensionPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, ExtensionPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, ExtensionPlan plan, Plan parent)
	{
	}
}
