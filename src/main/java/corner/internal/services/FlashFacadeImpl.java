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
package corner.internal.services;


import org.apache.tapestry5.services.Cookies;

import corner.services.FlashFacade;

/**
 * 实现flash消息机制
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public class FlashFacadeImpl implements FlashFacade {
	private Cookies cookies;

	public FlashFacadeImpl(Cookies cookies){
		this.cookies = cookies;
	}

	/**
	 * @see ganshane.services.flash.FlashFacade#get(java.lang.String)
	 */
	public String get(String key) {
		String v = this.cookies.readCookieValue(key);
		this.cookies.removeCookieValue(key);
		return v;
	}

	/**
	 * @see ganshane.services.flash.FlashFacade#push(java.lang.String, java.lang.String)
	 */
	public void push(String key, String value) {
		cookies.writeCookieValue(key, value, 30);
	}

}
