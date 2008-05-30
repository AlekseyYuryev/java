/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.lang;

public class PackageNotFoundException extends Exception
{
	/**
	 * use serialVersionUID from JDK 1.1.X for interoperability
	 */
	private static final long serialVersionUID = 4963238462943629433L;

	/**
	 * This field holds the exception ex if the
	 * PackageNotFoundException(String s, Throwable ex) constructor was
	 * used to instantiate the object
	 * @serial
	 */
	private Throwable ex;

	/**
	 * Constructs a <code>PackageNotFoundException</code> with no detail message.
	 */
	public PackageNotFoundException()
	{
		super((Throwable)null);  // Disallow initCause
	}

	/**
	 * Constructs a <code>PackageNotFoundException</code> with the
	 * specified detail message.
	 *
	 * @param   s   the detail message.
	 */
	public PackageNotFoundException(String s)
	{
		super(s, null);  //  Disallow initCause
	}

	/**
	 * Constructs a <code>PackageNotFoundException</code> with the
	 * specified detail message and optional exception that was
	 * raised while loading the class.
	 *
	 * @param s the detail message
	 * @param ex the exception that was raised while loading the class
	 */
	public PackageNotFoundException(String s, Throwable ex)
	{
		super(s, null);  //  Disallow initCause
		this.ex = ex;
	}

	/**
	 * Returns the exception that was raised if an error occurred while
	 * attempting to load the class. Otherwise, returns <tt>null</tt>.
	 *
	 * <p>This method predates the general-purpose exception chaining facility.
	 * The {@link Throwable#getCause()} method is now the preferred means of
	 * obtaining this information.
	 *
	 * @return the <code>Exception</code> that was raised while loading a class
	 */
	public Throwable getException()
	{
		return ex;
	}

	/**
	 * Returns the cause of this exception (the exception that was raised
	 * if an error occurred while attempting to load the class; otherwise
	 * <tt>null</tt>).
	 *
	 * @return  the cause of this exception.
	 */
	public Throwable getCause()
	{
		return ex;
	}
}
