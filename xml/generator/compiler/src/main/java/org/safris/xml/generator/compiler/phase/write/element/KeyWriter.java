package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.KeyPlan;

public class KeyWriter extends Writer<KeyPlan>
{
	protected void appendDeclaration(StringWriter writer, KeyPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, KeyPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, KeyPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, KeyPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, KeyPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, KeyPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, KeyPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, KeyPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, KeyPlan plan, Plan parent)
	{
	}
}
