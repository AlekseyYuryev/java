<!--
	Copyright 2008 Safris Technologies Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

			http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
-->

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
	<link href="http://www.safris.com/depiction/common.css" type="text/css" rel="stylesheet">
	<link href="http://www.safris.com/common.css" type="text/css" rel="stylesheet">
	<title>Blue Angels - San Francisco</title>
</head>

<%!
	File[] dirs =
	{
		new File("/party/10-9-2005")
	};
	int imagesPerPage = 12;
	int thumbnailSize = 120;
%>

<body background="http://www.safris.com/images/bklarge.gif" bgcolor="white">
<center>
<table align="center">
	<tr>
		<td align="center">
			<p><br></p>
			<span><font color="333333">Blue Angels - San Francisco</font></span><br>
			<a class="normal" href="http://www.safris.com/goodtimes/">Good Times</a>
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
					buffer.append("<table class='headlinked' cellspacing='1' cellpadding='0' border='0'>");
					buffer.append("<tr align='center'><td class='side-navigator'>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00001.avi\")'>MOV00001</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00002.avi\")'>MOV00002</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00003.avi\")'>MOV00003</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00004.avi\")'>MOV00004</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00005.avi\")'>MOV00005</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00005.avi\")'>MOV00006</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00007.avi\")'>MOV00007</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00008.avi\")'>MOV00008</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00009.avi\")'>MOV00009</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00010.avi\")'>MOV00010</a><br>");
					buffer.append("<a href='javascript:play(\"http://www.safris.com/pictures/party/10-9-2005/MOV00011.avi\")'>MOV00011</a>");
					buffer.append("</td><td><div id='PlayerDiv' class='HiliteOn'><object id='PlayerObject' width='176' height='144' classid='CLSID:22D6f312-B0F6-11D0-94AB-0080C74C7E95' standby='Loading Windows Media Player components...' type='application/x-oleobject' codebase='http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,7,1112'><param name='filename' value=''><param name='showcontrols' value='false'><param name='autostart' value='true'><embed id='PlayerEmbed' showcontrols='0' type='application/x-mplayer2' width='176' height='144' src='' name='MediaPlayer'></embed></object></div></td>");
					buffer.append("</tr></table><br><hr>");
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
		</td>
	</tr>
</table>
</center>
<p><br></p>

</body>
</html>

