<!--
	Copyright 2010 Safris Technologies Inc.

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

<%@ page import="org.apache.taglibs.cache.CacheUtil" %>

<%@ taglib uri="http://jakarta.apache.org/taglibs/cache-1.0" prefix="cache" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="req" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datagrid-1.0" prefix="ui" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string-1.1" prefix="str" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.safris.com/taglibs/depiction" prefix="depiction" %>

<html>
<head>
	<link href="common.css" type="text/css" rel="stylesheet">
	<title>David Pictures</title>
</head>

<body bgcolor="white">
<table align="center">
	<tr>
		<td align="center">
			<br>
			<p>This is my half-brother david. He's older than this now though.</p>
			<div align="center">
				<font face="Tahoma">
					<a href="http://www.safris.com/"><font color="#999999"><span class="blacktext">Home</span></font></a>
					<span class="blacktext"><font color="#999999">&gt; David Pictures</font></span>&nbsp;
					<br>
				</font>
			</div>

			<% CacheUtil.setCacheLifetime(86400, PageContext.APPLICATION_SCOPE, pageContext); %>

			<table><tr><td align="center">
			<hr width="400">
			<depiction:find path="/var/ftp/pub/pictures/david" name=".*[jJ][pP][gG]" var="files">
				<str:substring var="pg"><req:parameter name="pg"/></str:substring>
				<depiction:strictInt value="${pg}" min="0" max="${files.size}" var="pg">
					<depiction:table columns="4" rows="3" tableClass="thumbnail" rowClass="thumbnail" columnClass="thumbnail" var="cell">
						<cache:cache scope="application" name="page" key="${files.list[cell + pg].path}${files.list[cell + pg].lastModified}">
							<depiction:metadata file="${files.list[cell + pg]}" var="metadata">
								<str:stripStart delimiter="/var/ftp/pub/pictures" var="link">${files.list[cell + pg]}</str:stripStart>
								<a href="image.jsp?i=/${link}">
									<span class="popup" title="${metadata.popupComments}">
										<depiction:thumbnail imgClass="thumbnail" size="120" metadata="${metadata}" baseDir="/var/ftp/pub/pictures" baseUrl="www.safris.com/pictures"/>
									</span>
								</a>
							</depiction:metadata>
						</cache:cache>
					</depiction:table>
					<hr width="400">
					<table class="headlinked" cellspacing="1" cellpadding="0" border="0">
						<tr align="center">
							<td class="side-navigator">
								<c:if test="${0 <= pg - 4 * 3}"><a class="normal" href="?pg=${pg - 4 * 3}">Previous</a></c:if>
							</td>
							<td class="center-navigator">
								<str:chomp delimiter="." var="page">${1 + pg / (4 * 3)}</str:chomp>
								<b>Page ${page}</b>
							</td>
							<td class="side-navigator">
								<c:if test="${pg + 4 * 3 <= files.size}"><a class="normal" href="?pg=${pg + 4 * 3}">Next</a></c:if>
							</td>
						</tr>
					</table>
				</depiction:strictInt>
			</depiction:find>
			</td></tr></table>

		</td>
	</tr>
</table>
<p><br></p>

</body>
</html>
