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

public class MonthTest
{
	public static void main(String[] args)
	{
		new MonthTest().testMonth();
	}

	@Test
	public void testMonth()
	{
		try
		{
			Month.parseMonth(null);
			fail("Expected a NullPointerException");
		}
		catch(NullPointerException e)
		{
		}

		try
		{
			Month.parseMonth("");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Month.parseMonth("---5");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Month.parseMonth("-5");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Month.parseMonth("--A");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Month.parseMonth("--00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Month.parseMonth("--13");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Month.parseMonth("--4");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Month.parseMonth("--11Z-");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Time.parseTime("--12-15:00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Time.parseTime("--07+14:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Time.parseTime("--02+14:60.9");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		final String[] months = new String[]
		{
			"--12",
			"--04",
			"--03",
			"--02",
			"--01",
			"--01Z",
			"--07+01:00",
			"--09-01:00",
			"--10Z",
			"--11+12:00",
			"--12-12:30"
		};

		for(String month : months)
			assertEquals(month, Month.parseMonth(month).toString());
	}
}
