/*  Copyright Safris Software 2008
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

package org.safris.commons.expect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.safris.commons.el.ELs;
import org.safris.commons.el.ExpressionFormatException;
import org.safris.commons.exec.Processes;
import org.safris.commons.io.input.NonBlockingInputStream;
import org.safris.commons.io.scanner.InputStreamScanner;
import org.safris.commons.io.scanner.ScannerHandler;
import org.safris.commons.util.HashTree;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public final class Expect {
  public static void start(final InputStream in, final OutputStream out, final OutputStream err, final ExpectCallback callback, final File scriptFile) throws Exception {
    final ex_script script = (ex_script)Bindings.parse(new InputSource(new FileInputStream(scriptFile)));

    final $ex_processType processType = script._process(0);
    final String exec = processType._exec$().text().trim();
    final Map<String,String> variables = callback.process(exec);
    final Process process;
    final List<String> args = new ArrayList<String>();
    final StringTokenizer tokenizer = new StringTokenizer(dereference(exec, variables));
    while (tokenizer.hasMoreTokens())
      args.add(tokenizer.nextToken());

    final boolean sync = processType._fork$() != null && $ex_processType._fork$.SYNC.equals(processType._fork$().text());
    if (exec.startsWith("java")) {
      String className = null;
      final Map<String,String> props = new HashMap<String,String>();
      final List<String> javaArgs = new ArrayList<String>();
      for (String arg : args) {
        if (arg.startsWith("-D")) {
          arg = arg.substring(2);
          final String[] split = arg.split("=", 2);
          props.put(split[0], split[1]);
        }
        else if (arg.matches("([_a-zA-Z0-9]+\\.)+[_a-zA-Z0-9]+")) {
          if (className != null)
            throw new UnknownError("There is a problem with the regex used to determine the final class name. We have matched it twice!!");

          className = arg;
        }
        else if (!arg.equals("java"))
          javaArgs.add(arg);
      }

      if (sync)
        process = Processes.forkSync(in, out, err, props, Class.forName(className), javaArgs.toArray(new String[javaArgs.size()]));
      else
        process = Processes.forkAsync(in, out, err, props, Class.forName(className), javaArgs.toArray(new String[javaArgs.size()]));
    }
    else {
      if (sync)
        process = Processes.forkSync(in, out, err, new String[args.size()]);
      else
        process = Processes.forkAsync(in, out, err, args.toArray(new String[args.size()]));
    }

    // This is important: since we are not reading from STDERR, we must start a NonBlockingInputStream
    // on it such that its buffer doesn't fill. This is necessary because the STDERR of the sub-process
    // is teed into 2 input streams that both need to be read from: System.err, and process.getErrorStream()
    new NonBlockingInputStream(process.getErrorStream(), 1024);
    final InputStream stdout = process.getInputStream();

    HashTree.Node<ScannerHandler> firstTreeNode = null;
    final List<$ex_ruleType> rules = processType._rule();
    final Map<String,ScannerHandler> scannerMap = new HashMap<String,ScannerHandler>();
    final Map<String,HashTree.Node<ScannerHandler>> treeNodeMap = new HashMap<String,HashTree.Node<ScannerHandler>>();
    for (final $ex_ruleType rule : rules) {
      final ScannerHandler scanner = new ScannerHandler(rule._expect$().text()) {
        public void match(final String match) throws IOException {
          String response = rule._respond$().text();
          final Map<String,String> variables = callback.rule(rule._id$().text(), rule._expect$().text(), response);
          response = dereference(response, variables);
          if (!response.endsWith("\n"))
            response += "\n";

          process.getOutputStream().write(response.getBytes());
          process.getOutputStream().flush();
        }
      };
      scannerMap.put(rule._id$().text(), scanner);

      final HashTree.Node<ScannerHandler> treeNode = new HashTree.Node<ScannerHandler>(scanner);
      treeNodeMap.put(rule._id$().text(), treeNode);
      if (firstTreeNode == null)
        firstTreeNode = treeNode;
    }

    final List<$ex_processType._tree._node> nodes = processType._tree(0)._node();
    for (final $ex_processType._tree._node node : nodes) {
      final HashTree.Node<ScannerHandler> treeNode = treeNodeMap.get(node._rule$().text());
      final $ex_processType._tree._node._children$ children = node._children$();
      if (children == null)
        continue;

      final List<String> childIds = children.text();
      for (final String childId : childIds)
        treeNode.addChild(treeNodeMap.get(childId));
    }

    final HashTree<ScannerHandler> tree = new HashTree<ScannerHandler>();
    tree.addChild(firstTreeNode);

    final InputStreamScanner scanner = new InputStreamScanner(stdout, tree);
    scanner.start();
  }

  private static String dereference(final String string, final Map<String,String> variables) throws IOException {
    try {
      return ELs.dereference(string, variables);
    }
    catch (final ExpressionFormatException e) {
      throw new IOException(e.getMessage());
    }
  }

  private Expect() {
  }
}