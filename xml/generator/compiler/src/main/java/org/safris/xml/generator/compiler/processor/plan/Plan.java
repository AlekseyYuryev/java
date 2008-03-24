package org.safris.xml.generator.compiler.processor.plan;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.ElementWrapper;
import org.safris.xml.generator.compiler.processor.plan.element.ElementPlan;
import org.safris.xml.generator.lexer.processor.model.EnumerableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.commons.pipeline.PipelineEntity;

public abstract class Plan<T extends Model> implements PipelineEntity<Plan>
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
		final Collection<EnumerationModel> enumerations = model.getEnumerations();
		final boolean ret = enumerations != null && enumerations.size() != 0;
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

	public T getModel()
	{
		return model;
	}

	public final Plan getParent()
	{
		return parent;
	}
}
