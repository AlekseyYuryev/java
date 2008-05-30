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

package org.safris.commons.lang;

public final class Numbers
{
	public static boolean isNumber(String s)
	{
		if(s == null || (s = s.trim()).length() == 0)
			return false;

		int exponent = Integer.MIN_VALUE;
		int dot = Integer.MIN_VALUE;
		boolean negative = false;
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(c < '0')
			{
				if(c == '.')
				{
					if(dot != Integer.MIN_VALUE)
						return false;

					dot = i;
				}
				else if(c == '-')
				{
					if(i != exponent + 1 && i != 0)
						return false;
					else
						negative = true;
				}
				else
					return false;
			}
			else if('9' < c)
			{
				if(c == 'E')
				{
					if(1 < exponent || (negative && i == 1) || i - 1 == dot)
						return false;

					exponent = i;
				}
				else
					return false;
			}
		}

		return true;
	}

	public static String roundInsignificant(String value)
	{
		if(value == null)
			return null;

		if(value.length() == 0)
			return value;

		int i = value.length();
		while(i-- > 0)
			if(value.charAt(i) != '0' && value.charAt(i) != '.')
				break;

		return value.substring(0, i + 1);
	}

	private Numbers()
	{
	}
}
