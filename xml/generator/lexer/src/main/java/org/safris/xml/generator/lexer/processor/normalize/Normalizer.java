package org.safris.xml.generator.lexer.processor.normalize;

import java.lang.reflect.Method;
import java.util.Collection;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public abstract class Normalizer<T extends Model> extends ModuleProcessor<Model,Normalizer> implements ElementModule<Normalizer>
{
	private final NormalizerDirectory directory;
	private int stage = 0;

	public Normalizer(NormalizerDirectory directory)
	{
		this.directory = directory;
	}

	public NormalizerDirectory getDirectory()
	{
		return directory;
	}

	protected final void tailRecurse(Collection<Model> models, GeneratorContext generatorContext, ProcessorDirectory<Model,Normalizer> directory)
	{
		if(models == null || models.size() == 0)
			return;

		for(Model model : models)
		{
			if(model == null)
				continue;

			tailRecurse(disclose(model, generatorContext, directory), generatorContext, directory);
		}
	}

	public Collection<Normalizer> process(Collection<Model> models, GeneratorContext generatorContext, ProcessorDirectory<Model,Normalizer> directory)
	{
		int stages = 0;
		Method[] methods = Normalizer.class.getDeclaredMethods();
		for(Method method : methods)
			if(method.getName().startsWith("stage"))
				stages++;

		for(int stage = 0; stage < stages; stage++)
		{
			this.stage = stage;
			tailRecurse(models, generatorContext, directory);
		}

		return null;
	}

	protected Collection<Model> disclose(Model model, GeneratorContext generatorContext, ProcessorDirectory<Model,Normalizer> directory)
	{
		final Normalizer normalizer = (Normalizer)directory.lookup(model, this);
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
