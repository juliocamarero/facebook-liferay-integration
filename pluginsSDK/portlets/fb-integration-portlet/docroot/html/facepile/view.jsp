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
			<script type="text/javascript">
				// Load the SDK Asynchronously
				(function(d){
				  var js, id = 'facebook-jssdk'; if (d.getElementById(id)) {return;}
				  js = d.createElement('script'); js.id = id; js.async = true;
				  js.src = "//connect.facebook.net/<%= locale.getLanguage() %>_<%= locale.getCountry() %>/all.js";
				  d.getElementsByTagName('head')[0].appendChild(js);
				}(document));
			</script>

			<div
				class="fb-facepile"
				data-href="<%= href %>"
				data-size="<%= size %>"
				data-max-rows="<%= maxRows %>"
				data-width="<%= width %>"
				data-colorscheme="<%= colorsScheme %>">
			</div>
		</c:when>
		<c:otherwise>
			<iframe src="http://www.facebook.com/plugins/facepile.php?href=<%= HttpUtil.encodeURL(href) %>&amp;width=<%= width %>&amp;max_rows=<%= maxRows %>" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:200px;" allowTransparency="true"></iframe>
		</c:otherwise>
	</c:choose>
</div>