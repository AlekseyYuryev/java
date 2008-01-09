package org.safris.web.depiction;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.swing.JFrame;
import org.safris.web.depiction.FileFind;
import org.safris.web.depiction.ImageFile;
import org.safris.web.depiction.ThumbnailException;

public class Test
{
	public static void main(String[] args)
	{
		File dir = null;
		if(args[0] == null || !(dir = new File(args[0])).isDirectory())
		{
			System.out.println("input must be a directory name");
			System.exit(0);
		}
		else
		{
			System.out.println("Input dir = " + args[0]);
		}
		
		List<File> files = FileFind.ls(dir);
		
		for(File file : files)
		{
			if(!(file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG")) || file.getName().startsWith(".thumb.") || file.getName().endsWith(".tmp"))
				continue;
			
			BufferedImage image = null;
			ImageFile imageFile = new ImageFile(file, 120);
			try
			{
				image = imageFile.getThumbnail();
			}
			catch(ThumbnailException e)
			{
				e.printStackTrace();
				continue;
			}
			
			JFrame frame = new ThumbnailFrame(file.getName());
			Canvas canvas = new ThumbnailViewer(image);
			frame.setSize(new Dimension(image.getWidth(null) + 10, image.getHeight(null) + 26));
			frame.add(canvas);
			frame.pack();
			frame.reshape(50, 50, image.getWidth(null) + 10, image.getHeight(null) + 26);
			frame.show(true);
		}
	}
	
	static class ThumbnailFrame extends JFrame
	{
		public ThumbnailFrame(String name)
		{
			super(name);
			addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			});
		}
	}
	
	static class ThumbnailViewer extends Canvas
	{
		private final Image image;
		
		public ThumbnailViewer(Image image)
		{
			this.image = image;
		}
		
		public void paint(Graphics g)
		{
			g.drawImage(image, 0, 0, null);
		}
	}
}
