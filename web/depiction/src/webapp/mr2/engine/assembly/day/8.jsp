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
  <title>Engine Assembly Day 8</title>
</head>

<%!
  File[] dirs =
  {
    new File("cars/mr2/intercooler/radiator/day/1")
  };
  int imagesPerPage = 12;
  int thumbnailSize = 120;
%>

<body bgcolor="#006699" class="body" scroll="auto">

<table width="100%" cellspacing="0" cellpadding="0">
  <tr class="logo" height="0">
    <td colspan="2"><br><img class="logo" src="http://www.safris.com/images/logo.gif"></td>
  </tr>
  <tr class="text_body">
    <td>
      <table width="100%" cellspacing="0" cellpadding="0">
        <tr class="border_line">
          <td class="border_line" colspan="6"></td>
        </tr>
        <tr class="top_line1">
          <td class="top_line1" colspan="6"></td>
        </tr>
        <tr class="top_line2">
          <td class="top_line2" colspan="6"></td>
        </tr>
        <tr>
          <td width="100%" bgcolor="#FFFFFF">
<br>
<table align="center">
  <tr>
    <td align="center">
      <p></p>
      <span>Engine Assembly Day 8</span><br>
      <a class="normal" href="http://www.safris.com/car/mr2/">Toyota MR2 Pictures</a>
      <br>
      <table>
        <tr>
          <td align="left" width="600">
<p>Oh I hate this thing so much. This is Trueleo's W2A intercooler setup, and it is just a pain in the ass. I was told that this radiator fits perfectly into the front of the condenser in the MR2. However, it DOESN'T! It is WAY to tight and required serious hacking. I needed to remove thick sections in certain places, and then I had to seal them again.<br>
<br>
Here you see me sealing the sections I hacked to make this radiator fit into the front of the car.<br>
<br>
Also, the radiator came with its fittings attached with silicone goop all over. First of all, the silicone did not hold worth shit! Second of all, even with a complete reconstruction of the fittings with JB Weld, even that wont hold. I still need to remove the radiator from my car and weld it like it is supposed to be.</p>
          </td>
        </tr>
      </table>
      <table cellspacing="10" cellpadding="0" align="center" bordercolor="#666666">
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
</td>
</tr>
</table>
</td>
</tr>
<tr>
  <td bgcolor="#999999" colspan="6"><br></td>
</tr>
</table>
</tr>
</table>
<p><br></p>

</body>
</html>
