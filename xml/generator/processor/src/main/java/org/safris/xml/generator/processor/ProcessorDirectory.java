package org.safris.xml.generator.processor;

import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.ModuleProcessor;

public interface ProcessorDirectory<K extends ElementModule,V extends ElementModule>
{
	public ElementModule<V> getModule(K module, V parent);
	public ModuleProcessor<K,V> getProcessor();
	public void clear();
}