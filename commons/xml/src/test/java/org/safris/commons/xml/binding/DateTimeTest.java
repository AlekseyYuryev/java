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

public class DateTimeTest
{
	public static void main(String[] args)
	{
		new DateTimeTest().testDateTime();
	}

	@Test
	public void testDateTime()
	{
		try
		{
			DateTime.parseDateTime(null);
			fail("Expected a NullPointerException");
		}
		catch(NullPointerException e)
		{
		}

		try
		{
			DateTime.parseDateTime("");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("010");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("10");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("100");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("AAA");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-1");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-1Z");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-10-1");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-10-00Z");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-10-1Z");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-13-08-11:00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-12-08-15:00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-01-08+14:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-00-08+10:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-02-08+14:60.9");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-01-32+12:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-02-30+10:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-04-31+12:60.9");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}
		try
		{
			DateTime.parseDateTime("2227-12-08T12:30:61-11:00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-12-08T26:50:31-12:00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-12-08T13:50:31-15:00");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-01-08T10:90:01+12:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-01-08T00:00:00+10:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-02-08T12:12:12:22+14:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-01-32T13:13:13+12:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-02-30T05:01:01+10:60");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			DateTime.parseDateTime("2227-04-31TP04:20:00+12:60.9");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		final String[] dateTimes = new String[]
		{
			"2500-01-01T04:20:00",
			"1400-02-02T12:30:45.678",
			"0003-03-03T12:34:56.789",
			"0020-04-04T01:23:45.678",
			"0310-05-05T23:46:57.89",
			"1001-06-06T04:20:00Z",
			"2007-07-07T12:30:45.678+01:00",
			"3017-08-08T12:34:56.789-01:00",
			"4027-09-09T01:23:45.678Z",
			"1302-10-10T23:46:57.89+12:00",
			"1112-01-11T12:34:56.789-12:30"
		};

		for(String dateTime : dateTimes)
			assertEquals(dateTime, DateTime.parseDateTime(dateTime).toString());
	}
}
