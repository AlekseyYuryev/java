package org.safris.commons.util.logging;

import java.security.AccessController;
import java.text.MessageFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import sun.security.action.GetPropertyAction;

public class ConsoleFormatter extends Formatter
{
	// Line separator string.  This is the value of the line.separator
	// property at the moment that the SimpleFormatter was created.
	private final static String lineSeparator = AccessController.doPrivileged(new GetPropertyAction("line.separator"));
	private final static String format = "{0,date} {0,time}";
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
		synchronized(lineSeparator)
		{
			final StringBuffer buffer = new StringBuffer();
			final String message = formatMessage(record);
//			buffer.append("[").append(record.getLevel().getLocalizedName()).append("] ");
			buffer.append(message);
			buffer.append(lineSeparator);
			return buffer.toString();
		}
	}
}
