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

public final class Strings
{
	private static String changeCase(String string, boolean upper, int beginIndex, int endIndex)
	{
		if(string == null || string.length() == 0)
			return string;

		if(beginIndex > endIndex)
			throw new IllegalArgumentException("start {" + beginIndex + "} > end {" + endIndex + "}");

		if(string.length() < beginIndex)
			throw new StringIndexOutOfBoundsException("start index {" + beginIndex + "} > string length {" + string.length() + "}");

		if(endIndex < 0)
			throw new StringIndexOutOfBoundsException("end index {" + endIndex + "} < 0");

		if(beginIndex == endIndex)
			return string;

		if(beginIndex == 0)
		{
			final String caseString = string.substring(beginIndex, endIndex).toLowerCase();
			final String endString = string.substring(endIndex);
			return upper ? caseString.toUpperCase() + endString : caseString.toLowerCase() + endString;
		}

		if(endIndex == string.length())
		{
			final String beginString = string.substring(0, beginIndex);
			final String caseString = string.substring(beginIndex, endIndex).toLowerCase();
			return upper ? beginString + caseString.toUpperCase() : beginString + caseString.toLowerCase();
		}

		final String beginString = string.substring(0, beginIndex);
		final String caseString = string.substring(beginIndex, endIndex).toLowerCase();
		final String endString = string.substring(endIndex);
		return upper ? beginString + caseString.toUpperCase() + endString : beginString + caseString.toLowerCase() + endString;
	}

	public static final String toLowerCase(String string, int beginIndex, int endIndex)
	{
		return changeCase(string, false, beginIndex, endIndex);
	}

	public static final String toLowerCase(String string, int beginIndex)
	{
		return changeCase(string, false, beginIndex, string.length());
	}

	public static final String toUpperCase(String string, int beginIndex, int endIndex)
	{
		return changeCase(string, true, beginIndex, endIndex);
	}

	public static final String toUpperCase(String string, int beginIndex)
	{
		return changeCase(string, true, beginIndex, string.length());
	}

	private Strings()
	{
	}
}
