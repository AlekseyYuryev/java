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
import java.util.Date;
import java.util.TimeZone;

public class Time extends Date
{
	public static Time parseTime(String string)
	{
		try
		{
			return new Time(dateFormat.parse(string).getTime());
		}
		catch(ParseException e)
		{
			IllegalArgumentException illegalArgumentException = new IllegalArgumentException(string);
			illegalArgumentException.setStackTrace(e.getStackTrace());
			throw illegalArgumentException;
		}
	}

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss'Z'");

	static
	{
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public Time()
	{
		super();
    }

    public Time(String s)
	{
		super(s);
	}

	public Time(long date)
	{
		super(date);
    }

	public Time(int hour, int minute, int second)
	{
		super(0, 0, 0, hour, minute, second);
	}
}
