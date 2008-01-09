package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.AttributeGroupPlan;
import org.safris.xml.generator.compiler.phase.write.Writer;

public class AttributeGroupWriter extends Writer<AttributeGroupPlan>
{
	protected void appendDeclaration(StringWriter writer, AttributeGroupPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, AttributeGroupPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, AttributeGroupPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, AttributeGroupPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, AttributeGroupPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, AttributeGroupPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, AttributeGroupPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, AttributeGroupPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, AttributeGroupPlan plan, Plan parent)
	{
	}
}
