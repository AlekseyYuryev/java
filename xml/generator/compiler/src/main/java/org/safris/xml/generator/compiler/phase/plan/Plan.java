package org.safris.xml.generator.compiler.phase.plan;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.safris.commons.util.Files;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.ElementWrapper;
import org.safris.xml.generator.compiler.phase.plan.element.ElementPlan;
import org.safris.xml.generator.lexer.phase.model.EnumerableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.module.phase.GeneratorContext;
import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.ProcessorDirectory;
import org.safris.xml.generator.module.phase.ModuleProcessor;

public abstract class Plan<T extends Model> extends ModuleProcessor<Model,Plan> implements ElementModule<Plan>
{
	public static <A extends Plan>LinkedHashSet analyze(Collection<? extends Model> models, Plan parent)
	{
		LinkedHashSet<A> plans = null;
		if(models != null && models.size() != 0)
		{
			plans = new LinkedHashSet<A>(models.size());
			for(Object object : models)
			{
				Model model = (Model)object;
				if(model instanceof ElementWrapper)
				{
					ElementWrapper element = (ElementWrapper)model;
					A plan = Plan.<A>analyze(element.getElementModel(), parent);
					((ElementPlan)plan).setMinOccurs(element.getMinOccurs());
					((ElementPlan)plan).setMaxOccurs(element.getMaxOccurs());
					plans.add(plan);
				}
				else if(model instanceof Model)
					plans.add(Plan.<A>analyze(model, parent));
			}
		}
		else
			plans = new LinkedHashSet<A>(1);

		return plans;
	}

	public static <A extends Plan>A analyze(Model model, Plan parent)
	{
		if(model == null)
			return null;

		String planName = Plan.class.getPackage().getName() + ".element." + model.getClass().getSimpleName().substring(0, model.getClass().getSimpleName().indexOf("Model")) + "Plan";
		A plan = null;
		try
		{
			Constructor constructor = Class.forName(planName).getConstructor(model.getClass(), Plan.class);
			plan = (A)constructor.newInstance(model, parent);
		}
		catch(ClassNotFoundException e)
		{
			throw new CompilerError("Class not found for element [" + model.getClass().getSimpleName() + "]");
		}
		catch(Exception e)
		{
			throw new CompilerError(e);
		}

		return plan;
	}

	protected static boolean hasEnumerations(EnumerableModel model)
	{
//		if(model instanceof ListModel || model instanceof UnionModel)
//			return false;

		Collection<EnumerationModel> enumerations = model.getEnumerations();
		boolean ret = enumerations != null && enumerations.size() != 0;
		if(ret)
			return ret;

		if(!(model instanceof SimpleTypeModel))
			return false;

		SimpleTypeModel restrictionType = (SimpleTypeModel)model;
		if(restrictionType == null)
			return ret;

		while((restrictionType = restrictionType.getSuperType()) != null)
			if(restrictionType instanceof EnumerableModel)
				return hasEnumerations((EnumerableModel)restrictionType);

		return ret;
	}

	private final T model;
	private final Plan parent;

	public Plan(T model, Plan parent)
	{
		this.model = model;
		this.parent = parent;
	}

	public final Collection<Plan> process(Collection<Model> documents, GeneratorContext generatorContext, ProcessorDirectory<Model,Plan> directory)
	{
		final Collection<Plan> plans = new ArrayList<Plan>();
		for(Model model : documents)
		{
			if(model.getChildren() == null || model.getChildren().size() == 0)
				continue;

			final String display = Files.relativePath(Files.getCwd(), new File(model.getSchema().getURL().getFile()).getAbsoluteFile());
			logger().info("Parsing {" + model.getTargetNamespace() + "} from " + display);

			for(Model outerModels : model.getChildren())
				disclose(outerModels, generatorContext, plans, directory);
		}

		return plans;
	}

	protected final Collection<Model> disclose(Model model, GeneratorContext generatorContext, Collection<Plan> plans, ProcessorDirectory<Model, Plan> directory)
	{
		final Plan planInstance = (Plan)directory.lookup(model, this);
		plans.add(planInstance);
		return model.getChildren();
	}

	public T getModel()
	{
		return model;
	}

	public final Plan getParent()
	{
		return parent;
	}
}
