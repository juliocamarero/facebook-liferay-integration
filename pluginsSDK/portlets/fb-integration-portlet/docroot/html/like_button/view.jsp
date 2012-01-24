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

<%@ include file="init.jsp" %>

<div style="text-align:center">
	<c:choose>
		<c:when test="<%= useFbSdk %>">
			<liferay-util:html-top outputKey="fb">
				<script src="http://connect.facebook.net/<%= themeDisplay.getLanguageId()  %>/all.js#xfbml=1" type="text/javascript"></script>
			</liferay-util:html-top>

			<fb:like
				href="<%= urlToLike %>"
				<%= Validator.isNull(font) ? StringPool.BLANK : "font=\"" + font + "\""  %>
				<%= Validator.isNull(layoutStyle) ? StringPool.BLANK : "layout_style=\"" + layoutStyle + "\""  %>
				<%= Validator.isNull(verbToDisplay) ? StringPool.BLANK : "action=\"" + verbToDisplay + "\""  %>
				<%= Validator.isNull(colorsScheme) ? StringPool.BLANK : "colorscheme=\"" + colorsScheme + "\""  %>
				<%= showFaces ? StringPool.BLANK : "show_faces=\"" + showFaces + "\""  %>
				width="<%= width %>" height="<%= height %>" send="true"
			></fb:like>
		</c:when>
		<c:otherwise>
			<iframe src="http://www.facebook.com/plugins/like.php?href=<%= HttpUtil.encodeURL(urlToLike) %>&amp;layout=<%= layoutStyle %>&amp;show_faces=<%= showFaces %>&amp;width=<%= width %>&amp;action=<%= verbToDisplay %>&amp;colorscheme=<%= colorScheme %>&amp;height=<%= height %>"
				scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:450px; height:80px;" allowTransparency="true"></iframe>
		</c:otherwise>
	</c:choose>
</div>



