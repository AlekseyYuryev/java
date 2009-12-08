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
    public static void send(String from, String[] to, String subject, String body) throws MessagingException {
        MailClient.send(null, from, to, subject, body);
    }

    public static void send(final SMTPCredentials credentials, String from, String[] to, String subject, String body) throws MessagingException {
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

        final boolean debug = false;

        //Set the host smtp address
        final Properties properties = new Properties();
        if (credentials != null && credentials.getHostname() != null)
            properties.put("mail.smtp.host", credentials.getHostname());

        properties.put("mail.smtp.auth", Boolean.toString(credentials != null));
        final Session session;
        if (credentials != null) {
            final Authenticator authenticator = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(credentials.getUsername(), credentials.getPassword());
                }
            };

            session = Session.getDefaultInstance(properties, authenticator);
        }
        else
            session = Session.getDefaultInstance(properties);

        session.setDebug(debug);

        // create a message
        final Message message = new MimeMessage(session);

        // set the from and to address
        final InternetAddress addressFrom = new InternetAddress(from);
        message.setFrom(addressFrom);

        final InternetAddress[] addressesTo = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++)
            addressesTo[i] = new InternetAddress(to[i]);

        message.setRecipients(Message.RecipientType.TO, addressesTo);

        // Setting the Subject and Content Type
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    private MailClient() {
    }
}
