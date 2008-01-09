package org.safris.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class InputStreamScanner
{
	private final InputStream in;
	private List<HashTree.Node<ScannerHandler>> currentNodes;

	public InputStreamScanner(InputStream in)
	{
		this.in = in;
	}

	public void setScript(HashTree<ScannerHandler> tree)
	{
		currentNodes = tree.getChildren();
	}

	public void scrape()
	{
		new ScrapingThread(in).start();
	}

	private class ScrapingThread extends Thread
	{
		private final InputStream in;

		public ScrapingThread(InputStream in)
		{
			this.in = in;
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
				if("Pipe broken".equals(e.getMessage()))
				{
//					System.err.println(e.getMessage());
					System.exit(0);
				}

				throw new RuntimeException(e);
			}
		}
	}
}
