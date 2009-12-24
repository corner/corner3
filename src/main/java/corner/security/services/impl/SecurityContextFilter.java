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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

import corner.security.services.SecurityPrincipalService;

/**
 * 对安全进行控制的过滤器
 * 
 * @author Jun Tsai
 * @author dong
 * @version $Revision$
 * @since 0.0.1
 */
public class SecurityContextFilter implements RequestFilter {

	/** request globals object * */
	private RequestGlobals _requestGlobals;
	/** principal service * */
	private SecurityPrincipalService _principalService;

	/**
	 * @param requestGlobals
	 * @param principalService
	 */
	public SecurityContextFilter(RequestGlobals requestGlobals, SecurityPrincipalService principalService) {
		this._requestGlobals = requestGlobals;
		_principalService = principalService;
	}

	/**
	 * 
	 * @see org.apache.tapestry.services.RequestFilter#service(org.apache.tapestry.services.Request,
	 *      org.apache.tapestry.services.Response,
	 *      org.apache.tapestry.services.RequestHandler)
	 * @since 0.0.1
	 */
	public boolean service(Request request, Response response, RequestHandler handler) throws IOException {
		HttpServletRequest servletRequest = _requestGlobals.getHTTPServletRequest();
		SecuredHttpServletRequestWrapper delegateRequest = new SecuredHttpServletRequestWrapper(servletRequest,this._principalService);
		_requestGlobals.storeServletRequestResponse(delegateRequest, _requestGlobals.getHTTPServletResponse());
		return handler.service(request, response);

	}
}