<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/html/common/init.jsp" %>
<%
	
	Map<String, String> ogAttrs = (Map<String, String>)request.getAttribute("LIFERAY_SHARED_OPENGRAPH");
	if (ogAttrs != null) {
		for (String key : ogAttrs.keySet()) {
%>
<meta property="og:<%= key %>" content="<%= HtmlUtil.escape(ogAttrs.get(key)) %>" />
<%
		}
	}
	
	Map<String, String> fbAttrs = (Map<String, String>)request.getAttribute("LIFERAY_SHARED_FACEBOOK");
	if (fbAttrs != null) {
		for (String key : fbAttrs.keySet()) {
%>
<meta property="fb:<%= key %>" content="<%= HtmlUtil.escape(fbAttrs.get(key)) %>" />
<%
		}
	}

	Map<String, String> twAttrs = (Map<String, String>)request.getAttribute("LIFERAY_SHARED_TWITTER");
	if (twAttrs != null) {
		for (String key : twAttrs.keySet()) {
%>
<meta name="twitter:<%= key %>" content="<%= HtmlUtil.escape(twAttrs.get(key)) %>" />
<%
		}
	}
	
%>