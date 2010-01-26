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

<!--
	<%@ page import="org.apache.taglibs.cache.CacheUtil" %>

	<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="req" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://www.safris.com/taglibs/depiction" prefix="depiction" %>
-->

<html
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:req="http://jakarta.apache.org/taglibs/request-1.0"
	xmlns:string="http://jakarta.apache.org/taglibs/string-1.1"
	xmlns:depiction="http://web.safris.org/depiction/tags.xsd"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.w3.org/1999/xhtml ../main/resources/xsd/xhtml1-tags.xsd
	http://web.safris.org/depiction/tags.xsd ../main/resources/xsd/tags.xsd
	http://java.sun.com/jsp/jstl/core ../main/resources/xsd/c.xsd
	http://jakarta.apache.org/taglibs/request-1.0 ../main/resources/xsd/request.xsd
	http://jakarta.apache.org/taglibs/string-1.1 ../main/resources/xsd/string.xsd">
<head>
	<link href="common.css" type="text/css" rel="stylesheet"/>
	<title>Bigbore 907 Pictures</title>
</head>
<body>
<table>
	<tr align="center">
		<td align="center">
			<br/>
			<img alt="Bigbore" src="http://www.safris.com/images/bigbore.jpg"/>
			<br/>
			<p>This Suzuki GSXR 907 Superbike is California street legal w/ dual
			registration (for off-highway and on-highway use). This is Big Bore.
			She originally belonged to the Snow Racing motorcycle racing team.
			Here is a list of some of the interesting parts that distinguish this
			motorcycle as a superbike rather than just a sports bike...<br/><br/>
			Brand New 143 rear wheel Hp Big Bore 907cc kit (monster torque); Ported,
			polished and flowed by Yoshimura; Yosh Ti Fairing bracket; Yosh carbon
			fiber clip ons; Yosh magnesium push pull throttle; Yosh Ti bolt kit;
			Yosh rear sets; 39mm kehin flatt side carbs built by Yosh; Yosh slotted
			cam wheels (need to be installed); Yosh duplex race exhuast; Yosh pipe
			bracket; Steel braided brake lines (front and rear); 6 piston pot front
			calipers; EBC prolight rotors; Blue anodized front forks built by race
			tech (valved and sprung for 185lb rider); Fox rear race shock (fully
			adjustable ride hieght); Fox rear shock linkage; Vortex sprokets; New
			Regina Gold Race Chain</p>
			<div>
				<p style="font: Tahoma">
					<a href="http://www.safris.com/"><span style="color: #999999"><span class="blacktext">Home</span></span></a>
					<span class="blacktext"><span style="color: #999999">&gt; Bigbore 907 Pictures</span></span>
					<br/>
				</p>
			</div>

			<!-- <% CacheUtil.setCacheLifetime(86400, PageContext.APPLICATION_SCOPE, pageContext); %> -->

			<table><tr><td align="center">
			<hr/>
			<string:substring var="pg"><req:parameter name="pg"/></string:substring>
			<depiction:find path="/var/ftp/pub/pictures/motorcycles/bigbore" name=".*[jJ][pP][gG]" var="files">
				<depiction:strictInt value="${pg}" min="0" max="${files.size}" var="pg">
					<depiction:table columns="4" rows="3" tableClass="thumbnail" rowClass="thumbnail" columnClass="thumbnail" var="cell">
						<depiction:cache scope="application" name="page" key="${files.list[cell + pg].path}${files.list[cell + pg].lastModified}">
							<depiction:metadata file="${files.list[cell + pg]}" var="metadata">
								<string:stripStart delimiter="/var/ftp/pub/pictures" var="link">${files.list[cell + pg]}</string:stripStart>
								<a href="image.jsp?i=/${link}">
									<span class="popup" title="${metadata.popupComments}">
										<depiction:thumbnail imgClass="thumbnail" size="120" metadata="${metadata}" baseDir="/var/ftp/pub/pictures" baseUrl="www.safris.com/pictures"/>
									</span>
								</a>
							</depiction:metadata>
						</depiction:cache>
					</depiction:table>
				</depiction:strictInt>
			</depiction:find>
			<hr/>
			<table class="headlinked" cellspacing="1" cellpadding="0" border="0">
				<tr align="center">
					<td class="side-navigator">
						<c:if test="${0 &lt;= pg - 4 * 3}"><a class="normal" href="?pg=${pg - 4 * 3}">Previous</a></c:if>
					</td>
					<td class="center-navigator">
						<string:chomp delimiter="." var="page">${1 + pg / (4 * 3)}</string:chomp>
						<b>Page ${page}</b>
					</td>
					<td class="side-navigator">
						<c:if test="${pg + 4 * 3 &lt;= files.size}"><a class="normal" href="?pg=${pg + 4 * 3}">Next</a></c:if>
					</td>
				</tr>
			</table>
			</td></tr></table>
		</td>
	</tr>
</table>
<p><br/></p>
</body>
</html>