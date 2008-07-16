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

public class MonthDay
{
	public static MonthDay parseMonthDay(String string)
	{
		if(string == null || string.length() == 0)
			return null;

		int month = 1;
		int day = 1;
		final StringTokenizer tokenizer = new StringTokenizer(string.substring(2), "-Z");
		if(tokenizer.hasMoreTokens())
			month = Integer.parseInt(tokenizer.nextToken());

		if(tokenizer.hasMoreTokens())
			day = Integer.parseInt(tokenizer.nextToken());

		return new MonthDay(month, day);
	}

	private final int month;
	private final int day;

	public MonthDay(int month, int day)
	{
		this.month = month;
		this.day = day;
	}
}
