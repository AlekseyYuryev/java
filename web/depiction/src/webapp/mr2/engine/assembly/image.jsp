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

<%@ page import="com.safris.depiction.Comment" %>
<%@ page import="com.safris.depiction.ImageFile" %>
<%@ page import="com.safris.depiction.ImageServlet" %>
<%@ page import="java.io.File" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.List" %>

<html>
<head><title>Picture</title></head>

<%!
	int imageSize = 600;
	String referer = "http://www.safris.com/";
%>

<%
	String stringURLPath = request.getParameter(ImageServlet.IMAGE_PARAMETER);
	String refererURL = request.getParameter(ImageServlet.REFERER_PARAMETER);
	if(refererURL != null && refererURL.length() != 0)
	{
		referer = refererURL;
	}
	else
	{
		String realReferer = request.getHeader("referer");
		if(realReferer != null && realReferer.length() != 0)
			referer = realReferer;
	}
%>

<body bgcolor="white">
	<center>
		<a href="<%= referer %>">Back to Thumbnails</a>
	</center>
<br>
	<center>
<%
	if(stringURLPath == null || stringURLPath.length() == 0)
	{
		out.println("There is an error in your URL parameter.<br>Please go back and try again.");
	}
	else
	{
		String path = URLDecoder.decode(stringURLPath);
		if(!ImageServlet.loadCache(out, path))
		{
			synchronized(path)
			{
				if(!ImageServlet.loadCache(out, path))
				{
					StringBuffer buffer = new StringBuffer();
					ImageFile imageFile = new ImageFile(new File(ImageServlet.getPictureDirectory() + "/" + path));
					if(imageFile == null)
						buffer.append("<p>Please check the link that you specified. There is most probably a mistake.</p>");

					float[] size = new float[3];
					size[0] = imageFile.getWidth();
					size[1] = imageFile.getHeight();
					size[2] = imageFile.getRatio();
					if(size[0] > size[1] && size[0] > imageSize)
					{
						size[0] = imageSize;
						size[1] = (int)((float)imageSize * size[2]);
					}
					else if(size[1] > size[0] && size[1] > imageSize)
					{
						size[0] = (int)((float)imageSize / size[2]);
						size[1] = imageSize;
					}

					if(size[0] != imageFile.getWidth())
					{
						buffer.append("<script language=\"JavaScript\">\n");
						buffer.append("function resize(img) {\n");
						buffer.append("\tif(img.width > " + imageSize + " || img.height > " + imageSize + ") {\n");
						buffer.append("\t\timg.width = " + (int)size[0] + ";\n");
						buffer.append("\t\timg.height = " + (int)size[1] + ";\n\t}\n");
						buffer.append("\telse {\n");
						buffer.append("\t\timg.width = " + imageFile.getWidth() + ";\n");
						buffer.append("\t\timg.height = " + imageFile.getHeight() + ";\n\t}\n}\n");
						buffer.append("</script>\n");
						buffer.append("<a href=\"javascript:resize(document.i)\">");
					}

					buffer.append("<img name=\"i\" src=\"" + ImageServlet.getPictureURL() + path + "\" border=\"0\" ");
					buffer.append("width=\"" + (int)size[0] + "\" height=\"" + (int)size[1] + "\"");

					if(size[0] != imageFile.getWidth())
						buffer.append("></a>");
					else
						buffer.append(">");

					buffer.append("<br><br><table bgcolor='#DDDDDD' width='560'><tr bgcolor='#BBBBBB'><td nowrap width='20'>Date</td><td nowrap width='20'>Submitter</td><td width='100%'>Comment</td></tr>");
					List<Comment> comments = imageFile.getComments();
					if(comments == null || comments.size() == 0)
						buffer.append("<tr><td colspan='3'>No comments yet. Be the first!</td></tr>");
					else
					{
						String color1 = "#DDDDDD";
						String color2 = "#FFFFFF";
						String color = color1;
						comments = Comment.sortByDate(comments);
						for(Comment comment : comments)
						{
							buffer.append("<tr bgcolor='" + color + "'><td nowrap valign='top'>" + ImageServlet.getDateFormat().format(comment.getDate()) + "</td><td nowrap valign='top'>" + comment.getSubmitter() + "</td><td valign='top'>" + comment.getText() + "</td></tr>");
							if(color == color1)
								color = color2;
							else
								color = color1;
						}
					}

					buffer.append("<tr><td colspan='3'><form action='" + request.getContextPath() + "/ImageServlet' method='post'><input type='hidden' name='" + ImageServlet.IMAGE_PARAMETER + "' value='" + stringURLPath + "'><input type='hidden' name='" + ImageServlet.REFERER_PARAMETER + "' value='" + referer + "'><font size='-1'>your name:</font><br><input name='" + ImageServlet.SUBMITTER_PARAMETER + "' type='textbox'><br><font size='-1'>your comment:</font><br><textarea name='" + ImageServlet.COMMENT_PARAMETER + "' cols='76' rows='6'></textarea><br><input type='submit' value='add comment'></form></td></tr>");
					buffer.append("</table>");

					ImageServlet.createCache(path, buffer.toString());
					out.println(buffer.toString());
				}
			}
		}
	}
%>
		<br>
		<a href="<%= referer %>">Back to Thumbnails</a>
	</center>
</body>
</html>
