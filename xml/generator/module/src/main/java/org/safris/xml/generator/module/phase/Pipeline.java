package org.safris.xml.generator.module.phase;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import org.safris.commons.util.logging.ExitSevereError;

public class Pipeline
{
	private static class Entry<I extends ElementModule,O extends ElementModule>
	{
		private static Method getInstanceMethod(Class<? extends ElementModule> moduleClass)
		{
			final Method[] methods = moduleClass.getDeclaredMethods();
			Method instanceMethod = null;
			for(Method method : methods)
			{
				if(moduleClass.equals(method.getReturnType()) && (method.getModifiers() & Modifier.STATIC) == Modifier.STATIC && method.getParameterTypes().length == 0)
				{
					instanceMethod = method;
					break;
				}
			}

			if(instanceMethod == null)
				throw new ExitSevereError("The " + moduleClass.getSimpleName() + " module must have a static instance() method!");

			return instanceMethod;
		}

		private final Collection<I> input;
		private final Collection<O> output;
		private final HandlerDirectory<I,O> directory;

		public Entry(Collection<I> input, Collection<O> output, HandlerDirectory<I,O> directory)
		{
			this.input = input;
			this.output = output;
			this.directory = directory;
		}

		public Phase<I,O> getPhase()
		{
			return directory.getPhase();
		}

		public Collection<I> getInput()
		{
			return input;
		}

		public Collection<O> getOutput()
		{
			return output;
		}

		public HandlerDirectory<I,O> getDirectory()
		{
			return directory;
		}
	}

	private final Collection<Entry> modulePairs = new ArrayList<Entry>();
	private final BindingContext bindingContext;

	public Pipeline(BindingContext bindingContext)
	{
		this.bindingContext = bindingContext;
	}

	public <I extends ElementModule,O extends ElementModule>void addPhase(Collection<I> input, Collection<O> output, HandlerDirectory<I,O> handlerDirectory)
	{
		synchronized(modulePairs)
		{
			final Entry<I,O> modulePair = new Entry<I,O>(input, output, handlerDirectory);
			modulePairs.add(modulePair);
		}
	}

	public void begin()
	{
		final Collection<HandlerDirectory> directories = new ArrayList<HandlerDirectory>();
		for(Entry modulePair : modulePairs)
		{
			directories.add(modulePair.getDirectory());
			final Collection instanceHandles = modulePair.getPhase().manipulate(modulePair.getInput(), bindingContext, modulePair.getDirectory());
			if(modulePair.getOutput() != null)
				modulePair.getOutput().addAll(instanceHandles);
		}

		for(HandlerDirectory directory : directories)
			directory.clear();
	}
}
