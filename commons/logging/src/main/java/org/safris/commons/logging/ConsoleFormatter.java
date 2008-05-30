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

package org.safris.commons.logging;

import java.text.MessageFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleFormatter extends Formatter
{
	// Line separator string.  This is the value of the line.separator
	// property at the moment that the SimpleFormatter was created.
	private static final String format = "{0,date} {0,time}";
	private static final String newline = System.getProperty("line.separator");
	private final MessageFormat formatter;

	public ConsoleFormatter()
	{
		formatter = new MessageFormat(format);
	}

	/**
	 * Format the given LogRecord.
	 * @param record the log record to be formatted.
	 * @return a formatted log record
	 */
	public String format(LogRecord record)
	{
		synchronized(formatter)
		{
			final StringBuffer buffer = new StringBuffer();
			final String message = formatMessage(record);
//			buffer.append("[").append(record.getLevel().getLocalizedName()).append("] ");
			buffer.append(message);
			buffer.append(newline);
			return buffer.toString();
		}
	}
}
