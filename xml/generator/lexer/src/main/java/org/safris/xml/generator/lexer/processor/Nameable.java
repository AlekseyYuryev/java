package org.safris.xml.generator.lexer.processor;

import org.safris.xml.generator.processor.BindingQName;
import org.safris.xml.generator.processor.ModuleProcessor;

public interface Nameable<T extends ModuleProcessor>
{
	public BindingQName getName();
}
