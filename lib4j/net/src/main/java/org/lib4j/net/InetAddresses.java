/* Copyright (c) 2015 lib4j
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

package org.lib4j.net;

import java.net.InetAddress;

public class InetAddresses {
  public static String toStringIP(final InetAddress address) {
    final byte[] bytes = address.getAddress();
    final StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < bytes.length; i++) {
      if (i > 0)
        buffer.append(".");

      buffer.append(bytes[i] & 0xFF);
    }

    return buffer.toString();
  }

  private InetAddresses() {
  }
}