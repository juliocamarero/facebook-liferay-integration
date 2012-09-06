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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

UnicodeProperties layoutTypeSettings = null;

if (selLayout != null) {
	layoutTypeSettings = selLayout.getTypeSettingsProperties();
}

String ogTitle = layoutTypeSettings.getProperty("og-title");
String ogType = layoutTypeSettings.getProperty("og-type");
String ogURL = layoutTypeSettings.getProperty("og-URL");
String ogImage = layoutTypeSettings.getProperty("og-image");

String ogDescription = layoutTypeSettings.getProperty("og-description");
String ogVideo = layoutTypeSettings.getProperty("og-video");
String ogAudio = layoutTypeSettings.getProperty("og-audio");
String ogDeterminer = layoutTypeSettings.getProperty("og-determiner");
%>

<liferay-ui:error-marker key="errorSection" value="open-graph" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="required-metadata" /></h3>

<aui:fieldset>
	<aui:input helpMessage="og-title-help" label="title" name="TypeSettingsProperties--og-title--" type="text" value="<%= ogTitle %>" />
	<aui:input helpMessage="og-type-help" label="type" name="TypeSettingsProperties--og-type--" type="text" value="<%= ogType %>" />
	<aui:input helpMessage="og-image-help" label="image-url" name="TypeSettingsProperties--og-image--" type="text" value="<%= ogImage %>" />
</aui:fieldset>

<h3><liferay-ui:message key="optional-metadata" /></h3>

<aui:fieldset>
	<aui:input helpMessage="og-description-help" label="description" name="TypeSettingsProperties--og-description--" type="text" value="<%= ogDescription %>" />
	<aui:input label="video" name="TypeSettingsProperties--og-video--" type="text" value="<%= ogVideo %>" />
	<aui:input label="audio" name="TypeSettingsProperties--og-audio--" type="text" value="<%= ogAudio %>" />

	<aui:select label="determiner" name="TypeSettingsProperties--og-determiner--">
		<aui:option label="" value="auto" />
		<aui:option selected='<%= Validator.equals(ogDeterminer, "a") %>' label="a" />
		<aui:option selected='<%= Validator.equals(ogDeterminer, "an") %>' label="an" />
		<aui:option selected='<%= Validator.equals(ogDeterminer, "the") %>' label="the" />
	</aui:select>
</aui:fieldset>