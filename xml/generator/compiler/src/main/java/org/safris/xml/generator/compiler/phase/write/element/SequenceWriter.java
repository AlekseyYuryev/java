package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.phase.write.Writer;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.SequencePlan;

public class SequenceWriter extends Writer<SequencePlan>
{
	protected void appendDeclaration(StringWriter writer, SequencePlan plan, Plan parent)
	{
	}

	protected void appendGetMethod(StringWriter writer, SequencePlan plan, Plan parent)
	{
	}

	protected void appendSetMethod(StringWriter writer, SequencePlan plan, Plan parent)
	{
	}

	protected void appendMarshal(StringWriter writer, SequencePlan plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, SequencePlan plan, Plan parent)
	{
	}

	public void appendCopy(StringWriter writer, SequencePlan plan, Plan parent, String variable)
	{
	}

	protected void appendEquals(StringWriter writer, SequencePlan plan, Plan parent)
	{
	}

	protected void appendHashCode(StringWriter writer, SequencePlan plan, Plan parent)
	{
	}

	protected void appendClass(StringWriter writer, SequencePlan plan, Plan parent)
	{
	}
}
