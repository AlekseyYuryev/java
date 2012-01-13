<!--
  Copyright Safris Software 2006
  
  This code is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
-->

<%@ page import="java.io.File" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.safris.depiction.ImageFile" %>
<%@ page import="com.safris.depiction.ImageServlet" %>
<%@ page import="com.safris.depiction.PageCache" %>

<html>
<head><title>Picture</title></head>

<%!
  int imageSize = 600;
%>

<%
  String referer = request.getHeader("referer");
  if(referer == null)
    referer = "http://www.safris.com/car/mr2/";
%>

<body BGCOLOR="white">
  <center>
    <a href="<%= referer %>">Back to Thumbnails</a>
  </center>
<br>
  <center>
<%
  String stringURLPath = request.getParameter(ImageServlet.IMAGE_PARAMETER);
  String stringThumbnailSize = request.getParameter(ImageServlet.SIZE_PARAMETER);
  String path = null;
  int thumbnailSize = 0;
  if(stringURLPath != null && stringThumbnailSize != null)
  {
    path = URLDecoder.decode(stringURLPath);
    try
    {
      thumbnailSize = Integer.parseInt(stringThumbnailSize);
    }
    catch(NumberFormatException e)
    {
      out.println("anti-hack");
      return;
    }
  }

  PageCache cache = PageCache.getCache(this, path);
  if(cache != null && !cache.expired())
  {
    out.println("<!-- cached -->");
    out.println(cache.getPage());
  }
  else
  {
    synchronized(this)
    {
      cache = PageCache.getCache(this, path);
      if(cache != null && !cache.expired())
      {
        out.println("<!-- cached -->");
        out.println(cache.getPage());
      }
      else
      {
        if(path != null && thumbnailSize != 0)
        {

          StringBuffer buffer = new StringBuffer();
          ImageFile imageFile = new ImageFile(new File(ImageServlet.getPictureDirectory() + "/" + path), thumbnailSize);
          File file = null;
          if(imageFile == null)
            buffer.append("<p>Please check the link that you specified. There is most probably a mistake.</p>");

          if(file != null)
          {
            float size[] = new float[3];
            size[0] = imageFile.getWidth();
            size[1] = imageFile.getHeight();
            size[2] = imageFile.getRatio();
            if(size[0] > size[1] && size[0] > imageSize)
            {
              size[0] = imageSize;
              size[1] = (int)((float)imageSize / size[2]);
            }
            else if(size[1] > size[0] && size[1] > imageSize)
            {
              size[0] = (int)((float)imageSize * size[2]);
              size[1] = imageSize;
            }

            buffer.append("<script language=\"JavaScript\">");
            buffer.append("function resize(img){\n");
            buffer.append("if(img.width > " + imageSize + " || img.height > " + imageSize + "){\n");
            buffer.append("img.width = " + (int)size[0] + ";");
            buffer.append("img.height = " + (int)size[1] + ";}\n");
            buffer.append("else{\n");
            buffer.append("img.width = " + imageFile.getWidth() + ";");
            buffer.append("img.height = " + imageFile.getHeight() + ";}\n}\n");
            buffer.append("</script>");
            buffer.append("<a href=\"javascript:resize(document.i)\">");
            buffer.append("<img name=\"i\" src=\"" + ImageServlet.getPictureURL() + "/" + imageFile.getPath() + "\" border=\"0\" ");
            buffer.append("width=\"" + (int)size[0] + "\" height=\"" + (int)size[1] + "\"");
            buffer.append("></a>");
          }
          else
          {
            buffer.append("<p>There is no reference for file: " + path + "</p>");
          }

          Calendar calendar = Calendar.getInstance();
          calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 72);
          cache = new PageCache(calendar, buffer.toString());
          PageCache.setCache(this, path, cache);
          out.println(buffer.toString());
        }
      }
    }
  }
%>
  </center>
</body>
</html>
