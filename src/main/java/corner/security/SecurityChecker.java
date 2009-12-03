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
package corner.security;

import java.io.IOException;

/**
 * 安全检查
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 718 $
 * @since 0.0.1
 */
public interface SecurityChecker {

	/**
	 * 通过给定的安全设置的annotation来检查。
	 * @param secured 安全设置.
	 * @return 是否成功.
	 */
	public boolean check(Security secured) throws RequiredLoginException,InvlidateRoleException,IOException;
}
