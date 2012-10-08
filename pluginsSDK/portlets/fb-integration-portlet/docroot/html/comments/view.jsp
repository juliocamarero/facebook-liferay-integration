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

<script type="text/javascript">
	// Load the SDK Asynchronously
    (function(d){
      var js, id = 'facebook-jssdk'; if (d.getElementById(id)) {return;}
      js = d.createElement('script'); js.id = id; js.async = true;
      js.src = "//connect.facebook.net/<%= locale.getLanguage() %>_<%= locale.getCountry() %>/all.js";
      d.getElementsByTagName('head')[0].appendChild(js);
    }(document));
</script>

<div style="text-align:center">
	<div
		class="fb-comments"
		data-href="<%= href %>"
		data-num-posts="<%= numPosts %>"
		data-width="<%= width %>"
		data-colorscheme="<%= colorsScheme %>">
	</div>
</div>