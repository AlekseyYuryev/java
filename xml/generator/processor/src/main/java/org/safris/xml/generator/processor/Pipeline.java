package org.safris.xml.generator.processor;

import java.util.ArrayList;
import java.util.Collection;

public class Pipeline
{
	private static class Entry<I extends ElementModule,O extends ElementModule>
	{
		private final Collection<I> input;
		private final Collection<O> output;
		private final ProcessorDirectory<I,O> directory;

		public Entry(Collection<I> input, Collection<O> output, ProcessorDirectory<I,O> directory)
		{
			this.input = input;
			this.output = output;
			this.directory = directory;
		}

		public ModuleProcessor<I,O> getProcessor()
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

	public <I extends ElementModule,O extends ElementModule>void addProcessor(Collection<I> input, Collection<O> output, ProcessorDirectory<I,O> handlerDirectory)
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
			final Collection instanceHandles = modulePair.getProcessor().process(modulePair.getInput(), generatorContext, modulePair.getDirectory());
			if(instanceHandles != null && modulePair.getOutput() != null)
				modulePair.getOutput().addAll(instanceHandles);
		}

		for(ProcessorDirectory directory : directories)
			directory.clear();
	}
}
