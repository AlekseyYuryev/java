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

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class MailTest {
  @Test
  @Ignore
  public void testClient() throws Exception {
    final Mail.Credentials smtpCredentials = new Mail.Credentials("filehost", "FileH0st");
    final Mail.Server server = Mail.Server.instance(Mail.Protocol.SMTP, "smtp.safris.com", 465);
    final Mail.Message message1 = new Mail.Message("test1", new MimeContent("test1", "text/html"), "seva@safris.org", "seva.safris@gmail.com");
    final Mail.Message message2 = new Mail.Message("test2", new MimeContent("test2", "text/html"), "seva@safris.com", "safris@berkeley.edu");
    final Mail.Message message3 = new Mail.Message("test3", new MimeContent("test3", "text/html"), "seva@safris.biz", "seva@djseva.com");
    Assert.assertEquals(0, server.send(smtpCredentials, message1, message2, message3).size());
  }
}