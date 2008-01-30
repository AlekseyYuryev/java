package org.safris.xml.generator.processor.phase;

import java.util.Collection;
import org.safris.commons.util.logging.Logger;

public abstract class ModuleProcessor<I extends ElementModule,O extends ElementModule>
{
	protected final java.util.logging.Logger logger()
	{
		return Logger.getLogger(getClass().getName()).logger();
	}

	public abstract Collection<O> process(Collection<I> documents, GeneratorContext generatorContext, ProcessorDirectory<I,O> directory);
}
