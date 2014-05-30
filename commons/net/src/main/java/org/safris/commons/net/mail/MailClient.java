/*  Copyright Safris Software 2009
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

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class MailClient {
  public static void send(final String from, final String[] to, String subject, final String body) throws MessagingException {
    MailClient.send(null, from, to, subject, body);
  }

  public static void send(final SMTPCredentials credentials, final String from, String[] to, final String subject, final String body) throws MessagingException {
    if (from == null)
      throw new NullPointerException("from == null");

    if (to == null)
      throw new NullPointerException("to == null");

    if (to.length == 0)
      throw new IllegalArgumentException("to.length == 0");

    if (subject == null)
      throw new NullPointerException("subject == null");

    if (body == null)
      throw new NullPointerException("body == null");

    final boolean debug = true;
    final String protocol = "smtp";
    final int port = 25;

    // System.setProperty("javax.net.debug", "ssl,handshake");

    final Properties properties = new Properties();
    properties.put("mail.transport.protocol", protocol);
    properties.put("mail.from", from);

    properties.put("mail." + protocol + ".debug", Boolean.toString(debug));
    properties.put("mail." + protocol + ".port", port);
    properties.put("mail." + protocol + ".quitwait", "false");
    if ("smtps".equals(protocol)) {
      properties.put("mail." + protocol + ".ssl.enable", "true");
      properties.put("mail." + protocol + ".ssl.protocols", "SSLv3 TLSv1");
      properties.put("mail." + protocol + ".starttls.enable", "true");
      properties.put("mail." + protocol + ".starttls.required", "true");
      properties.put("mail." + protocol + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.put("mail." + protocol + ".socketFactory.port", port);
      properties.put("mail." + protocol + ".socketFactory.fallback", "false");
    }

    final Session session;
    if (credentials != null) {
      if (credentials.getHostname() != null)
        properties.put("mail." + protocol + ".host", credentials.getHostname());

      properties.put("mail." + protocol + ".auth", Boolean.toString(true));
      properties.put("mail." + protocol + ".ehlo", "false");
      properties.put("mail." + protocol + ".user", credentials.getUsername());
      final Authenticator authenticator = new Authenticator() {
        public PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(credentials.getUsername(), credentials.getPassword());
        }
      };

      session = Session.getInstance(properties, authenticator);
    }
    else {
      session = Session.getInstance(properties);
    }

    session.setDebug(debug);

    // create a message
    final Message message = new MimeMessage(session);

    // set the from and to address
    message.setFrom(new InternetAddress(from));

    final InternetAddress[] addressesTo = new InternetAddress[to.length];
    for (int i = 0; i < to.length; i++)
      addressesTo[i] = new InternetAddress(to[i]);

    message.setRecipients(Message.RecipientType.TO, addressesTo);

    // Setting the Subject and Content Type
    message.setSubject(subject);
    message.setText(body);

    if (credentials != null) {
      final Transport transport = session.getTransport(protocol);
      message.saveChanges();
      try {
        transport.connect(credentials.getHostname(), port, credentials.getUsername(), credentials.getPassword());
        transport.sendMessage(message, message.getAllRecipients());
      }
      finally {
        transport.close();
      }
    }
    else {
      Transport.send(message, message.getAllRecipients());
    }
  }

  private MailClient() {
  }
}