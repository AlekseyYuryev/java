package org.safris.xml.generator.module.phase;

import java.util.Collection;
import org.safris.commons.util.logging.Logger;

public abstract class Phase<I extends ElementModule,O extends ElementModule>
{
	protected final java.util.logging.Logger logger()
	{
		return Logger.getLogger(getClass().getName()).logger();
	}

	public abstract Collection<O> manipulate(Collection<I> documents, BindingContext bindingContext, HandlerDirectory<I,O> directory);
}
