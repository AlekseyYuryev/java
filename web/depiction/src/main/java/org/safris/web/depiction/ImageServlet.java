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

package org.safris.web.depiction;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import org.safris.web.depiction.Comment;
import org.safris.web.depiction.CommentException;
import org.safris.web.depiction.FileFind;
import org.safris.web.depiction.ImageFile;
import org.safris.web.depiction.ImageServlet;
import org.safris.web.depiction.PageCache;
import org.safris.web.depiction.Redirect;
import org.safris.web.depiction.ThumbnailException;

public class ImageServlet extends HttpServlet
{
	private static final Map<String,byte[]> cache = new Hashtable<String,byte[]>();
	private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	public static final String PAGE_PARAMETER = "p";
	public static final String IMAGE_PARAMETER = "i";
	public static final String SUBMITTER_PARAMETER = "n";
	public static final String COMMENT_PARAMETER = "c";
	public static final String REFERER_PARAMETER = "r";
	private static String pictureDirectory = null;
	private static String pictureURL = null;
	private static float thumbnailQuality = 1;
	private static String jpegrdf = null;

	public static DateFormat getDateFormat()
	{
		return dateFormat;
	}

	public static List<ImageFile> getImageFiles(String directories, int thumbnailSize)
	{
		if(directories == null || directories.length() == 0)
			return null;

		StringTokenizer tokenizer = new StringTokenizer(directories, ":");
		List<File> directoryList = new LinkedList<File>();
		while(tokenizer.hasMoreTokens())
			directoryList.add(new File(tokenizer.nextToken()));

		if(directoryList.size() == 0)
			return null;

		File[] dirs = directoryList.toArray(new File[directoryList.size()]);

		List<File> fileList = new LinkedList<File>();
		for(File dir : dirs)
			fileList.addAll(ImageServlet.getImages(dir));

		File[] files = FileFind.sort(fileList.toArray(new File[fileList.size()]));
		if(files == null)
			return null;

		List<ImageFile> imageFiles = new LinkedList<ImageFile>();
		for(File dir : files)
		{
			ImageFile imageFile = new ImageFile(dir, thumbnailSize);
			try
			{
				imageFile.getThumbnail();
			}
			catch(ThumbnailException e)
			{
				e.printStackTrace();
			}
			imageFiles.add(imageFile);
		}

		return imageFiles;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String referer = request.getHeader("referer");
		String stringURLPath = request.getParameter(IMAGE_PARAMETER);
		String submitterURL = request.getParameter(SUBMITTER_PARAMETER);
		String commentURL = request.getParameter(COMMENT_PARAMETER);
		String refererURL = request.getParameter(REFERER_PARAMETER);
		if(stringURLPath == null || stringURLPath.length() == 0 || submitterURL == null || submitterURL.length() == 0 || commentURL == null || commentURL.length() == 0 || refererURL == null || refererURL.length() == 0)
		{
			response.sendError(406, "Please ");
			return;
		}

		String path = URLDecoder.decode(stringURLPath);
		ImageFile imageFile = new ImageFile(new File(ImageServlet.getPictureDirectory() + "/" + path));
		List<Comment> comments = null;
		try
		{
			comments = imageFile.getComments();
		}
		catch(CommentException e)
		{
			e.printStackTrace();
			response.sendError(406, "Not your error... image comment problem!");
		}

		if(comments != null && comments.size() != 0)
		{
			for(Comment comment : comments)
			{
				if(submitterURL.equals(comment.getSubmitter()) && commentURL.equals(comment.getText()))
				{
					response.sendRedirect(referer);
					return;
				}
			}
		}

		try
		{
			imageFile.addComment(new Comment(new Date(System.currentTimeMillis()), submitterURL, commentURL));
		}
		catch(CommentException e)
		{
			e.printStackTrace();
			response.sendError(406, "Not your error... image comment problem!");
		}
		PageCache.invalidateCache(path);
		Map<String,String> map = new HashMap<String,String>(1);
		map.put(REFERER_PARAMETER, refererURL);
		Redirect.postRedirect(response, referer, map, null);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		doGet(request, response);
	}

	public void init(ServletConfig servletConfig)
	{
		pictureDirectory = servletConfig.getInitParameter("PictureDirectory");
		if(pictureDirectory.endsWith("/") || pictureDirectory.endsWith("\\"))
			pictureDirectory = pictureDirectory.substring(0, pictureDirectory.length() - 1);

		pictureURL = servletConfig.getInitParameter("PictureURL");
		thumbnailQuality = Integer.parseInt(servletConfig.getInitParameter("ThumbnailQuality"));
		thumbnailQuality = Math.max(0, Math.min(thumbnailQuality, 100)) / 100f;
		ServletContext servletContext = servletConfig.getServletContext();
		jpegrdf = servletContext.getRealPath("WEB-INF/jpegrdf.rdf");
	}

	public String getServletInfo()
	{
		return "Image Servlet";
	}

	public static List<File> getImages(File directory)
	{
		List<File> list = null;
		directory = new File(ImageServlet.getPictureDirectory() + directory);
		List<File> files = FileFind.ls(directory);
		if(files == null)
			return null;

		list = new LinkedList<File>();
		for(File file : files)
			if(ImageFile.isImage(file))
				list.add(file);

		return list;
	}

	public static String getPictureDirectory()
	{
		if(pictureDirectory == null)
			throw new IllegalStateException("ImageServlet ERROR");

		return pictureDirectory;
	}

	public static String getPictureURL()
	{
		if(pictureURL == null)
			throw new IllegalStateException("ImageServlet ERROR");

		return pictureURL;
	}

	public static float getThumbnailQuality()
	{
		return thumbnailQuality;
	}

	public static String getJpegrdf()
	{
		return jpegrdf;
	}

	public static void createCache(String path, String string)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 72);
		PageCache pageCache = new PageCache(calendar, string);
		PageCache.setCache(path, pageCache);
	}

	public static boolean loadCache(JspWriter out, String path) throws IOException
	{
		PageCache cache = PageCache.getCache(path);
		if(cache == null || cache.expired())
			return false;

		out.println("<!-- cached -->");
		out.println(cache.getPage());
		return true;
	}
}
