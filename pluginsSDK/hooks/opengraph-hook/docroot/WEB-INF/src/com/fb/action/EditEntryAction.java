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
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Julio Camarero
 */
public class EditEntryAction extends BaseStrutsPortletAction {

	@Override
	public String render(
			StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		String forward = originalStrutsPortletAction.render(
			portletConfig, renderRequest, renderResponse);

		BlogsEntry entry = (BlogsEntry)renderRequest.getAttribute(
			"BLOGS_ENTRY");

		if (entry != null) {
			addOpenGraphProperties(renderRequest, renderResponse, entry);
		}

		return forward;
	}

	protected void addOpenGraphProperties(
			PortletRequest request, PortletResponse response, BlogsEntry entry)
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

		opengraphAttributes.put("type", "website");

		PortletURL portletURL = ((LiferayPortletResponse)response).createRenderURL();

		portletURL.setParameter("struts_action", "/blogs/view_entry");
		portletURL.setParameter("urlTitle", entry.getUrlTitle());

		String url = PortalUtil.getCanonicalURL(
			portletURL.toString(), themeDisplay, themeDisplay.getLayout());

		opengraphAttributes.put("url", url);
		twitterAttributes.put("url", url);

		String title = entry.getTitle();

		opengraphAttributes.put("title", title);
		twitterAttributes.put("title", title);

		String description = entry.getDescription();

		if (Validator.isNotNull(description)) {
			opengraphAttributes.put("description", description);
			twitterAttributes.put("description", description);
		}

		Group group = GroupLocalServiceUtil.getGroup(entry.getGroupId());

		opengraphAttributes.put("site_name", group.getDescriptiveName());

		opengraphAttributes.put("locale", themeDisplay.getLanguageId());

		String smallImage = null;

		if (entry.isSmallImage()) {
			smallImage = entry.getEntryImageURL(themeDisplay);
		}

		if (Validator.isNotNull(smallImage)) {
			opengraphAttributes.put("image", smallImage);
			twitterAttributes.put("image", smallImage);
		}
		
		String twCard = "summary";

		if (Validator.isNotNull(smallImage)) {
			twCard = "photo";
		}

		twitterAttributes.put("card", twCard);

		/*

		// TODO: Site Setting for twitter account

		String twSite = "";

		if (Validator.isNotNull(twSite)) {
			twitterAttributes.put("site", StringPool.AT + twSite);
		}

		*/

		User user = UserLocalServiceUtil.getUser(entry.getUserId());

		Contact contact = user.getContact();

		if (Validator.isNotNull(contact.getTwitterSn())) {
			twitterAttributes.put(
				"creator", StringPool.AT + contact.getTwitterSn());
		}
		
	}

	public final static String OPENGRAPH_ATTRIBUTE = "LIFERAY_SHARED_OPENGRAPH";
	public final static String TWITTER_ATTRIBUTE = "LIFERAY_SHARED_TWITTER";

}