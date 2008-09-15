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

public class MonthDayTest
{
	public static void main(String[] args)
	{
		new MonthDayTest().testMonthDay();
	}

	@Test
	public void testMonthDay()
	{
		try
		{
			MonthDay.parseMonthDay(null);
			fail("Expected a NullPointerException");
		}
		catch(NullPointerException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("---5");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("-5-30");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--A");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--13");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--4");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--04-32");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--04-00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--04-7");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--02-30");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--04-31");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--06-31");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--09-31");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--11Z-");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--12-30-15:00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--07-12+14:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			MonthDay.parseMonthDay("--02-01+14:60.9");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		final String[] monthDays = new String[]
		{
			"--12-31",
			"--04-30",
			"--03-31",
			"--02-29",
			"--01-31",
			"--01-12Z",
			"--07-02+01:00",
			"--09-12-01:00",
			"--10-11Z",
			"--11-15+12:00",
			"--12-17-12:30"
		};

		for(String monthDay : monthDays)
			assertEquals(monthDay, MonthDay.parseMonthDay(monthDay).toString());
	}
}
