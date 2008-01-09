package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.MinInclusivePlan;

public class MinInclusiveWriter extends Writer<MinInclusivePlan>
{
	protected void appendDeclaration(StringWriter writer, MinInclusivePlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, MinInclusivePlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, MinInclusivePlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, MinInclusivePlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, MinInclusivePlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, MinInclusivePlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, MinInclusivePlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, MinInclusivePlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, MinInclusivePlan plan, Plan parent)
	{
	}
}
