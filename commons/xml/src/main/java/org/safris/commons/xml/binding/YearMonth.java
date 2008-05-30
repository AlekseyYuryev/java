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

import java.util.StringTokenizer;

public class YearMonth
{
	public static YearMonth parseYearMonth(String string)
	{
		if(string == null || string.length() == 0)
			return null;

		int year = 1;
		int month = 1;
		StringTokenizer tokenizer = new StringTokenizer(string, "-");
		if(tokenizer.hasMoreTokens())
			year = Integer.parseInt(tokenizer.nextToken());

		if(tokenizer.hasMoreTokens())
			month = Integer.parseInt(tokenizer.nextToken());

		return new YearMonth(year, month);
	}

	private final int year;
	private final int month;

	public YearMonth(int year, int month)
	{
		this.year = year;
		this.month = month;
	}
}
