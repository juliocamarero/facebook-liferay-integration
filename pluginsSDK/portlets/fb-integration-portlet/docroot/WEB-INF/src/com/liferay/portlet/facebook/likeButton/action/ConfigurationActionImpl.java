package com.liferay.portlet.facebook.likeButton.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class ConfigurationActionImpl extends BaseConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String portletResource = ParamUtil.getString(actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);

		String urlToLike = ParamUtil.getString(actionRequest, "urlToLike");
		String layoutStyle = ParamUtil.getString(actionRequest, "layoutStyle");
		String verbToDisplay = ParamUtil.getString(actionRequest, "verbToDisplay");
		String colorsScheme = ParamUtil.getString(actionRequest, "colorsScheme");
		String font = ParamUtil.getString(actionRequest, "font");
		boolean showFaces = ParamUtil.getBoolean(actionRequest, "showFaces");
		String width = ParamUtil.getString(actionRequest, "width");
		String height = ParamUtil.getString(actionRequest, "height");
		
		preferences.setValue("url-to-like", urlToLike);
		preferences.setValue("layout-style", layoutStyle);
		preferences.setValue("verb-to-display", verbToDisplay);
		preferences.setValue("colors-scheme", colorsScheme);
		preferences.setValue("font", font);
		preferences.setValue("show-faces", String.valueOf(showFaces));
		preferences.setValue("width", width);
		preferences.setValue("height", height);

		if (SessionErrors.isEmpty(actionRequest)) {
			preferences.store();

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/like_button/configuration.jsp";
	}
	
}
