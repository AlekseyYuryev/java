package org.safris.xml.generator.module.phase;

public abstract class HandlerDirectory<K,V>
{
	protected abstract V lookup(K key);
}
