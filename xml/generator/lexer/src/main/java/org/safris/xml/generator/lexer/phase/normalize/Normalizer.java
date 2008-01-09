package org.safris.xml.generator.lexer.phase.normalize;

import java.lang.reflect.Method;
import java.util.Collection;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.module.phase.BindingParameters;
import org.safris.xml.generator.module.phase.Phase;

public abstract class Normalizer<T extends Model> extends Phase<Model>
{
	private static final Normalizer instance = new Normalizer()
	{
		protected void stage1(Model handler)
		{
		}

		protected void stage2(Model handler)
		{
		}

		protected void stage3(Model handler)
		{
		}

		protected void stage4(Model handler)
		{
		}

		protected void stage5(Model handler)
		{
		}

		protected void stage6(Model handler)
		{
		}
	};

	public static Normalizer instance()
	{
		return instance;
	}

	private int stage = 0;

	protected final void tailRecurse(Collection<Model> models, BindingParameters share)
	{
		if(models == null || models.size() == 0)
			return;

		for(Model model : models)
		{
			if(model == null)
				continue;

			tailRecurse(disclose(model, share), share);
		}
	}

	public Collection<Normalizer> manipulate(Collection<Model> models, BindingParameters share)
	{
		synchronized(instance)
		{
			int stages = 0;
			Method[] methods = Normalizer.class.getDeclaredMethods();
			for(Method method : methods)
				if(method.getName().startsWith("stage"))
					stages++;

			for(int stage = 0; stage < stages; stage++)
			{
				this.stage = stage;
				tailRecurse(models, share);
			}

			return null;
		}
	}

	protected Collection<Model> disclose(Model model, BindingParameters share)
	{
		final Normalizer normalizer = NormalizerDirectory.instance().lookup(model);
		try
		{
			final Method method = normalizer.getClass().getDeclaredMethod("stage" + (stage + 1), Model.class);
			method.setAccessible(true);
			method.invoke(normalizer, model);
		}
		catch(Exception e)
		{
			throw new LexerError(e);
		}

		return model.getChildren();
	}

	// NOTE: This stage used for fixing globally accessible types
	protected abstract void stage1(T handler);

	// NOTE: This stage used for de-referencing qName references to correct types
	protected abstract void stage2(T handler);

	// NOTE: This stage used for de-referencing <redefine/> rules
	protected abstract void stage3(T handler);

	// NOTE: This stage used for injection of information into parent elements as per the physical schema structure
	protected abstract void stage4(T handler);

	// NOTE: This stage used for injection of information into parent elements as per the logical schema structure
	protected abstract void stage5(T handler);

	// NOTE: This stage used to ammend information in certain edge-case situations.
	protected abstract void stage6(T handler);
}
