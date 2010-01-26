/*  Copyright 2010 Safris Technologies Inc.
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

package org.safris.commons.io.scanner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.safris.commons.util.HashTree;

public final class InputStreamScanner extends Thread {
    private final InputStream in;
    private List<HashTree.Node<ScannerHandler>> currentNodes;

    public InputStreamScanner(InputStream in, HashTree<ScannerHandler> handlers) {
        super(InputStreamScanner.class.getSimpleName());
        this.in = in;
        currentNodes = handlers != null ? handlers.getChildren() : null;
    }

    private boolean onMatch(String line, List<HashTree.Node<ScannerHandler>> nodes) throws IOException {
        boolean match = false;
        for (HashTree.Node<ScannerHandler> node : nodes) {
            if (node.getValue() != null) {
                if (line.matches(node.getValue().getMatch())) {
                    match = true;
                    node.getValue().match(line);
                    if (node.hasChildren())
                        currentNodes = node.getChildren();
                }
            }
            else {
                for (HashTree.Node<ScannerHandler> child : node.getChildren())
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
                    if (ch == ' ' && line.length() == 0)
                        continue;

                    line += ch;
                }
                else
                    line = "";

                if (currentNodes == null)
                    continue;

                if (onMatch(line, currentNodes))
                    line = "";
            }
        }
        catch (Exception e) {
            if ("Pipe broken".equals(e.getMessage()))
                return;

            throw new RuntimeException(e);
        }
        finally {
            try {
                notifyAll();
            }
            catch (IllegalMonitorStateException e) {
            }
        }
    }
}
