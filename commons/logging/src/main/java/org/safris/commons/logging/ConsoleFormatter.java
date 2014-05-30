/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.logging;

import java.text.MessageFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class ConsoleFormatter extends Formatter {
  // Line separator string.  This is the value of the line.separator
  // property at the moment that the SimpleFormatter was created.
  private static final String format = "{0,date} {0,time}";
  private static final String newline = System.getProperty("line.separator");
  private final MessageFormat formatter;

  public ConsoleFormatter() {
    formatter = new MessageFormat(format);
  }

  /**
   * Format the given LogRecord.
   * @param record the log record to be formatted.
   * @return a formatted log record
   */
  public String format(final LogRecord record) {
    synchronized (formatter) {
      final StringBuffer buffer = new StringBuffer();
      final String message = formatMessage(record);
//          buffer.append("[").append(record.getLevel().getLocalizedName()).append("] ");
      buffer.append(message);
      buffer.append(newline);
      return buffer.toString();
    }
  }
}