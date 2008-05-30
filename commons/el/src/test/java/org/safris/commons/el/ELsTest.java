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

package org.safris.commons.el;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.*;

public class ELsTest
{
	public static void main(String[] args)
	{
		new ELsTest().testDereference();
	}

	@Test
	public void testDereference()
	{
		final Map<String,String> variables = new HashMap<String,String>();
		variables.put("right", "RIGHT");
		variables.put("left", "LEFT");
		variables.put("middle", "MIDDLE");

		final Map<String,String> map = new HashMap<String,String>();
		map.put("this string has a token on the ${right}", "this string has a token on the RIGHT");
		map.put("${left} token here", "LEFT token here");
		map.put("something in the ${middle} of this string", "something in the MIDDLE of this string");

		for(Map.Entry<String,String> entry : map.entrySet())
			assertEquals(ELs.dereference(entry.getKey(), variables), entry.getValue());

		assertNull(ELs.dereference(null, variables));
		assertNull(ELs.dereference(null, null));
		final String same = "string with ${a} variable";
		assertEquals(ELs.dereference(same, null), same);

		try
		{
			ELs.dereference("expect an ${exception here", variables);
			fail("Expected a ExpressionFormatException");
		}
		catch(ExpressionFormatException e)
		{
		}
	}
}
