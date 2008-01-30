package org.safris.xml.generator.processor;

import java.util.Collection;
import org.safris.commons.util.logging.Logger;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ProcessorDirectory;

public abstract class ModuleProcessor<I extends ElementModule,O extends ElementModule>
{
	protected final java.util.logging.Logger logger()
	{
		return Logger.getLogger(getClass().getName()).logger();
	}
	
	public abstract Collection<O> process(Collection<I> documents, GeneratorContext generatorContext, ProcessorDirectory<I,O> directory);
}
