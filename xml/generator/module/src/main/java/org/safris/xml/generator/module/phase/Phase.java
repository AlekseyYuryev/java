package org.safris.xml.generator.module.phase;

import java.util.Collection;
import org.safris.commons.util.logging.Logger;

public abstract class Phase<T extends Phase>
{
	protected final java.util.logging.Logger logger()
	{
		return Logger.getLogger(getClass().getName()).logger();
	}

	public abstract Collection<? extends Phase> manipulate(Collection<T> documents, BindingContext share);
}
