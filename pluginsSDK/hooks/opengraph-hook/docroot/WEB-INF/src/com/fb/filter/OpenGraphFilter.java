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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public class OpenGraphFilter extends BaseFilter {
  
	public final static String SKIP_FILTER = OpenGraphFilter.class.getName() + "SKIP_FILTER";
	public final static String OPENGRAPH_XMLNS = "http://ogp.me/ns";
	public final static String FACEBOOK_XMLNS = "http://ogp.me/ns/fb";

	private final static String _START_HTML = "<html";

	private static Log _log = LogFactoryUtil.getLog(OpenGraphFilter.class);

	@Override
	public boolean isFilterEnabled(HttpServletRequest request, HttpServletResponse response) {
		if (isAlreadyFiltered(request)) {return false;}
		else {return true;}
	}

	protected String getContent(HttpServletRequest request, String content) {
		StringBundler sb = new StringBundler();
		sb.append(_START_HTML);
		sb.append(" xmlns:og=\"" + OPENGRAPH_XMLNS + "\" xmlns:fb=\"" + FACEBOOK_XMLNS + "\"");
		content = StringUtil.replaceFirst(content, _START_HTML, sb.toString());
		return content;
	}

	protected Log getLog() {return _log;}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {return true;}
		else {return false;}
	}

	@Override
	protected void processFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws Exception {

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);
		if (_log.isDebugEnabled()) {
			String completeURL = HttpUtil.getCompleteURL(request);
			_log.debug("Adding Open Graph Attributes " + completeURL);
		}

		StringServletResponse stringServletResponse = new StringServletResponse(response);
		processFilter(OpenGraphFilter.class, request, stringServletResponse, filterChain);
		
		String contentType = response.getContentType();
		if ((contentType != null)&&(contentType.startsWith(ContentTypes.TEXT_HTML))) {
			String contentEncoding = stringServletResponse.getHeader(HttpHeaders.CONTENT_ENCODING);
			boolean isGZIP = ((contentEncoding != null)&&(contentEncoding.toLowerCase().equals("gzip")));
			String content;
			if (isGZIP) {
				content = decompress(stringServletResponse.getUnsyncByteArrayOutputStream().toByteArray(), response.getCharacterEncoding());
			} else {
				content = stringServletResponse.getString();
			}
			content = getContent(request, content);
			if (isGZIP) {ServletResponseUtil.write(response, compress(content, response.getCharacterEncoding()));}
			else {ServletResponseUtil.write(response, content);}
		} else {
			ServletResponseUtil.write(response, stringServletResponse);
		}
		
	}

  public static byte[] compress(String str, String encoding) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gos = new GZIPOutputStream(baos);
		gos.write(str.getBytes(encoding));
		gos.close();
		return baos.toByteArray();
  }

  public static String decompress(byte[] source, String encoding) throws Exception {
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(source));
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, encoding));
		StringBuffer out = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {out.append(line);}
		return out.toString();
  }
    
}