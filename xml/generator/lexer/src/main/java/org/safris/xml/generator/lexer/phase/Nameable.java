package org.safris.xml.generator.lexer.phase;

import org.safris.xml.generator.processor.BindingQName;
import org.safris.xml.generator.processor.ModuleProcessor;

public interface Nameable<T extends ModuleProcessor>
{
	public BindingQName getName();
}
