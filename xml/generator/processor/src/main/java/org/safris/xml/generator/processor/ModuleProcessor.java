package org.safris.xml.generator.processor;

import java.util.Collection;

public interface ModuleProcessor<T extends ProcessContext,I extends ElementModule,O extends ElementModule>
{
	public Collection<O> process(Collection<I> documents, T processContext, ProcessorDirectory<T,I,O> directory);
}
