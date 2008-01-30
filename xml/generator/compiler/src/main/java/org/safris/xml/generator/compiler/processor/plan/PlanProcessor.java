package org.safris.xml.generator.compiler.processor.plan;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.safris.commons.util.Files;
import org.safris.commons.util.logging.Logger;
import org.safris.xml.generator.compiler.lang.CompilerLoggerName;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public final class PlanProcessor implements ModuleProcessor<Model,Plan>
{
	private static final Logger logger = Logger.getLogger(CompilerLoggerName.PLAN);
	private Plan root;

	public final Collection<Plan> process(Collection<Model> documents, GeneratorContext generatorContext, ProcessorDirectory<Model,Plan> directory)
	{
		root = new Plan(null, null){};
		final Collection<Plan> plans = new ArrayList<Plan>();
		for(Model model : documents)
		{
			if(model.getChildren() == null || model.getChildren().size() == 0)
				continue;

			final String display = Files.relativePath(Files.getCwd(), new File(model.getSchema().getURL().getFile()).getAbsoluteFile());
			logger.info("Parsing {" + model.getTargetNamespace() + "} from " + display);

			for(Model outerModels : model.getChildren())
				disclose(outerModels, root, plans, generatorContext, directory);
		}

		return plans;
	}

	protected final Collection<Model> disclose(Model model, Plan parent, Collection<Plan> plans, GeneratorContext generatorContext, ProcessorDirectory<Model, Plan> directory)
	{
		final Plan planInstance = (Plan)directory.getModule(model, parent);
		plans.add(planInstance);
		return model.getChildren();
	}
}
