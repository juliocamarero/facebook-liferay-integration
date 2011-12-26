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
String urlToLike = preferences.getValue("urlToLike", StringPool.BLANK);
String layoutStyle = preferences.getValue("layoutStyle", StringPool.BLANK);
String verbToDisplay = preferences.getValue("verbToDisplay", StringPool.BLANK);
String colorsScheme = preferences.getValue("colorsScheme", StringPool.BLANK);
String font = preferences.getValue("font", StringPool.BLANK);
boolean showFaces = GetterUtil.getBoolean(preferences.getValue("showFaces", Boolean.TRUE.toString()));
String width = preferences.getValue("width", "300");
String height = preferences.getValue("height", "300");
%>