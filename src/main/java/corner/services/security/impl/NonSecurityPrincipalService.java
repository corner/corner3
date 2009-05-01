/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-20
 */
package corner.services.security.impl;

import java.security.Principal;

import corner.services.security.SecurityPrincipalService;

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
    public Principal getCurrentPrincipal() {
        return null;
    }

    /**
     * 取得principal的拥有的角色
     *
     * @param princiapl
     * @return 如果principal没有任何角色,返回null;否则返回principal所拥有的角色
     */
    public String[] getPrincipalRoles(Principal princiapl) {
        return new String[0];
    }
}
