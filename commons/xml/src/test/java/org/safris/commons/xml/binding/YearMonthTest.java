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

import org.junit.Test;

import static org.junit.Assert.*;

public class YearMonthTest
{
	public static void main(String[] args)
	{
		new YearMonthTest().testYearMonth();
	}

	@Test
	public void testYearMonth()
	{
		try
		{
			YearMonth.parseYearMonth(null);
			fail("Expected a NullPointerException");
		}
		catch(NullPointerException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("010");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("10");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("100");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("AAA");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("2227-1");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("2227-1Z");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("2227-13-11:00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("2227-12-15:00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("2227-01+14:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("2227-00+10:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			YearMonth.parseYearMonth("2227-02+14:60.9");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		final String[] yearMonths = new String[]
		{
			"2500-01",
			"1400-02",
			"0003-03",
			"0020-04",
			"0310-05",
			"1001-06Z",
			"2007-07+01:00",
			"3017-08-01:00",
			"4027-09Z",
			"1302-10+12:00",
			"1112-11-12:30"
		};

		for(String yearMonth : yearMonths)
			assertEquals(yearMonth, YearMonth.parseYearMonth(yearMonth).toString());
	}
}
