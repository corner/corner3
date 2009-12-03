/* 
 * Copyright 2008 The Corner Team.
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
package corner.security.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.services.RequestGlobals;

import corner.security.InvlidateRoleException;
import corner.security.RequiredLoginException;
import corner.security.Security;
import corner.security.SecurityChecker;

/**
 * 安全检查类
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 718 $
 * @since 0.0.1
 */
public class SecurityCheckerImpl implements SecurityChecker {

	private RequestGlobals _requestGlobals;

	public SecurityCheckerImpl(RequestGlobals requestGlobals) {
		_requestGlobals = requestGlobals;
	}

	/**
	 * 
	 * @throws IOException
	 */
	public boolean check(Security secured) throws RequiredLoginException, InvlidateRoleException, IOException {
		HttpServletRequest request = _requestGlobals.getHTTPServletRequest();
		if (request.getUserPrincipal() == null) {
			return false;
		}
		boolean result = false;
		final String[] roles = secured.value();
		for (String role : roles) {
			if (role.length() == 0) {
				// 为空的角色,在此认为不需要进行权限检验,通过验证
				result = true;
				break;
			} else if (request.isUserInRole(role)) {
				result = true;
				break;
			}
		}
		return result;

	}

}
