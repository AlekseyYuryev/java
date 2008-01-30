package org.safris.xml.generator.module.phase;

public interface HandlerDirectory<K extends ElementModule,V extends ElementModule>
{
	public ElementModule<V> lookup(K key, V parent);
	public Phase<K,V> getPhase();
	public void clear();
}
