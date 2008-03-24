package org.safris.xml.generator.compiler.processor.write;

import java.io.StringWriter;
import java.util.Collection;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.processor.plan.NestablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.SimpleTypePlan;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.pipeline.PipelineDirectory;

public class WriterProcessor implements PipelineProcessor<GeneratorContext,Plan,Writer>
{
	private final Writer root = new Writer()
	{
		protected void appendDeclaration(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendGetMethod(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendSetMethod(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendMarshal(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendParse(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendCopy(StringWriter writer, Plan plan, Plan parent, String variable)
		{
		}

		protected void appendEquals(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendHashCode(StringWriter writer, Plan plan, Plan parent)
		{
		}

		protected void appendClass(StringWriter writer, Plan plan, Plan parent)
		{
		}
	};

	public Collection<Writer> process(GeneratorContext pipelineContext, Collection<Plan> documents, PipelineDirectory<GeneratorContext,Plan,Writer> directory)
	{
		Writer.directory = directory;
		tailRecurse(pipelineContext, documents, directory);
		return null;
	}

	protected final void tailRecurse(GeneratorContext pipelineContext, Collection<Plan> models, PipelineDirectory<GeneratorContext,Plan,Writer> directory)
	{
		if(models == null || models.size() == 0)
			return;

		for(Plan model : models)
		{
			if(model == null)
				continue;

			tailRecurse(pipelineContext, disclose(pipelineContext, model, directory), directory);
		}
	}

	protected Collection<Plan> disclose(GeneratorContext pipelineContext, Plan plan, PipelineDirectory<GeneratorContext,Plan,Writer> directory)
	{
		if(!(plan instanceof SimpleTypePlan) || (plan instanceof NestablePlan && ((NestablePlan)plan).isNested()))
			return null;

		if(((Nameable)plan).getName().getNamespaceURI() == null)
			throw new CompilerError("Cannot generate classes for schema with no targetNamespace.");

		root.writeFile(((Writer)directory.getEntity(plan, null)), plan, pipelineContext.getDestDir());

		return null;
	}
}
