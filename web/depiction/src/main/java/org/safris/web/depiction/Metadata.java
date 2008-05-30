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

package org.safris.web.depiction;

import com.nwalsh.jpegrdf.JpegRDF;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import org.safris.web.depiction.Comment;
import org.safris.web.depiction.CommentException;
import org.safris.web.depiction.HTMLEncode;
import org.safris.web.depiction.ImageFile;
import org.safris.web.depiction.ImageServlet;
import org.safris.web.depiction.Thumbnail;
import org.safris.web.depiction.ThumbnailException;
import org.safris.web.depiction.util.CopyFile;

public class Metadata
{
	private final File file;
	private static final String QNAME = "exif:comment";
	private Thumbnail thumbnail = null;

	public Metadata(File file)
	{
		if(!file.exists())
			throw new IllegalArgumentException(file.getPath() + "\ndoes not exist.");

		this.file = file;
	}

	public File getFile()
	{
		return file;
	}

	private void exifToRDF() throws CommentException
	{
		synchronized(file.getPath())
		{
			try
			{
				new JpegRDF(new String[]
				{
					"-c",
					ImageServlet.getJpegrdf(),
					"-exif",
					file.getPath()
				});
			}
			catch(Exception e)
			{
				throw new CommentException(e);
			}
		}
	}

	public void deleteComment(Comment comment) throws CommentException
	{
		synchronized(file.getPath())
		{
			try
			{
				new JpegRDF(new String[]
				{
					"-c",
					ImageServlet.getJpegrdf(),
					"-dl",
					QNAME,
					Comment.encode(comment),
					file.getPath()
				});
			}
			catch(Exception e)
			{
				throw new CommentException(e);
			}
		}
	}

	public List<Comment> getComment() throws CommentException
	{
		try
		{
			JpegRDF jpegRDF = new JpegRDF(new String[]
			{
				"-c",
				ImageServlet.getJpegrdf(),
				"-q",
				QNAME,
				file.getPath()
			});

			return jpegRDF.getRDF(QNAME);
		}
		catch(Exception e)
		{
			throw new CommentException(e);
		}
	}

	public void addComment(Comment comment) throws CommentException
	{
		exifToRDF();

		synchronized(file.getPath())
		{
			try
			{
				new JpegRDF(new String[]
				{
					"-c",
					ImageServlet.getJpegrdf(),
					"-al",
					QNAME,
					Comment.encode(comment),
					file.getPath()
				});

			}
			catch(Exception e)
			{
				throw new CommentException(e);
			}

			// FIXME: You know what this is for. Make it right!
			System.err.println("[RDF] COMMENT (" + file.getPath() + "): " + comment);
		}
	}

	public Thumbnail getThumbnail() throws ThumbnailException
	{
		if(thumbnail != null)
			return thumbnail;

		synchronized(file.getPath())
		{
			if(thumbnail != null)
				return thumbnail;

			ImageInputStream imageInputStream = null;
			try
			{
				imageInputStream = ImageIO.createImageInputStream(file);
			}
			catch(IOException e)
			{
				throw new ThumbnailException(e);
			}

			try
			{
				thumbnail = new Thumbnail(imageInputStream);
			}
			catch(IOException e)
			{
				throw new ThumbnailException(file.getPath(), e);
			}
			finally
			{
				try
				{
					imageInputStream.close();
				}
				catch(IOException e)
				{
				}
			}

			if(thumbnail.getImage() == null)
				return thumbnail = null;

			File thumb = ImageFile.getThumbnailFile(file);
			if(!thumb.exists())
			{
				ImageOutputStream thumbOutputStream = null;
				try
				{
					thumbOutputStream = ImageIO.createImageOutputStream(thumb);

					ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
					writer.setOutput(thumbOutputStream);

					JPEGImageWriteParam imageWriteParam = new JPEGImageWriteParam(Locale.US);
					imageWriteParam.setOptimizeHuffmanTables(true);
					imageWriteParam.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
					imageWriteParam.setCompressionQuality(ImageServlet.getThumbnailQuality());

					IIOImage iioImage = new IIOImage(thumbnail.getImage(), null, null);
					writer.write(null, iioImage, imageWriteParam);
				}
				catch(IOException e)
				{
					throw new ThumbnailException(file.getPath(), e);
				}
				finally
				{
					try
					{
						thumbOutputStream.close();
					}
					catch(IOException e)
					{
					}
				}
			}

			return thumbnail;
		}
	}

	public Thumbnail getThumbnail(int size) throws ThumbnailException
	{
		synchronized(file.getPath())
		{
			ImageInputStream imageInputStream = null;
			try
			{
				imageInputStream = ImageIO.createImageInputStream(file);
			}
			catch(IOException e)
			{
				throw new ThumbnailException(e);
			}

			if(imageInputStream == null)
			{
				System.err.println("Unable to open stream for: " + file.getPath());
				return null;
			}

			try
			{
				thumbnail = new Thumbnail(imageInputStream);
			}
			catch(IOException e)
			{
				throw new ThumbnailException(file.getPath(), e);
			}
			finally
			{
				try
				{
					imageInputStream.close();
				}
				catch(IOException e)
				{
				}
			}

			BufferedImage image = thumbnail.getImage();
			File thumb = ImageFile.getThumbnailFile(file);
			if(image == null || (size != image.getWidth() && size != image.getHeight()))
			{
				ImageOutputStream imageOutputStream = null;
				try
				{
					imageInputStream = ImageIO.createImageInputStream(file);
					File temp = new File(file.getPath() + ".tmp");
					imageOutputStream = ImageIO.createImageOutputStream(temp);
					thumbnail = new Thumbnail(imageInputStream, imageOutputStream, size);
					imageOutputStream.close();
					imageInputStream.close();
					File bak = new File(file.getPath() + ".bak");
					if(file.renameTo(bak))
					{
						if(temp.renameTo(file))
						{
							if(!bak.delete())
								System.err.println("Unable to delete: " + file.getPath());
						}
						else if(!bak.renameTo(file))
						{
							temp.delete();
							throw new ThumbnailException("File: " + file.getPath() + "\n  renamed to: " + bak.getPath());
						}
					}
					else
					{
						temp.delete();
						throw new ThumbnailException("Unable to move temp file over original");
					}
					image = thumbnail.getImage();
				}
				catch(IOException e)
				{
					throw new ThumbnailException(file.getPath(), e);
				}

				writeThumbFile(image, thumb);
			}
			else if(!thumb.exists())
			{
				writeThumbFile(image, thumb);
			}

			return thumbnail;
		}
	}

	private static void writeThumbFile(BufferedImage image, File file) throws ThumbnailException
	{
		if(image == null)
			throw new IllegalArgumentException("Thumbnail immage passed in is null");

		ImageOutputStream thumbOutputStream = null;
		try
		{
			backupFile(file);
		}
		catch(IOException e)
		{
			throw new ThumbnailException("Unable to backup file: " + file.getPath(), e);
		}

		try
		{

			thumbOutputStream = ImageIO.createImageOutputStream(file);

			ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
			writer.setOutput(thumbOutputStream);

			JPEGImageWriteParam imageWriteParam = new JPEGImageWriteParam(Locale.US);
			imageWriteParam.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
			imageWriteParam.setCompressionQuality(ImageServlet.getThumbnailQuality());
			imageWriteParam.setOptimizeHuffmanTables(true);

			IIOImage iioImage = new IIOImage(image, null, null);
			writer.write(null, iioImage, imageWriteParam);
		}
		catch(IOException e)
		{
			throw new ThumbnailException(file.getPath(), e);
		}
		finally
		{
			try
			{
				thumbOutputStream.close();
			}
			catch(IOException e)
			{
			}
		}
	}

	private static void backupFile(File file) throws IOException
	{
		if(file.getName().startsWith(ImageFile.THUMB_PREFIX))
			return;

		String dir = file.getParent();
		if(dir == null)
		{
			System.err.println("This should never happen.. parent of file is null?!");
			return;
		}

		File backup = new File(dir + "/backup/" + file.getName());
		if(!backup.exists())
		{
			if(!backup.getParentFile().exists())
				backup.getParentFile().mkdir();

			CopyFile.copyFile(file, backup);
		}
	}

	public String getPopupComments()
	{
		try
		{
			List<Comment> comments = getComment();
			if(comments == null)
				return null;

			StringBuffer buffer = new StringBuffer();
			for(Comment comment : comments)
				buffer.append(" ").append(HTMLEncode.encode(comment.getText())).append(" \n");

			return buffer.toString().substring(0, buffer.length() - 1);
		}
		catch(CommentException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
