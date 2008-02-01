package org.safris.commons.util;

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
