package org.safris.commons.xml.binding;

public class Decimal extends Number
{
	public static Decimal parseDecimal(String string)
	{
		return new Decimal(Double.parseDouble(string));
	}

	private final Number value;

	public Decimal(Number value)
	{
		this.value = value;
	}

	/**
	 * Returns the value of the specified number as an <code>int</code>.
	 * This may involve rounding or truncation.
	 * @return  the numeric value represented by this object after conversion
	 *          to type <code>int</code>.
	 */
	public int intValue()
	{
		return value.intValue();
	}

	/**
	 * Returns the value of the specified number as a <code>long</code>.
	 * This may involve rounding or truncation.
	 * @return  the numeric value represented by this object after conversion
	 *          to type <code>long</code>.
	 */
	public long longValue()
	{
		return value.longValue();
	}

	/**
	 * Returns the value of the specified number as a <code>float</code>.
	 * This may involve rounding.
	 * @return  the numeric value represented by this object after conversion
	 *          to type <code>float</code>.
	 */
	public float floatValue()
	{
		return value.floatValue();
	}

	/**
	 * Returns the value of the specified number as a <code>double</code>.
	 * This may involve rounding.
	 * @return  the numeric value represented by this object after conversion
	 *          to type <code>double</code>.
	 */
	public double doubleValue()
	{
		return value.doubleValue();
	}

	public String toString()
	{
		if(longValue() == doubleValue())
			return String.valueOf(longValue());

		return String.valueOf(doubleValue());
	}
}
