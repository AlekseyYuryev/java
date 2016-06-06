package org.safris.commons.net;

import java.io.IOException;
import java.net.ServerSocket;

public final class Ports {
  public static int findOpenPort(final int from, final int to) throws IOException {
    for (int port = from; port < to; port ++) {
      try (final ServerSocket socket = new ServerSocket(port)) {
        return socket.getLocalPort();
      }
      catch (final IOException e) {
        continue;
      }
    }

    throw new IOException("no available port found");
  }

  public static int findRandomOpenPort() throws IOException {
    try (final ServerSocket socket = new ServerSocket(0)) {
      return socket.getLocalPort();
    }
  }

  private Ports() {
  }
}