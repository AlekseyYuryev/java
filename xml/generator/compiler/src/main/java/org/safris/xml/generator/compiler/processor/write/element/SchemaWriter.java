package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.SchemaPlan;

public class SchemaWriter extends Writer<SchemaPlan>
{
	protected void appendDeclaration(StringWriter writer, SchemaPlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, SchemaPlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, SchemaPlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, SchemaPlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, SchemaPlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, SchemaPlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, SchemaPlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, SchemaPlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, SchemaPlan plan, Plan parent)
	{
	}
}
