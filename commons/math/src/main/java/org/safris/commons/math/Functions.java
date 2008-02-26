package org.safris.commons.math;

public final class Functions
{
	/**
	 * Calculate the log given both the base and value.
	 *
	 * @param base The base.
	 * @param value The value.
	 *
	 * @return The log.
	 */
	public static double log(int base, int value)
	{
		return Math.log(value) / Math.log(base);
	}

	private Functions()
	{
	}
}
