package com.liferay.portlet.facebook.likeBox.action;


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
import com.liferay.portal.kernel.util.GetterUtil;
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

		boolean useFBSDK = ParamUtil.getBoolean(actionRequest, "isFBSDK");

		String href = ParamUtil.getString(actionRequest, "href");
		String width = ParamUtil.getString(actionRequest, "width");
		String height = ParamUtil.getString(actionRequest, "height");
		int connections = ParamUtil.getInteger(actionRequest, "connections", 10);
		boolean showStream = ParamUtil.getBoolean(actionRequest, "showStream");
		boolean showHeader = ParamUtil.getBoolean(actionRequest, "showHeader");
		boolean showFaces = ParamUtil.getBoolean(actionRequest, "showFaces");

		preferences.setValue("use-fb-sdk", String.valueOf(useFBSDK));
		preferences.setValue("href", href);
		preferences.setValue("width", width);
		preferences.setValue("height", height);
		preferences.setValue("connections", String.valueOf(connections));
		preferences.setValue("show-stream", String.valueOf(showStream));
		preferences.setValue("show-header", String.valueOf(showHeader));
		preferences.setValue("show-faces", String.valueOf(showFaces));

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

		return "/html/like_box/configuration.jsp";
	}
	
}
