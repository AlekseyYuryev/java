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
