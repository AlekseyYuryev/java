/* Copyright (c) 2017 Seva Safris
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

package org.safris.commons.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketImplFactory;

public final class Sockets {
  private static boolean socketDefined = false;

  public static void disableNetwork() throws IOException {
    if (socketDefined)
      return;

    socketDefined = true;
    Socket.setSocketImplFactory(new SocketImplFactory() {
      @Override
      public SocketImpl createSocketImpl() {
        return new SocketImpl() {
          @Override
          public void setOption(int optID, Object value) throws SocketException {
          }

          @Override
          public Object getOption(int optID) throws SocketException {
            return null;
          }

          @Override
          protected void create(boolean stream) throws IOException {
          }

          @Override
          protected void connect(String host, int port) throws IOException {
          }

          @Override
          protected void connect(InetAddress address, int port) throws IOException {
          }

          @Override
          protected void connect(SocketAddress address, int timeout) throws IOException {
          }

          @Override
          protected void bind(InetAddress host, int port) throws IOException {
          }

          @Override
          protected void listen(int backlog) throws IOException {
          }

          @Override
          protected void accept(SocketImpl s) throws IOException {
          }

          @Override
          protected InputStream getInputStream() throws IOException {
            return new InputStream() {
              @Override
              public int read() throws IOException {
                throw new IOException();
              }
            };
          }

          @Override
          protected OutputStream getOutputStream() throws IOException {
            return new OutputStream() {
              @Override
              public void write(int b) throws IOException {
                throw new IOException();
              }
            };
          }

          @Override
          protected int available() throws IOException {
            return 0;
          }

          @Override
          protected void close() throws IOException {
          }

          @Override
          protected void sendUrgentData(int data) throws IOException {
          }
        };
      }
    });
  }

  private Sockets() {
  }
}