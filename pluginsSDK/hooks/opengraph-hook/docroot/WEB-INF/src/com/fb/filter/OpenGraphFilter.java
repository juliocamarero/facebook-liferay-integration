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
package com.fb.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public class OpenGraphFilter extends BaseFilter {
	public static final String SKIP_FILTER =
		OpenGraphFilter.class.getName() + "SKIP_FILTER";

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (isAlreadyFiltered(request)) {
			return false;
		}
		else {
			return true;
		}
	}

	protected String getContent(HttpServletRequest request, String content) {
		if (Validator.isNull(_appNamespaces)) {
			if (_companyId == 0) {
				_companyId = PortalUtil.getCompanyId(request);
			}

			try {
				_appNamespaces = PrefsPropsUtil.getStringArray(
					_companyId, "APP_NAMESPACES", StringPool.NEW_LINE);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (Validator.isNotNull(_appNamespaces)) {
			StringBundler sb = new StringBundler(2 + 4 * _appNamespaces.length);

			sb.append(_START_HEAD);
			sb.append(" og:http://ogp.me/ns fb:http://ogp.me/ns/fb ");

			for (String appNamespace : _appNamespaces) {
				sb.append(appNamespace);
				sb.append(":http://ogp.me/ns/fb/");
				sb.append(appNamespace);
				sb.append(StringPool.SPACE);
			}

			content = StringUtil.replaceFirst(
				content, _START_HEAD, sb.toString());
		}

		return content;
	}

	protected Log getLog() {
		return _log;
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);

		if (_log.isDebugEnabled()) {
			String completeURL = HttpUtil.getCompleteURL(request);

			_log.debug("Adding Open Graph Attributes " + completeURL);
		}

		StringServletResponse stringServerResponse = new StringServletResponse(
			response);

		// LPS-30162

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();
		currentThread.setContextClassLoader(
			PortalClassLoaderUtil.getClassLoader());

		try {
			processFilter(
				OpenGraphFilter.class, request, stringServerResponse, filterChain);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		String contentType = response.getContentType();

		if ((contentType != null) &&
			contentType.startsWith(ContentTypes.TEXT_HTML)) {

			String content = getContent(
				request, stringServerResponse.getString());

			ServletResponseUtil.write(response, content);
		}
		else {
			ServletResponseUtil.write(response, stringServerResponse);
		}
	}

	private static final String _START_HEAD = "<head";

	private static Log _log = LogFactoryUtil.getLog(OpenGraphFilter.class);

	private String[] _appNamespaces = null;
	private long _companyId = 0;

}