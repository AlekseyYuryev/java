package org.safris.xml.generator.processor;

import java.util.Collection;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ProcessorDirectory;

public interface ModuleProcessor<I extends ElementModule,O extends ElementModule>
{
	public Collection<O> process(Collection<I> documents, GeneratorContext generatorContext, ProcessorDirectory<I,O> directory);
}
