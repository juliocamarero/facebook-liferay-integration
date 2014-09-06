<%@ page import="com.liferay.portal.util.FacebookConnectUtil" %>

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
%>