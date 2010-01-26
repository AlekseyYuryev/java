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
    public static void start(InputStream in, OutputStream out, OutputStream err, final ExpectCallback callback, File scriptFile) throws Exception {
        final ex_script script = (ex_script)Bindings.parse(new InputSource(new FileInputStream(scriptFile)));

        final $ex_processType<?> processType = script.get_process().get(0);
        final String exec = processType.get_exec$().getText().trim();
        final Map<String,String> variables = callback.process(exec);
        final Process process;
        final List<String> args = new ArrayList<String>();
        final StringTokenizer tokenizer = new StringTokenizer(dereference(exec, variables));
        while (tokenizer.hasMoreTokens())
            args.add(tokenizer.nextToken());

        final boolean sync = processType.get_fork$() != null && $ex_processType._fork$.SYNC.equals(processType.get_fork$().getText());
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
                        throw new UnknownError("There is a problem with the regex used to determine the class name. We have matched it twice!!");

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
        // on it such that its buffer doesnt fill. This is necessary because the STDERR of the sub-process
        // is teed into 2 input streams that both need to be read from: System.err, and process.getErrorStream()
        new NonBlockingInputStream(process.getErrorStream(), 1024);
        final InputStream stdout = process.getInputStream();

        HashTree.Node<ScannerHandler> firstTreeNode = null;
        final List<$ex_ruleType<?>> rules = processType.get_rule();
        final Map<String,ScannerHandler> scannerMap = new HashMap<String,ScannerHandler>();
        final Map<String,HashTree.Node<ScannerHandler>> treeNodeMap = new HashMap<String,HashTree.Node<ScannerHandler>>();
        for (final $ex_ruleType<?> rule : rules) {
            final ScannerHandler scanner = new ScannerHandler(rule.get_expect$().getText()) {
                public void match(String match) throws IOException {
                    String response = rule.get_respond$().getText();
                    final Map<String,String> variables = callback.rule(rule.get_id$().getText(), rule.get_expect$().getText(), response);
                    response = dereference(response, variables);
                    if (!response.endsWith("\n"))
                        response += "\n";

                    process.getOutputStream().write(response.getBytes());
                    process.getOutputStream().flush();
                }
            };
            scannerMap.put(rule.get_id$().getText(), scanner);

            final HashTree.Node<ScannerHandler> treeNode = new HashTree.Node<ScannerHandler>(scanner);
            treeNodeMap.put(rule.get_id$().getText(), treeNode);
            if (firstTreeNode == null)
                firstTreeNode = treeNode;
        }

        final List<$ex_processType<?>._tree._node> nodes = processType.get_tree().get(0).get_node();
        for ($ex_processType<?>._tree._node node : nodes) {
            final HashTree.Node<ScannerHandler> treeNode = treeNodeMap.get(node.get_rule$().getText());
            final $ex_processType._tree._node._children$ children = node.get_children$();
            if (children == null)
                continue;

            final List<String> childIds = children.getText();
            for (String childId : childIds)
                treeNode.addChild(treeNodeMap.get(childId));
        }

        final HashTree<ScannerHandler> tree = new HashTree<ScannerHandler>();
        tree.addChild(firstTreeNode);

        final InputStreamScanner scanner = new InputStreamScanner(stdout, tree);
        scanner.start();
    }

    private static String dereference(String string, Map<String,String> variables) throws IOException {
        try {
            return ELs.dereference(string, variables);
        }
        catch (ExpressionFormatException e) {
            throw new IOException(e.getMessage());
        }
    }

    private Expect() {
    }
}
