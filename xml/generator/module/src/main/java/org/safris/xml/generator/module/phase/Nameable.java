package org.safris.xml.generator.module.phase;

import org.safris.commons.util.xml.BindingQName;

public interface Nameable<T extends Phase>
{
	public BindingQName getName();
}
