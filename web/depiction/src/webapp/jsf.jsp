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
