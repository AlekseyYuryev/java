package org.safris.commons.util;

import java.util.Iterator;

public interface TreeIterator<E> extends Iterator<E>
{
	public int level();
}
