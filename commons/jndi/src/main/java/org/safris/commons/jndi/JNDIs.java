/* Copyright (c) 2008 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.commons.jndi;

import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;

import tyrex.tm.RuntimeContext;

public final class JNDIs {
  /**
   * Binds a name to an object.
   * All intermediate contexts and the target context (that named by all
   * but terminal atomic component of the name) must already exist.
   *
   * @param name
   *      the name to bind; may not be empty
   * @param obj
   *      the object to bind; possibly null
   * @throws  NameAlreadyBoundException if name is already bound
   * @throws  javax.naming.directory.InvalidAttributesException
   *      if object did not supply all mandatory attributes
   * @throws  NamingException if a naming exception is encountered
   *
   * @see #bind(String, Object)
   * @see #rebind(Name, Object)
   * @see javax.naming.directory.DirContext#bind(Name, Object,
   *      javax.naming.directory.Attributes)
   */
  public static void bind(final String name, final Object obj) throws NamingException {
    if (name == null)
      throw new NullPointerException("name == null");

    if (!name.startsWith("java:"))
      throw new NamingException("!name.startsWith(\"java:\")");

    final RuntimeContext runtimeContext = RuntimeContext.newRuntimeContext();
    Context context = runtimeContext.getEnvContext();

    final StringTokenizer tokenizer = new StringTokenizer(name.substring(5), "/");
    while (tokenizer.hasMoreTokens()) {
      final String token = tokenizer.nextToken();
      if (tokenizer.hasMoreTokens())
        context = context.createSubcontext(token);
      else
        context.bind(token, obj);
    }

    RuntimeContext.setRuntimeContext(runtimeContext);
  }

  private JNDIs() {
  }
}