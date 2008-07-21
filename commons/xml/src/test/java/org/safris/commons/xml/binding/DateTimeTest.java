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

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateTimeTest
{
	public static void main(String[] args)
	{
		new DateTimeTest().testDateTime();
	}

	private void assertEquals(String s1, String s2)
	{
		final DateTime dateTime1 = DateTime.parseDateTime(s1);
		final DateTime dateTime2 = DateTime.parseDateTime(s2);

		assertEquals(dateTime1, dateTime1);
		assertEquals(dateTime2, dateTime2);
		assertEquals(dateTime1, dateTime2);
	}

	private void assertEquals(DateTime dateTime1, DateTime dateTime2)
	{
		if(dateTime1.equals(dateTime2))
			return;

		fail("for: [ " + dateTime1 + " ], [ " + dateTime2 + " ]\ngot: ![ " + dateTime1 + " ].equals([ " + dateTime2 + " ])\n\t" + dateTime1.getTime() + "\n\t" + dateTime2.getTime());
	}

	@Test
	public void testDateTime()
	{
		final Map<String,String> map = new HashMap<String,String>();
		map.put("2002-10-10T12:00:00+05:00", "2002-10-10T07:00:00Z");
		map.put("2002-10-10T00:00:00+05:00", "2002-10-09T19:00:00Z");
		map.put("1999-10-10T00:00:00-00:00", "1999-10-10T00:00:00Z");
		map.put("2000-03-04T23:00:00-03:00", "2000-03-04T26:00:00Z");
		map.put("2000-03-04T23:00:00-03:45", "2000-03-04T26:45:00Z");
		map.put("2007-06-20T20:43:23.887Z", "2007-06-20T25:43:23.887+05:00");
		map.put("2001-10-26T21:32:52.12679", "2001-10-26T21:32:52.12679");

		for(Map.Entry<String,String> entry : map.entrySet())
			assertEquals(entry.getKey(), entry.getValue());
	}
}
