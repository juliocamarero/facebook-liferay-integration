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

	@Override
	public String execute(
			StrutsAction originalStrutsAction, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		addOpenGraphProperties(request);

		return originalStrutsAction.execute(request, response);
	}

	protected void addOpenGraphProperties(HttpServletRequest request)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> opengraphAttributes =
			(Map<String, String>)request.getAttribute(OPENGRAPH_ATTRIBUTE);

		if (opengraphAttributes == null) {
			opengraphAttributes = new HashMap<String, String>();

			request.setAttribute(OPENGRAPH_ATTRIBUTE, opengraphAttributes);
		}

		Map<String, String> twitterAttributes =
			(Map<String, String>)request.getAttribute(TWITTER_ATTRIBUTE);

		if (twitterAttributes == null) {
			twitterAttributes = new HashMap<String, String>();

			request.setAttribute(TWITTER_ATTRIBUTE, twitterAttributes);
		}

		UnicodeProperties layoutTypeSettings = null;
		Layout layout = themeDisplay.getLayout();
		Group group = themeDisplay.getScopeGroup();

		if (layout != null) {
			layoutTypeSettings = layout.getTypeSettingsProperties();
		}

		String ogTitle = layoutTypeSettings.getProperty("og-title");
		String ogType = layoutTypeSettings.getProperty("og-type");
		String ogImage = layoutTypeSettings.getProperty("og-image");
		String ogDescription = layoutTypeSettings.getProperty("og-description");
		String ogVideo = layoutTypeSettings.getProperty("og-video");
		String ogAudio = layoutTypeSettings.getProperty("og-audio");
		String ogDeterminer = layoutTypeSettings.getProperty("og-determiner");

		if (Validator.isNull(ogType)) {
			if (group.isUser()) {
				ogType = "profile";
			}
			else {
				ogType = "website";
			}
		}

		opengraphAttributes.put("type", ogType);

		opengraphAttributes.put("url", PortalUtil.getCanonicalURL(
			PortalUtil.getLayoutFullURL(themeDisplay), themeDisplay, layout));

		if (Validator.isNull(ogTitle)) {
			ogTitle = layout.getTitle(themeDisplay.getLanguageId());
		}

		opengraphAttributes.put("title", ogTitle);
		twitterAttributes.put("title", ogTitle);

		if (Validator.isNull(ogDescription)) {
			ogDescription = layout.getDescription(themeDisplay.getLanguageId());
		}

		if (Validator.isNotNull(ogDescription)) {
			opengraphAttributes.put("description",ogDescription);
			twitterAttributes.put("description", ogDescription);
		}

		if (Validator.isNotNull(ogVideo)) {
			opengraphAttributes.put("video", ogVideo);
		}

		if (Validator.isNotNull(ogAudio)) {
			opengraphAttributes.put("audio", ogAudio);
		}

		if (Validator.isNotNull(ogDeterminer)) {
			opengraphAttributes.put("determiner", ogDeterminer);
		}

		opengraphAttributes.put("site_name", group.getDescriptiveName());

		opengraphAttributes.put("locale", themeDisplay.getLanguageId());

		if (Validator.isNull(ogImage) && layout.isIconImage()) {
			ogImage = themeDisplay.getPathImage() + "/layout_icon?img_id=" +
				layout.getIconImageId() + "&t=" +
				WebServerServletTokenUtil.getToken(layout.getIconImageId());
		}

		if (Validator.isNotNull(ogImage)) {
			opengraphAttributes.put("image", ogImage);
			twitterAttributes.put("image", ogImage);
		}
		
		String twCard = layoutTypeSettings.getProperty("twitter-card");
		String twSite = layoutTypeSettings.getProperty("twitter-site");
		String twCreator = layoutTypeSettings.getProperty("twitter-creator");
		
		if (Validator.isNull(twCard)) {
			if (Validator.isNotNull(ogImage)) {
				twCard = "photo";
			}
			else {
				twCard = "summary";
			}
		}

		twitterAttributes.put("card", twCard);


		if (Validator.isNotNull(twSite)) {
			twitterAttributes.put("site", twSite);
		}

		if (Validator.isNotNull(twCreator)) {
			twitterAttributes.put("creator", twCreator);
		}
		
	}

	public final static String OPENGRAPH_ATTRIBUTE = "LIFERAY_SHARED_OPENGRAPH";
	public final static String TWITTER_ATTRIBUTE = "LIFERAY_SHARED_TWITTER";


}