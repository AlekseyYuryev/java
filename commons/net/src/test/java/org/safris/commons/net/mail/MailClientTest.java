/* Copyright (c) 2010 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.commons.net.mail;

import org.junit.Ignore;
import org.junit.Test;

public final class MailClientTest {
  public static void main(final String[] args) throws Exception {
    new MailClientTest().testMailClient();
  }

  @Test
  @Ignore
  public void testMailClient() throws Exception {
    final SMTPCredentials smtpCredentials = new SMTPCredentials("smtp.safris.com", "filehost", "FileH0st");
    MailClient.send(smtpCredentials, "seva@safris.com", new String[] {
      "seva.safris@gmail.com"
    }, "test", "test");
  }
}