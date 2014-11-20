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
package com.fb.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.webserver.WebServerServletTokenUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public class OpenGraphLayoutAction extends BaseStrutsAction {

	public final static String OPENGRAPH_ATTRIBUTE = "LIFERAY_SHARED_OPENGRAPH";
	public final static String FACEBOOK_ATTRIBUTE = "LIFERAY_SHARED_FACEBOOK";
	public final static String TWITTER_ATTRIBUTE = "LIFERAY_SHARED_TWITTER";
	
	@Override
	public String execute(StrutsAction originalStrutsAction, HttpServletRequest request, HttpServletResponse response) throws Exception {
		addOpenGraphProperties(request);
		return originalStrutsAction.execute(request, response);
	}

	protected void addOpenGraphProperties(HttpServletRequest request) throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		Map<String, String> ogAttrs = (Map<String, String>)request.getAttribute(OPENGRAPH_ATTRIBUTE);
		if (ogAttrs == null) {
			ogAttrs = new HashMap<String, String>();
			request.setAttribute(OPENGRAPH_ATTRIBUTE, ogAttrs);
		}
		Map<String, String> fbAttrs = (Map<String, String>)request.getAttribute(FACEBOOK_ATTRIBUTE);
		if (fbAttrs == null) {
			fbAttrs = new HashMap<String, String>();
			request.setAttribute(FACEBOOK_ATTRIBUTE, fbAttrs);
		}
		Map<String, String> twAttrs = (Map<String, String>)request.getAttribute(TWITTER_ATTRIBUTE);
		if (twAttrs == null) {
			twAttrs = new HashMap<String, String>();
			request.setAttribute(TWITTER_ATTRIBUTE, twAttrs);
		}

		UnicodeProperties layoutTypeSettings = null;
		Layout layout = themeDisplay.getLayout();
		Group group = themeDisplay.getScopeGroup();

		if (layout != null) {layoutTypeSettings = layout.getTypeSettingsProperties();}

		String ogType = layoutTypeSettings.getProperty("og-type");
		String ogTitle = layoutTypeSettings.getProperty("og-title");
		String ogImage = layoutTypeSettings.getProperty("og-image");
		String ogDescription = layoutTypeSettings.getProperty("og-description");
		String ogVideo = layoutTypeSettings.getProperty("og-video");
		String ogAudio = layoutTypeSettings.getProperty("og-audio");

		if (Validator.isNull(ogType)) {
			if (group.isUser()) {ogType = "profile";}
			else {ogType = "website";}
		}
		ogAttrs.put("type", ogType);
		if (Validator.isNull(ogTitle)) {ogTitle = layout.getTitle(themeDisplay.getLanguageId());}
		ogAttrs.put("title", ogTitle);
		ogAttrs.put("url", PortalUtil.getCanonicalURL(PortalUtil.getLayoutFullURL(themeDisplay), themeDisplay, layout));
		if (Validator.isNull(ogDescription)) {ogDescription = layout.getDescription(themeDisplay.getLanguageId());}
		if (Validator.isNotNull(ogDescription)) {ogAttrs.put("description", ogDescription);}
		ogAttrs.put("site_name", group.getDescriptiveName());
		ogAttrs.put("locale", themeDisplay.getLanguageId());
		if (Validator.isNotNull(ogVideo)) {ogAttrs.put("video", ogVideo);}
		if (Validator.isNotNull(ogAudio)) {ogAttrs.put("audio", ogAudio);}
		if ((Validator.isNull(ogImage))&&(layout.isIconImage())) {
			ogImage = themeDisplay.getPathImage() + "/layout_icon?img_id=" + layout.getIconImageId() + "&t=" +
				WebServerServletTokenUtil.getToken(layout.getIconImageId());
		}
		if (Validator.isNotNull(ogImage)) {ogAttrs.put("image", ogImage);}
		
		String fbAppId = layoutTypeSettings.getProperty("fb-app_id");
		String fbAdmins = layoutTypeSettings.getProperty("fb-admins");

		if (Validator.isNotNull(fbAppId)) {fbAttrs.put("app_id", fbAppId);}
		if (Validator.isNotNull(fbAdmins)) {fbAttrs.put("admins", fbAdmins);}
		
		String twCard = layoutTypeSettings.getProperty("twitter-card");
		String twTitle = layoutTypeSettings.getProperty("twitter-title");
		String twDescription = layoutTypeSettings.getProperty("twitter-description");
		String twImage = layoutTypeSettings.getProperty("twitter-image");
		String twSite = layoutTypeSettings.getProperty("twitter-site");
		String twCreator = layoutTypeSettings.getProperty("twitter-creator");
		
		if (Validator.isNull(twCard)) {twCard = "summary";}
		twAttrs.put("card", twCard);
		if (Validator.isNull(twTitle)) {twTitle = ogTitle;}
		if (Validator.isNotNull(twTitle)) {twAttrs.put("title", twTitle);}
		twAttrs.put("url", PortalUtil.getCanonicalURL(PortalUtil.getLayoutFullURL(themeDisplay), themeDisplay, layout));
		if (Validator.isNull(twDescription)) {twDescription = ogDescription;}
		if (Validator.isNotNull(twDescription)) {twAttrs.put("description", twDescription);}
		if (Validator.isNull(twImage)) {twImage = ogImage;}
		if (Validator.isNotNull(twImage)) {twAttrs.put("image", twImage);}
		if (Validator.isNotNull(twSite)) {twAttrs.put("site", twSite);}
		if (Validator.isNotNull(twCreator)) {twAttrs.put("creator", twCreator);}
		
	}

}