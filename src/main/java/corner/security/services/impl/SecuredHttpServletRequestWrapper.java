/* 
 * Copyright 2009 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.security.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import corner.security.services.SecurityPrincipalService;

/**
 * 对servlet中的Request进行了包装了，封装自带的{@link #getUserPrincipal()},以及
 * {@link #isUserInRole(String)}.
 * 
 * @author Jun Tsai
 * @version $Revision$
 * @since 0.0.1
 */
public class SecuredHttpServletRequestWrapper implements HttpServletRequest {
	/** * */
	private final HttpServletRequest _request;
	private final SecurityPrincipalService _principalService;
	private String[] _roles;

	/**
	 * @param request
	 *            原始的HttpServletRequest
	 * @param principalService
	 *            用于获取当前的登录用户,使用变量传递Pincipal会导致已经登录的用户状态在Immediate模式下无法获取
	 * @param roles
	 */
	SecuredHttpServletRequestWrapper(HttpServletRequest request,
			SecurityPrincipalService principalService) {
		this._request = request;
		this._principalService = principalService;
		this._roles = null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#isUserInRole(java.lang.String)
	 * @since 0.0.1
	 */
	@Override
	public boolean isUserInRole(String role) {
		if (_roles == null) {
			String[] roles = (_principalService.getCurrentPrincipal() != null ? _principalService
					.getPrincipalRoles(_principalService.getCurrentPrincipal())
					: null);
			if (roles == null) {
				roles = new String[0];
			}
			_roles = roles;
		}
		if (_roles == null || _roles.length == 0 || role == null) {
			return false;
		}
		// 循环匹配的角色.
		for (String tmpRole : _roles) {
			if (role.equals(tmpRole)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
	 * @since 0.0.1
	 */
	@Override
	public Principal getUserPrincipal() {
		return this._principalService.getCurrentPrincipal();
	}

	/**
	 * @see javax.servlet.ServletRequest#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String name) {
		return _request.getAttribute(name);
	}

	/**
	 * @see javax.servlet.ServletRequest#getAttributeNames()
	 */
	@Override
	public Enumeration getAttributeNames() {
		return _request.getAttributeNames();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getAuthType()
	 */
	@Override
	public String getAuthType() {
		return _request.getAuthType();
	}

	/**
	 * @see javax.servlet.ServletRequest#getCharacterEncoding()
	 */
	@Override
	public String getCharacterEncoding() {
		return _request.getCharacterEncoding();
	}

	/**
	 * @see javax.servlet.ServletRequest#getContentLength()
	 */
	@Override
	public int getContentLength() {
		return _request.getContentLength();
	}

	/**
	 * @see javax.servlet.ServletRequest#getContentType()
	 */
	@Override
	public String getContentType() {
		return _request.getContentType();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getContextPath()
	 */
	@Override
	public String getContextPath() {
		return _request.getContextPath();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getCookies()
	 */
	@Override
	public Cookie[] getCookies() {
		return _request.getCookies();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getDateHeader(java.lang.String)
	 */
	@Override
	public long getDateHeader(String name) {
		return _request.getDateHeader(name);
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
	 */
	@Override
	public String getHeader(String name) {
		return _request.getHeader(name);
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
	 */
	@Override
	public Enumeration getHeaderNames() {
		return _request.getHeaderNames();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
	 */
	@Override
	public Enumeration getHeaders(String name) {
		return _request.getHeaders(name);
	}

	/**
	 * @see javax.servlet.ServletRequest#getInputStream()
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return _request.getInputStream();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getIntHeader(java.lang.String)
	 */
	@Override
	public int getIntHeader(String name) {
		return _request.getIntHeader(name);
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return _request.getLocale();
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocales()
	 */
	@Override
	public Enumeration getLocales() {
		return _request.getLocales();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getMethod()
	 */
	@Override
	public String getMethod() {
		return _request.getMethod();
	}

	/**
	 * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String name) {
		return _request.getParameter(name);
	}

	/**
	 * @see javax.servlet.ServletRequest#getParameterMap()
	 */
	@Override
	public Map getParameterMap() {
		return _request.getParameterMap();
	}

	/**
	 * @see javax.servlet.ServletRequest#getParameterNames()
	 */
	@Override
	public Enumeration getParameterNames() {
		return _request.getParameterNames();
	}

	/**
	 * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) {
		return _request.getParameterValues(name);
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getPathInfo()
	 */
	@Override
	public String getPathInfo() {
		return _request.getPathInfo();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
	 */
	@Override
	public String getPathTranslated() {
		return _request.getPathTranslated();
	}

	/**
	 * @see javax.servlet.ServletRequest#getProtocol()
	 */
	@Override
	public String getProtocol() {
		return _request.getProtocol();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getQueryString()
	 */
	@Override
	public String getQueryString() {
		return _request.getQueryString();
	}

	/**
	 * @see javax.servlet.ServletRequest#getReader()
	 */
	@Override
	public BufferedReader getReader() throws IOException {
		return _request.getReader();
	}

	/**
	 * @deprecated
	 * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
	 */
	@Deprecated
	@Override
	public String getRealPath(String path) {
		return _request.getRealPath(path);
	}

	/**
	 * @see javax.servlet.ServletRequest#getRemoteAddr()
	 */
	@Override
	public String getRemoteAddr() {
		return _request.getRemoteAddr();
	}

	/**
	 * @see javax.servlet.ServletRequest#getRemoteHost()
	 */
	@Override
	public String getRemoteHost() {
		return _request.getRemoteHost();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
	 */
	@Override
	public String getRemoteUser() {
		return _request.getRemoteUser();
	}

	/**
	 * @see javax.servlet.ServletRequest#getRequestDispatcher(java.lang.String)
	 */
	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return _request.getRequestDispatcher(path);
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
	 */
	@Override
	public String getRequestedSessionId() {
		return _request.getRequestedSessionId();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getRequestURI()
	 */
	@Override
	public String getRequestURI() {
		return _request.getRequestURI();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getRequestURL()
	 */
	@Override
	public StringBuffer getRequestURL() {
		return _request.getRequestURL();
	}

	/**
	 * @see javax.servlet.ServletRequest#getScheme()
	 */
	@Override
	public String getScheme() {
		return _request.getScheme();
	}

	/**
	 * @see javax.servlet.ServletRequest#getServerName()
	 */
	@Override
	public String getServerName() {
		return _request.getServerName();
	}

	/**
	 * @see javax.servlet.ServletRequest#getServerPort()
	 */
	@Override
	public int getServerPort() {
		return _request.getServerPort();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getServletPath()
	 */
	@Override
	public String getServletPath() {
		return _request.getServletPath();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getSession()
	 */
	@Override
	public HttpSession getSession() {
		return _request.getSession();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
	 */
	@Override
	public HttpSession getSession(boolean create) {
		return _request.getSession(create);
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
	 */
	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return _request.isRequestedSessionIdFromCookie();
	}

	/**
	 * @deprecated
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
	 */
	@Override
	@Deprecated
	public boolean isRequestedSessionIdFromUrl() {
		return _request.isRequestedSessionIdFromUrl();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
	 */
	@Override
	public boolean isRequestedSessionIdFromURL() {
		return _request.isRequestedSessionIdFromURL();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
	 */
	@Override
	public boolean isRequestedSessionIdValid() {
		return _request.isRequestedSessionIdValid();
	}

	/**
	 * @see javax.servlet.ServletRequest#isSecure()
	 */
	@Override
	public boolean isSecure() {
		return _request.isSecure();
	}

	/**
	 * @see javax.servlet.ServletRequest#removeAttribute(java.lang.String)
	 */
	@Override
	public void removeAttribute(String name) {
		_request.removeAttribute(name);
	}

	/**
	 * @see javax.servlet.ServletRequest#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void setAttribute(String name, Object o) {
		_request.setAttribute(name, o);
	}

	/**
	 * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
	 */
	@Override
	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		_request.setCharacterEncoding(arg0);
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocalAddr()
	 */
	@Override
	public String getLocalAddr() {
		return _request.getLocalAddr();
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocalName()
	 */
	@Override
	public String getLocalName() {
		return _request.getLocalName();
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocalPort()
	 */
	@Override
	public int getLocalPort() {
		return _request.getLocalPort();
	}

	/**
	 * @see javax.servlet.ServletRequest#getRemotePort()
	 */
	@Override
	public int getRemotePort() {
		return _request.getRemotePort();
	}
}