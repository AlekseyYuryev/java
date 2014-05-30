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

package org.safris.commons.io.scanner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.safris.commons.util.HashTree;

public final class InputStreamScanner extends Thread {
  private final InputStream in;
  private List<HashTree.Node<ScannerHandler>> currentNodes;

  public InputStreamScanner(final InputStream in, final HashTree<ScannerHandler> handlers) {
    super(InputStreamScanner.class.getSimpleName());
    this.in = in;
    currentNodes = handlers != null ? handlers.getChildren() : null;
  }

  private boolean onMatch(final String line, final List<HashTree.Node<ScannerHandler>> nodes) throws IOException {
    boolean match = false;
    for (final HashTree.Node<ScannerHandler> node : nodes) {
      if (node.getValue() != null) {
        if (line.matches(node.getValue().getMatch())) {
          match = true;
          node.getValue().match(line);
          if (node.hasChildren())
            currentNodes = node.getChildren();
        }
      }
      else {
        for (final HashTree.Node<ScannerHandler> child : node.getChildren())
          onMatch(line, child.getChildren());
      }
    }

    return match;
  }

  public void run() {
    String line = "";
    try {
      char ch = 0;
      while ((ch = (char)in.read()) != -1) {
        if (ch != '\n') {
          if (ch != ' ' || line.length() != 0)
            line += ch;
        }
        else {
          line = "";
        }

        if (currentNodes == null)
          continue;

        if (onMatch(line, currentNodes))
          line = "";
      }
    }
    catch (final Exception e) {
      if ("Pipe broken".equals(e.getMessage()))
        return;

      throw new RuntimeException(e);
    }
    finally {
      try {
        notifyAll();
      }
      catch (final IllegalMonitorStateException e) {
      }
    }
  }
}