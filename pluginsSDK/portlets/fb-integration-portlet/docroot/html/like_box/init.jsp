<%
/**
* Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/
%>

<%@ include file="../init.jsp" %>

<%
String href = preferences.getValue("href", StringPool.BLANK);
String connections = preferences.getValue("connections", "10");
String width = preferences.getValue("width", "250");
String height = preferences.getValue("height", "750");
boolean showStream = GetterUtil.getBoolean(preferences.getValue("showStream", Boolean.TRUE.toString()));
boolean showHeader = GetterUtil.getBoolean(preferences.getValue("showHeader", Boolean.TRUE.toString()));
boolean showFaces = GetterUtil.getBoolean(preferences.getValue("showFaces", Boolean.TRUE.toString()));
%>