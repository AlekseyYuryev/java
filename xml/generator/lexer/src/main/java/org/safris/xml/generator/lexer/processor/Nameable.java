package org.safris.xml.generator.lexer.processor;

import org.safris.xml.generator.processor.BindingQName;
import org.safris.xml.generator.processor.ElementModule;

public interface Nameable<T extends ElementModule>
{
	public BindingQName getName();
}
