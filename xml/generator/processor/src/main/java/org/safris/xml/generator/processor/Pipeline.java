package org.safris.xml.generator.processor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import org.safris.commons.util.logging.ExitSevereError;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

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
		private final ProcessorDirectory<I,O> directory;
		
			public Entry(Collection<I> input, Collection<O> output, ProcessorDirectory<I,O> directory)
			{
			this.input = input;
		this.output = output;
		this.directory = directory;
		}
		
			public ModuleProcessor<I,O> getPhase()
		{
		return directory.getProcessor();
		}
		
			public Collection<I> getInput()
		{
		return input;
		}
		
			public Collection<O> getOutput()
		{
		return output;
		}
		
			public ProcessorDirectory<I,O> getDirectory()
		{
	return directory;
	}
	}
	
	private final Collection<Entry> modulePairs = new ArrayList<Entry>();
	private final GeneratorContext generatorContext;
	
		public Pipeline(GeneratorContext generatorContext)
	{
	this.generatorContext = generatorContext;
	}
	
		public <I extends ElementModule,O extends ElementModule>void addPhase(Collection<I> input, Collection<O> output, ProcessorDirectory<I,O> handlerDirectory)
		{
			synchronized(modulePairs)
			{
		final Entry<I,O> modulePair = new Entry<I,O>(input, output, handlerDirectory);
	modulePairs.add(modulePair);
	}
	}
	
		public void begin()
		{
		final Collection<ProcessorDirectory> directories = new ArrayList<ProcessorDirectory>();
			for(Entry modulePair : modulePairs)
			{
			directories.add(modulePair.getDirectory());
				final Collection instanceHandles = modulePair.getPhase().process(modulePair.getInput(), generatorContext, modulePair.getDirectory());
		if(modulePair.getOutput() != null)
		modulePair.getOutput().addAll(instanceHandles);
		}
			
	for(ProcessorDirectory directory : directories)
directory.clear();
	}
}
