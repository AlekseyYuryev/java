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

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date extends java.util.Date
{
	public static Date parseDate(String string)
	{
		try
		{
			return new Date(dateFormat.parse(string).getTime());
		}
		catch(ParseException e)
		{
			IllegalArgumentException illegalArgumentException = new IllegalArgumentException(string);
			illegalArgumentException.setStackTrace(e.getStackTrace());
			throw illegalArgumentException;
		}
	}

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public Date()
	{
		super();
    }

    protected Date(String s)
	{
		super(s);
	}

	public Date(long date)
	{
		super(date);
    }

	public Date(int year, int month, int date)
	{
		super(year - 1900, month - 1, date);
	}

	public int getYear()
	{
		return super.getYear() + 1900;
	}

	public int getMonth()
	{
		return super.getMonth() + 1;
	}

	public String toString()
	{
		return dateFormat.format(this);
	}
}
