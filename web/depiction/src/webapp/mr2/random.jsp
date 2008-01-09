<%@ page import="com.safris.depiction.FileFind" %>
<%@ page import="com.safris.depiction.ImageFile" %>
<%@ page import="com.safris.depiction.ImageServlet" %>
<%@ page import="com.safris.depiction.PageCache" %>
<%@ page import="java.io.File" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.List" %>

<html>
<head>
	<link href="../common.css" type="text/css" rel="stylesheet">
	<title>Unsorted MR2 Pictures</title>
</head>

<%!
	File[] dirs =
	{
		new File("/cars/mr2"),
		new File("/cars/mr2/intercooler")
	};
	int imagesPerPage = 12;
	int thumbnailSize = 120;
%>

<body bgcolor="white">
<center>
<table>
	<tr>
		<td align="center">
			<br>
			<img src="http://www.safris.com/images/mr2-title.jpg">
			<br>
			<p>Unsorted pictures of my mr2 project.</p>
			<div align="center">
				<font face="Tahoma">
					<a href="http://www.safris.com/"><font color="999999"><span class="blacktext">Home</span></font></a>
					<span class="blacktext">&gt;</span> <a href="http://www.safris.com/car/mr2/"><font color="999999"><span class="blacktext">MR2</span></font></a>
					<span class="blacktext"><font color="999999">&gt; MR2 Pictures</font></span>&nbsp;
					<br>
				</font>
			</div>
			<form method="GET">
			<input type="hidden" name="i">
			<table cellspacing="10" cellpadding="0" align="center" bordercolor="666666">
				<tr><td colspan="4"><hr></td>
<%
	int pageRequest = 1;
	String parameter = request.getParameter(ImageServlet.PAGE_PARAMETER);
	if(parameter != null)
	{
		try
		{
			pageRequest = Integer.parseInt(parameter);
		}
		catch(NumberFormatException e)
		{
		}
	}

	String requestString = request.getQueryString();
	if(requestString == null || requestString.length() == 0)
		requestString = ImageServlet.PAGE_PARAMETER + "=" + pageRequest;

	String cacheKey = request.getRequestURI() + "?" + requestString;
	PageCache pageCache = PageCache.getCache(cacheKey);
	for(File dir : dirs)
		if(pageCache != null && pageCache.getTimeCached() < new File(ImageServlet.getPictureDirectory() + dir.getPath()).lastModified())
			PageCache.invalidateCache(cacheKey);

	if(!ImageServlet.loadCache(out, cacheKey))
	{
		synchronized(cacheKey)
		{
			if(!ImageServlet.loadCache(out, cacheKey))
			{
				List<File> list = new LinkedList<File>();
				for(File dir : dirs)
					list.addAll(ImageServlet.getImages(dir));

				StringBuffer buffer = new StringBuffer();
				File[] files = FileFind.sort(list.toArray(new File[list.size()]));
				if(files != null)
				{
					String name = null;
					for(int i = (pageRequest - 1) * imagesPerPage; i < Math.min(files.length, pageRequest * imagesPerPage); i++)
					{
						if(i % 4 == 0)
							buffer.append("</tr><tr>");
						name = files[i].getName();
						name.lastIndexOf(".");
//						name = name.substring(0, name.lastIndexOf("."));
						ImageFile imageFile = new ImageFile(files[i], thumbnailSize);
						imageFile.getThumbnail();
						buffer.append("<td><center><a href=\"../image.jsp?i=" + URLEncoder.encode(ImageFile.getLocalFile(files[i]).getPath()) + "\">");
						buffer.append("<img class=\"image-cell\" src=\"" + ImageServlet.getPictureURL() + ImageFile.getLocalFile(ImageFile.getThumbnailFile(files[i])).getPath() + "\" border=\"1\"></a></center></td>");

//						buffer.append("<br>" + imageFile.getWidth() + "x" + imageFile.getHeight() + "<br>" + name + "</a></td>");
					}

					buffer.append("</tr></table><hr>");
					buffer.append("<table class=\"headlinked\" cellspacing=\"1\" cellpadding=\"0\" border=\"0\">");
					buffer.append("<tr align=\"center\"><td class=\"side-navigator\">");
					if(pageRequest > 1)
						buffer.append("<a class=\"normal\" href=\"" + request.getRequestURI() + "?" + ImageServlet.PAGE_PARAMETER + "=" + (pageRequest - 1) + "\">Previous</a>");

					buffer.append("</td><td class=\"center-navigator\"><b>Page " + pageRequest + "</b></td>");
					buffer.append("<td class=\"side-navigator\">");
					if(pageRequest * imagesPerPage < files.length)
						buffer.append("<a class=\"normal\" href=\"" + request.getRequestURI() + "?" + ImageServlet.PAGE_PARAMETER + "=" + (pageRequest + 1) + "\">Next</a>");

					buffer.append("</td></tr></table>");
				}

				ImageServlet.createCache(cacheKey, buffer.toString());
				out.println(buffer.toString());
			}
		}
	}
%>
	</tr>
</table>
</form>
		</td>
	</tr>
</table>
</center>
<p><br></p>

</body>
</html>
