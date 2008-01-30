package org.safris.xml.generator.module.phase;

public interface ProcessorDirectory<K extends ElementModule,V extends ElementModule>
{
	public ElementModule<V> lookup(K key, V parent);
	public ModuleProcessor<K,V> getProcessor();
	public void clear();
}
