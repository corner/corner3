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
package corner.security.services;

import java.security.Principal;

/**
 * 用于Principal的服务类
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.1
 */
public interface SecurityPrincipalService {
	/**
	 * 取得当前上下文中的Principal
	 * 
	 * @return 
	 */
	public Principal getCurrentPrincipal();
	
	/**
	 * 取得principal的拥有的角色
	 * @param princiapl
	 * @return 如果principal没有任何角色,返回null;否则返回principal所拥有的角色
	 */
	public String[] getPrincipalRoles(Principal princiapl);

}
