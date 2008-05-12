package org.safris.xml.generator.compiler.processor.plan;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.safris.commons.io.Files;
import org.safris.commons.logging.Logger;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.compiler.lang.CompilerLoggerName;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.model.Model;

public final class PlanProcessor implements PipelineProcessor<GeneratorContext,Model,Plan>
{
	private static final Logger logger = Logger.getLogger(CompilerLoggerName.PLAN);
	private Plan root;

	public final Collection<Plan> process(GeneratorContext pipelineContext, Collection<Model> documents, PipelineDirectory<GeneratorContext,Model,Plan> directory)
	{
		root = new Plan(null, null){};
		final Collection<Plan> plans = new ArrayList<Plan>();
		for(Model model : documents)
		{
			if(model.getChildren() == null || model.getChildren().size() == 0)
				continue;

			final String display = Files.relativePath(Files.getCwd(), new File(model.getSchema().getURL().getFile()).getAbsoluteFile());
			logger.info("Parsing {" + model.getTargetNamespace() + "} from " + display);

			for(Model child : model.getChildren())
				disclose(child, root, plans, pipelineContext, directory);
		}

		return plans;
	}

	protected final void disclose(Model model, Plan parent, Collection<Plan> plans, GeneratorContext pipelineContext, PipelineDirectory<GeneratorContext,Model,Plan> directory)
	{
		final Plan plan = (Plan)directory.getEntity(model, parent);
		plans.add(plan);
	}
}
