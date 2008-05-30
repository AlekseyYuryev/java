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

package org.safris.commons.io.scanner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.safris.commons.util.HashTree;

public class InputStreamScanner extends Thread
{
	private final InputStream in;
	private final HashTree<ScannerHandler> tree;
	private List<HashTree.Node<ScannerHandler>> currentNodes;

	public InputStreamScanner(InputStream in, HashTree<ScannerHandler> tree)
	{
		this.in = in;
		this.tree = tree;
		currentNodes = tree != null ? tree.getChildren() : null;
	}

	private void doMatch(String line, List<HashTree.Node<ScannerHandler>> nodes) throws IOException
	{
		for(HashTree.Node<ScannerHandler> node : nodes)
		{
			if(node.getValue() != null)
			{
				if(line.matches(node.getValue().getMatch()))
				{
					node.getValue().match(line);
					if(node.hasChildren())
						currentNodes = node.getChildren();
				}
			}
			else
			{
				for(HashTree.Node<ScannerHandler> child : node.getChildren())
					doMatch(line, child.getChildren());
			}
		}
	}

	public void run()
	{
		String line = "";
		try
		{
			char ch = 0;
			while((ch = (char)in.read()) != -1)
			{
				if(ch == '\n')
					line = "";
				else
					line += ch;

				if(currentNodes == null)
					continue;

				doMatch(line, currentNodes);
			}
		}
		catch(Exception e)
		{
			// FIXME: Have to NOT System.exit() here.
			if("Pipe broken".equals(e.getMessage()))
				System.exit(0);

			throw new RuntimeException(e);
		}
		finally
		{
			notifyAll();
		}
	}
}
