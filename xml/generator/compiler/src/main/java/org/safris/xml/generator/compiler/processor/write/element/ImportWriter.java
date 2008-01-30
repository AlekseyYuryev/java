package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.ImportPlan;

public class ImportWriter extends Writer<ImportPlan>
{
	protected void appendDeclaration(StringWriter writer, ImportPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, ImportPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, ImportPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, ImportPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, ImportPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, ImportPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, ImportPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, ImportPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, ImportPlan plan, Plan parent)
	{
	}
}
