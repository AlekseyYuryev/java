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

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:root version="1.2" 
	xmlns:f="http://java.sun.com/jsf/core" 
	xmlns:h="http://java.sun.com/jsf/html" 
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
	http://java.sun.com/JSP/Page http://java.sun.com/dtd/jspxml.xsd">
	
	<jsp:directive.page contentType="text/html;charset=UTF-8"/>
	<jsp:text><![CDATA[
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	]]></jsp:text>
	
	<f:view>
		<html>
			<head>
				<title>Celsius converter</title>
			</head>
			<body>
				<g></g>
				<h:form>
					<h:inputText value="25"/>
					<h:outputText value="Celsius"/><br/>
					<h:outputText value="77"/>
					<h:outputText value="Fahrenheit"/><br/>
					<h:commandButton value="Convert"/>
				</h:form>
			</body>
		</html>
	</f:view>
</jsp:root>