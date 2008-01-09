package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.DocumentationPlan;
import org.safris.xml.generator.compiler.phase.write.Writer;

public class DocumentationWriter extends Writer<DocumentationPlan>
{
	protected void appendDeclaration(StringWriter writer, DocumentationPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, DocumentationPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, DocumentationPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, DocumentationPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, DocumentationPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, DocumentationPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, DocumentationPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, DocumentationPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, DocumentationPlan plan, Plan parent)
	{
	}
}
