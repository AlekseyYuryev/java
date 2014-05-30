package org.safris.xml.generator.compiler.runtime;

import java.io.Serializable;
import java.util.List;
import java.util.RandomAccess;

public interface BindingList<E> extends List<E>, RandomAccess, Cloneable, Serializable {
  public Binding getParent();
}
