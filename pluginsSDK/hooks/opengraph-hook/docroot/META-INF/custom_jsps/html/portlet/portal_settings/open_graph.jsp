<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
String APP_NAMESPACES = "APP_NAMESPACES";

String appNamespace = PrefsPropsUtil.getString(company.getCompanyId(), APP_NAMESPACES);
%>

<liferay-ui:error-marker key="errorSection" value="open-graph" />

<h3><liferay-ui:message key="open-graph-configuration" /></h3>

<div class="portlet-msg-info">
	<liferay-ui:message key="open-graph-help" />
</div>

<aui:fieldset>
	<aui:input cssClass="lfr-textarea-container" helpMessage="app-namespaces-help" label="app-namespaces" name='<%= "settings--" + APP_NAMESPACES + "--" %>' type="textarea" value="<%= appNamespace %>" />
</aui:fieldset>