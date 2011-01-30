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

import java.security.Principal;

import corner.security.services.SecurityPrincipalService;

/**
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class NonSecurityPrincipalService  implements SecurityPrincipalService {
    /**
     * 取得当前上下文中的Principal
     *
     * @return
     */
	@Override
    public Principal getCurrentPrincipal() {
        return null;
    }

    /**
     * 取得principal的拥有的角色
     *
     * @param princiapl
     * @return 如果principal没有任何角色,返回null;否则返回principal所拥有的角色
     */
	@Override
    public String[] getPrincipalRoles(Principal princiapl) {
        return new String[0];
    }
}
