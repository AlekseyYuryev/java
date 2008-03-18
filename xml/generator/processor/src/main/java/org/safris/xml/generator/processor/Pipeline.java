package org.safris.xml.generator.processor;

import java.util.ArrayList;
import java.util.Collection;

public class Pipeline<T extends ProcessContext>
{
	private class Entry<I extends ElementModule,O extends ElementModule>
	{
		private final Collection<I> input;
		private final Collection<O> output;
		private final ProcessorDirectory<T,I,O> directory;

		public Entry(Collection<I> input, Collection<O> output, ProcessorDirectory<T,I,O> directory)
		{
			this.input = input;
			this.output = output;
			this.directory = directory;
		}

		public ModuleProcessor<T,I,O> getProcessor()
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

		public ProcessorDirectory<T,I,O> getDirectory()
		{
			return directory;
		}
	}

	private final Collection<Entry> entries = new ArrayList<Entry>();
	private final T processContext;

	public Pipeline(T processContext)
	{
		this.processContext = processContext;
	}

	public <I extends ElementModule,O extends ElementModule>void addProcessor(Collection<I> input, Collection<O> output, ProcessorDirectory<T,I,O> handlerDirectory)
	{
		synchronized(entries)
		{
			final Entry<I,O> modulePair = new Entry<I,O>(input, output, handlerDirectory);
			entries.add(modulePair);
		}
	}

	public void begin()
	{
		final Collection<ProcessorDirectory> directories = new ArrayList<ProcessorDirectory>();
		for(Entry modulePair : entries)
		{
			directories.add(modulePair.getDirectory());
			final Collection instanceHandles = modulePair.getProcessor().process(modulePair.getInput(), processContext, modulePair.getDirectory());
			if(instanceHandles != null && modulePair.getOutput() != null)
				modulePair.getOutput().addAll(instanceHandles);
		}

		for(ProcessorDirectory directory : directories)
			directory.clear();
	}
}
