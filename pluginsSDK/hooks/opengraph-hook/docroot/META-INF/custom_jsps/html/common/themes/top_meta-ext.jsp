<%@ page import="com.liferay.portal.util.FacebookConnectUtil" %>

<%@ include file="/html/common/init.jsp" %>

<meta property="fb:app_id" content="<%= FacebookConnectUtil.getAppId(company.getCompanyId())%>" />

<%
Map<String, String> opengraphAttributes = (Map<String, String>)request.getAttribute("LIFERAY_SHARED_OPENGRAPH");

if (opengraphAttributes != null) {
	for (String key : opengraphAttributes.keySet()) {
%>

		<meta property="og:<%= key %>" content="<%= HtmlUtil.escape(opengraphAttributes.get(key)) %>" />

<%
	}
}
%>