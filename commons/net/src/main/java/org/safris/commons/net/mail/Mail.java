/* Copyright (c) 2009 Seva Safris
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocketFactory;

public final class Mail {
  private static final Logger logger = Logger.getLogger(Mail.class.getName());

  public static enum Protocol {
    SMTP, SMTPS
  }

  public static class Message {
    public final String subject;
    public final MimeContent content;
    public final String from;
    public final String[] to;

    public Message(final String subject, final MimeContent content, final String from, final String ... to) {
      this.subject = subject;
      if (subject == null)
        throw new NullPointerException("subject == null");

      this.content = content;
      if (content == null)
        throw new NullPointerException("content == null");

      this.from = from;
      if (from == null)
        throw new NullPointerException("from == null");

      this.to = to;
      if (to == null)
        throw new NullPointerException("to == null");

      if (to.length == 0)
        throw new IllegalArgumentException("to.length == 0");
    }

    public boolean equals(final Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof Message))
        return false;

      final Message that = (Message)obj;
      return subject.equals(that.subject) && content.equals(that.content) && from.equals(that.from) && Arrays.equals(to, that.to);
    }

    public int hashCode() {
      int hashCode = 0;
      hashCode += 2 * subject.hashCode();
      hashCode += 3 * content.hashCode();
      hashCode += 5 * from.hashCode();
      hashCode += 7 * to.hashCode();
      return hashCode;
    }
  }

  public static final class Server {
    private static final boolean debug = true;

    static {
      // System.setProperty("javax.net.debug", "ssl,handshake");
    }

    private static final Map<Server,Server> instances = new HashMap<Server,Server>();

    public static Server instance(final Protocol protocol, final String host, final int port) {
      final Server key = new Server(protocol, host, port);
      Server server = instances.get(key);
      if (server != null)
        return server;

      synchronized (instances) {
        if ((server = instances.get(key)) != null)
          return server;

        instances.put(key, server = key);
        return server;
      }
    }

    private final Protocol protocol;
    private final String host;
    private final int port;
    private final Properties defaultProperties;

    private Server(final Protocol protocol, final String host, final int port) {
      this.protocol = protocol;
      if (protocol == null)
        throw new NullPointerException("protocol == null");

      this.host = host;
      if (host == null)
        throw new NullPointerException("host == null");

      this.port = port;
      if (port < 1 || 65535 < port)
        throw new IllegalArgumentException("port [" + port + "] <> (1, 65535)");

      final String protocolString = this.protocol.toString().toLowerCase();

      this.defaultProperties = new Properties();
      defaultProperties.put("mail.debug", "true");
      defaultProperties.put("mail.transport.protocol", protocolString);

      defaultProperties.put("mail." + protocolString + ".debug", Boolean.toString(debug));

      defaultProperties.put("mail." + protocolString + ".host", host);
      defaultProperties.put("mail." + protocolString + ".port", port);

      defaultProperties.put("mail." + protocolString + ".quitwait", "false");

      defaultProperties.put("mail." + protocolString + ".ssl.trust", "*");
      defaultProperties.put("mail." + protocolString + ".starttls.enable", "true");

      if (this.protocol == Protocol.SMTPS) {
        defaultProperties.put("mail." + protocolString + ".ssl.enable", "true");
        defaultProperties.put("mail." + protocolString + ".ssl.protocols", "SSLv3 TLSv1");
        defaultProperties.put("mail." + protocolString + ".socketFactory.class", SSLSocketFactory.class.getName());
        defaultProperties.put("mail." + protocolString + ".socketFactory.port", port);
        defaultProperties.put("mail." + protocolString + ".socketFactory.fallback", "false");
      }
    }

    public boolean send(final Credentials credentials, final String subject, final MimeContent content, final String from, final String ... to) throws MessagingException {
      return send(credentials, new Message(subject, content, from, to)).size() == 0;
    }

    public Set<Message> send(final Credentials credentials, final Message ... message) throws MessagingException {
      final String protocolString = protocol.toString().toLowerCase();
      final Properties properties = new Properties(defaultProperties);
      final Session session;
      if (credentials != null) {
        properties.put("mail." + protocolString + ".auth", "true");
        // the following 2 lines were causing "Relaying denied. Proper authentication required." messages from sendmail
        // properties.put("mail." + protocolString + ".ehlo", "false");
        // properties.put("mail." + protocolString + ".user", credentials.username);

        session = Session.getInstance(properties, new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(credentials.username, credentials.password);
          }
        });
      }
      else {
        session = Session.getInstance(properties);
      }

      final Set<Message> failed = new HashSet<Message>();
      session.setDebug(debug);
      final Transport transport = session.getTransport(protocolString);
      try {
        transport.connect(host, port, credentials.username, credentials.password);
        for (final Message msg : message) {
          logger.info("Email: " + Arrays.toString(msg.to));
          // FIXME: Emails are coming through from a 'default' from address?!?!?!
          session.getProperties().setProperty("mail." + protocolString + ".from", msg.from);
          final MimeMessage mimeMessage = new MimeMessage(session);

          try {
            // FIXME: Emails are coming through from a 'default' from address?!?!?!
            mimeMessage.setFrom(new InternetAddress(msg.from));

            final InternetAddress[] addressesTo = new InternetAddress[msg.to.length];
            for (int i = 0; i < msg.to.length; i++)
              addressesTo[i] = new InternetAddress(msg.to[i]);

            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, addressesTo);

            // Setting the Subject and Content Type
            mimeMessage.setSubject(msg.subject);
            mimeMessage.setContent(msg.content.getContent(), msg.content.getType());

            mimeMessage.saveChanges();
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
          }
          catch (final MessagingException e) {
            logger.throwing(Mail.class.getName(), "send", e);
            failed.add(msg);
          }
        }
      }
      finally {
        transport.close();
      }

      return failed;
    }

    public boolean equals(final Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof Server))
        return false;

      final Server that = (Server)obj;
      return host.equals(that.host) && protocol == that.protocol && port == that.port;
    }

    public int hashCode() {
      int hashCode = 0;
      hashCode += 2 * host.hashCode();
      hashCode *= protocol.ordinal() + 1;
      hashCode += 3 * port;
      return hashCode;
    }
  }

  public static final class Credentials {
    public final String username;
    public final String password;

    public Credentials(final String username, final String password) {
      this.username = username;
      if (username == null)
        throw new NullPointerException("username == null");

      this.password = password;
      if (password == null)
        throw new NullPointerException("password == null");
    }

    public boolean equals(final Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof Credentials))
        return false;

      final Credentials that = (Credentials)obj;
      return username.equals(that.username) && password.equals(that.password);
    }

    public int hashCode() {
      int hashCode = 0;
      hashCode += 2 * username.hashCode();
      hashCode += 3 * password.hashCode();
      return hashCode;
    }
  }
}