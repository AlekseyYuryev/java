package org.safris.xml.generator.module.phase;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import org.safris.commons.util.logging.ExitSevereError;

public class Pipeline
{
	private static class Entry<T extends Phase>
	{
		private static Method getInstanceMethod(Class<? extends Phase> moduleClass)
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

		private final Class<T> moduleClass;
		private final Collection input;
		private final Collection<T> output;

		public Entry(Class<T> moduleClass, Collection input, Collection<T> output)
		{
			this.moduleClass = moduleClass;
			this.input = input;
			this.output = output;
		}

		public Phase<T> getModule()
		{
			final Method instanceMethod = Entry.getInstanceMethod(moduleClass);
			try
			{
				return (Phase)instanceMethod.invoke(null);
			}
			catch(Exception e)
			{
				throw new ExitSevereError(e);
			}
		}

		public Collection getInput()
		{
			return input;
		}

		public Collection<T> getOutput()
		{
			return output;
		}
	}

	private final Collection<Entry> modulePairs = new ArrayList<Entry>();
	private final BindingContext share;

	public Pipeline(BindingContext share)
	{
		this.share = share;
	}

	public void addPhase(Collection input, Collection output, Class moduleClass)
	{
		synchronized(modulePairs)
		{
			Entry modulePair = new Entry(moduleClass, input, output);
			modulePairs.add(modulePair);
		}
	}

	public void begin()
	{
		for(Entry<? extends Phase> modulePair : modulePairs)
		{
			Collection instanceHandles = modulePair.getModule().manipulate(modulePair.getInput(), share);
			if(modulePair.getOutput() != null)
				modulePair.getOutput().addAll(instanceHandles);
		}
	}
}
