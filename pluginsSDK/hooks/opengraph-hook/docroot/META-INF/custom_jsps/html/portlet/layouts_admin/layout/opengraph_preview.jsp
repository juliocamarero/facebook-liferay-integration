<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String ogLocale = layoutTypeSettings.getProperty("og-locale");
String ogSiteName = layoutTypeSettings.getProperty("og-site_name");
String ogVideo = layoutTypeSettings.getProperty("og-video");
String ogAudio = layoutTypeSettings.getProperty("og-audio");
String fbAppId = layoutTypeSettings.getProperty("fb-app_id");
String fbAdmins = layoutTypeSettings.getProperty("fb-admins");
String twCard = layoutTypeSettings.getProperty("twitter-card");
String twTitle = layoutTypeSettings.getProperty("twitter-title");
String twDescription = layoutTypeSettings.getProperty("twitter-description");
String twURL = layoutTypeSettings.getProperty("twitter-url");
String twImage = layoutTypeSettings.getProperty("twitter-image");
String twSite = layoutTypeSettings.getProperty("twitter-site");
String twCreator = layoutTypeSettings.getProperty("twitter-creator");
%>

<liferay-ui:error-marker key="errorSection" value="opengraph-preview" />

<h3><liferay-ui:message key="opengraph-preview" /></h3>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h4><liferay-ui:message key="opengraph-metadata" /></h4>

<aui:fieldset>
	<aui:input helpMessage="og-type-help" label="type" name="TypeSettingsProperties--og-type--" type="text" value="<%= ogType %>" style="min-width:300px;" />
	<aui:input helpMessage="og-title-help" label="title" name="TypeSettingsProperties--og-title--" type="text" value="<%= ogTitle %>" style="min-width:300px;" />
	<aui:input helpMessage="og-image-help" label="image-url" name="TypeSettingsProperties--og-image--" type="text" value="<%= ogImage %>" style="min-width:300px;" />
	<aui:input helpMessage="og-description-help" label="description" name="TypeSettingsProperties--og-description--" type="textarea" value="<%= ogDescription %>" style="min-width:300px;min-height:150px;" />
	<aui:input helpMessage="og-video-help" label="video-url" name="TypeSettingsProperties--og-video--" type="text" value="<%= ogVideo %>" style="min-width:300px;" />
	<aui:input helpMessage="og-audio-help" label="audio-url" name="TypeSettingsProperties--og-audio--" type="text" value="<%= ogAudio %>" style="min-width:300px;" />
</aui:fieldset>

<br />

<h4><liferay-ui:message key="facebook-metadata" /></h4>

<aui:fieldset>
	<aui:input helpMessage="fb-app_id-help" label="fb-app_id" name="TypeSettingsProperties--fb-app_id--" type="text" value="<%= fbAppId %>" style="min-width:300px;" />
	<aui:input helpMessage="fb-admins-help" label="fb-admins" name="TypeSettingsProperties--fb-admins--" type="text" value="<%= fbAdmins %>" style="min-width:300px;" />
</aui:fieldset>

<br />

<h4><liferay-ui:message key="twitter-metadata" /></h4>

<aui:fieldset>
	<aui:select helpMessage="twitter-card-help" label="twitter-card" name="TypeSettingsProperties--twitter-card--" style="min-width:300px;">
		<aui:option label="" value="" />
		<aui:option label="twitter-card-summary" selected='<%= Validator.equals(twCard, "summary") %>' value="summary" />
		<aui:option label="twitter-card-summary_large_image" selected='<%= Validator.equals(twCard, "summary_large_image") %>' value="summary_large_image" />
		<aui:option label="twitter-card-photo" selected='<%= Validator.equals(twCard, "photo") %>' value="photo" />
		<aui:option label="twitter-card-gallery" selected='<%= Validator.equals(twCard, "gallery") %>' value="gallery" />
		<aui:option label="twitter-card-product" selected='<%= Validator.equals(twCard, "product") %>' value="product" />
		<aui:option label="twitter-card-app" selected='<%= Validator.equals(twCard, "app") %>' value="app" />
		<aui:option label="twitter-card-player" selected='<%= Validator.equals(twCard, "player") %>' value="player" />
	</aui:select>
	<aui:input helpMessage="twitter-title-help" label="twitter-title" name="TypeSettingsProperties--twitter-title--" type="text" value="<%= twTitle %>" style="min-width:300px;" />
	<aui:input helpMessage="twitter-description-help" label="twitter-description" name="TypeSettingsProperties--twitter-description--" type="textarea" value="<%= twDescription %>" style="min-width:300px;min-height:150px;" />
	<aui:input helpMessage="twitter-image-help" label="twitter-image" name="TypeSettingsProperties--twitter-image--" type="text" value="<%= twImage %>" style="min-width:300px;" />
	<aui:input helpMessage="twitter-site-help" label="twitter-site" name="TypeSettingsProperties--twitter-site--" type="text" value="<%= twSite %>" style="min-width:300px;" />
	<aui:input helpMessage="twitter-creator-help" label="twitter-creator" name="TypeSettingsProperties--twitter-creator--" type="text" value="<%= twCreator %>" style="min-width:300px;" />
</aui:fieldset>