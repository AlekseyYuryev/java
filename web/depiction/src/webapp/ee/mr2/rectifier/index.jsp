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
	<title>Distributor Signal Rectifier</title>
</head>

<%!
	File[] dirs =
	{
		new File("/cars/mr2/engine/assembly/day/31"),
		new File("/cars/mr2/engine/assembly/day/32"),
		new File("/cars/mr2/engine/assembly/day/33"),
	};
	int imagesPerPage = 12;
	int thumbnailSize = 120;
%>

<body class="body" bgcolor="006699" scroll="auto">

<table width="100%" height="100%" cellspacing="0" cellpadding="0">
	<tr class="logo" height="10">
		<td colspan="2"><img class="logo" src="http://www.safris.com/images/logo.gif"></td>
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
					<td width="100%" bgcolor="FFFFFF">
<br>
<table align="center">
	<tr>
		<td align="center">
			<p></p>
			<form method="GET">
			<input type="hidden" name="i">
			<span>Distributor Signal Rectifier</span><br>
			<a class="normal" href="http://www.safris.com/ee/">Electrical Engineering</a>
<div align="left">
<h4>This section is devoted to just one of the hundreds of subprojects that are involved with my MR2. The pictures below depict an interesting problem with an elegant solution.</h4>

<h4><u>The problem</u>: Without going deeply into core mechanics of the engine I am building, I will outline the problem as cleanly as possible... The TEC3 uses a crank position wheel with 60 - 2 teeth that rotates past a magnetic sensor. The wheel's teeth induce a current in a magnetic pickup to let the TEC3 know exactly where the engine is in the rotation. Thus, the TEC3 knows (with 360 / 60 = 6 degrees of precision) how far the engine has rotated from 0 to 360 degrees in one full crank rotation of the engine. One crank rotation, however, is not enough! There exist 4 strokes that overlap 2 crank rotations. Basically, the intake and combustion, and exhaust and compression strokes occur in the same respective degree of crank rotation. The TEC3 doesnt know, therefore, if the engine is compressing or exhausting. Generally speaking, a set of all 4 strokes requires 2 full crank rotations, and the TEC3 doesnt know whether the current rotation is the 1st or 2nd of the 2 crank rotations necessary for the 4 strokes.</h4>

<h4><u>Most popular solution</u>: The most popular solution to this problem is to attach a cam position wheel to the cam and detect its rotation with a Hall Effect sensor. The cam rotates at 1/2 the speed of the crank, and therefore has a unique position for the total 4 strokes. The cams, really, are the ones that define where the strokes are (by opening and closing the intake and exhaust valves), right?! This solution requires 1) fabrication of the wheel, 2) fabrication of the mount point for the wheel onto the cam, 3) purchase of a hall effect sensor, and 4) fabrication of the mount point for the sensor to the head.</h4>

<h4>As I sat and pondered this problem late at night, I fixed my eyes upon the stock distributor. It seemed to me that the distributor's job can be slighly altered considering that there is no need for it to do its original job any longer.</h4>

<h4><u>The solution</u>: Instead of solving this problem with a dedicated Hall Effect sensor, I began research on the electrical properties of the stock MR2 distributor. After an extensive search for documentation, I realized that the only way to know for sure was to test it for myself. I set up the distributor to be spun by a drill-press, and observed its wave patterns with an oscilloscope.</h4>

<h4>I found that the stock distributor provides three distinct signals with the use of three seperate magnetic pickups. The NE signal is used by the stock ECU as the highest resolution signal of 26 pins. The G2 signal is used to let the ECU know when the engine enters each stroke. And finally, the G1 signal is used to let the ECU know when the engine is entering cylinder #1 compression stroke.</h4>

<h4>The properties of the signal were not good. The TEC3 would look for a Hall Effect ~8V square wave signal, but the G1 signal coming from the distributor was not a square wave. The signal was a saw-tooth wave with an amplitude of 200mV. <b>I needed to convert the output of the distributor</b>.</h4>

<h4>I then started searching the National Semiconductors catalog. In the catalog, I spotted an integrated circuit that seemed promising. Here is a link to the specs of the chip that I used:</h4>
<h3 align="center"><a class="normal" href="http://www.national.com/ds/LM/LM1815.pdf">http://www.national.com/ds/LM/LM1815.pdf</a></h3>
<h4>With some communication with Electromotive Inc. regarding the spec of the EFI computer, I gathered enough information to model the correct signal necessary. By varying the time constant of the trigger capacitor and resistors, I was able to engineer the exact signal that was needed.</h4>

<h4>After rigorous testing from expected engine speeds of 500 to 8000 rpm, I put the circuit away to wait the completion of the other components I was working on.</h4>

<h4><b>Much to my delightful, and amazingly incredible surprise, with the 1st turn of the key the engine started! The fact that it started on the 1st turn of the key says something beyond that of just this module... check out the <a href="http://www.safris.com/car/mr2/">engine assembly files</a> to see how much work went into this engine.</b></h4>
</div>
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
						buffer.append("<td><center><a href=\"../../../image.jsp?i=" + URLEncoder.encode(ImageFile.getLocalFile(files[i]).getPath()) + "\">");
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
<p><br></p>

	</tr>
</table>
	</tr>
</table>
</body>
</html>
