<%@ page import="com.liferay.portal.kernel.facebook.FacebookConnectUtil" %>

<%@ include file="/html/common/init.jsp" %>

<meta content="<%= FacebookConnectUtil.getAppId(company.getCompanyId()) %>" property="fb:app_id" />

<%
Map<String, String> opengraphAttributes = (Map<String, String>)request.getAttribute("LIFERAY_SHARED_OPENGRAPH");

if (opengraphAttributes != null) {
	for (String key : opengraphAttributes.keySet()) {
%>

		<meta content="<%= HtmlUtil.escape(opengraphAttributes.get(key)) %>" property="og:<%= key %>" />

<%
	}
}
	
Map<String, String> twitterAttributes = (Map<String, String>)request.getAttribute("LIFERAY_SHARED_TWITTER");

if (twitterAttributes != null) {
	for (String key : twitterAttributes.keySet()) {
%>

<meta content="<%= HtmlUtil.escape(twitterAttributes.get(key)) %>" name="twitter:<%= key %>" />

<%
	}
}
%>