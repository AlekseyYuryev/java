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

public class DurationTest
{
	public static void main(String[] args)
	{
		new DurationTest().testDuration();
	}

	@Test
	public void testDuration()
	{
		try
		{
			Duration.parseDuration(null);
			fail("Expected a NullPointerException");
		}
		catch(NullPointerException e)
		{
		}

		try
		{
			Duration.parseDuration("");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("X1347Y");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("PTT347Y");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P16349286492843693864932864932864293864Y3M5DT7H10M3.3S");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1.Y3M5DT7H10M3.3S");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M5D3.3S");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M5DT7H10M3.3S3.3S");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M5DT7H10M3S3S");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M5DT7H10M10M3S");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M5DT7H3S10M");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M5DT7H7H10M3S");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M5DT10M7H3S");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M5DT3S7H10M");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M5DT7H10M3Y");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3Y3M5D");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P3M1Y5D");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P3D1Y");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M3M5D");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y5D3M");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M3D3D");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M3D7H");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		try
		{
			Duration.parseDuration("P1Y3M3D7S");
			fail("Expected a IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}

		final String[] durations = new String[]
		{
			"P3Y",
			"-P1Y",
			"P10M",
			"-P8M",
			"P7D",
			"-P2D",
			"PT7H",
			"-PT9H",
			"PT8M",
			"-PT1M",
			"PT5S",
			"-PT4S",
			"PT5.555S",
			"-PT4.332S",
			"P3Y4M",
			"-P13Y34M",

			"P1Y3M5DT7H10M3.3S",
			"P1M",
			"P1D"
		};

		for(String duration : durations)
			assertEquals(duration, Duration.parseDuration(duration).toString());
	}
}
