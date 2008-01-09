package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.KeyrefPlan;

public class KeyrefWriter extends Writer<KeyrefPlan>
{
	protected void appendDeclaration(StringWriter writer, KeyrefPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, KeyrefPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, KeyrefPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, KeyrefPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, KeyrefPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, KeyrefPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, KeyrefPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, KeyrefPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, KeyrefPlan plan, Plan parent)
	{
	}
}
