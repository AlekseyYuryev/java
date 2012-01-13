/*  Copyright Safris Software 2010
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

package org.safris.commons.net.mail;

import org.junit.Ignore;
import org.junit.Test;

public class MailClientTest {
  public static void main(String[] args) throws Exception {
    new MailClientTest().testMailClient();
    }

    @Test
  @Ignore
    public void testMailClient() throws Exception {
    final SMTPCredentials smtpCredentials = new SMTPCredentials("smtp.safris.com", "filehost", "FileH0st");
    MailClient.send(smtpCredentials, "seva@safris.com", new String[]{"seva.safris@gmail.com"}, "test", "test");
  }
}
