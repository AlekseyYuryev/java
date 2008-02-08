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
