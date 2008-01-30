package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.HasPropertyPlan;

public class HasPropertyWriter extends Writer<HasPropertyPlan>
{
	protected void appendDeclaration(StringWriter writer, HasPropertyPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, HasPropertyPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, HasPropertyPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, HasPropertyPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, HasPropertyPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, HasPropertyPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, HasPropertyPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, HasPropertyPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, HasPropertyPlan plan, Plan parent)
	{
	}
}
