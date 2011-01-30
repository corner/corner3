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
package corner.orm.gae.impl;

import java.util.HashMap;
import java.util.Map;

import com.google.apphosting.api.ApiProxy.Environment;

/**
 * test environment
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class TestEnvironment implements Environment {

	/**
	 * @see com.google.apphosting.api.ApiProxy.Environment#getAppId()
	 */
	@Override
	public String getAppId() {
		return "Unit Tests";
	}

	/**
	 * @see com.google.apphosting.api.ApiProxy.Environment#getVersionId()
	 */
	@Override
	public String getVersionId() {
		return "1.0";
	}

	public void setDefaultNamespace(String s) {
	}

	/**
	 * @see com.google.apphosting.api.ApiProxy.Environment#getRequestNamespace()
	 */
	@Override
	public String getRequestNamespace() {
		return "gmail.com";
	}

	/**
	 * @return
	 * @since 3.1
	 */
	public String getDefaultNamespace() {
		return null;
	}

	/**
	 * @see com.google.apphosting.api.ApiProxy.Environment#getAuthDomain()
	 */
	@Override
	public String getAuthDomain() {
		return null;
	}

	/**
	 * @see com.google.apphosting.api.ApiProxy.Environment#isLoggedIn()
	 */
	@Override
	public boolean isLoggedIn() {
		return false;
	}

	/**
	 * @see com.google.apphosting.api.ApiProxy.Environment#getEmail()
	 */
	@Override
	public String getEmail() {
		return null;
	}

	/**
	 * @see com.google.apphosting.api.ApiProxy.Environment#isAdmin()
	 */
	@Override
	public boolean isAdmin() {
		return false;
	}

	/**
	 * @see com.google.apphosting.api.ApiProxy.Environment#getAttributes()
	 */
	@Override
	public Map<String, Object> getAttributes() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
}
