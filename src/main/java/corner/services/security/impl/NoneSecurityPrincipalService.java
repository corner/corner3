/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-20
 */
package corner.services.security.impl;

import corner.services.security.SecurityPrincipalService;

import java.security.Principal;

/**
 * 没有任何功能的安全角色服务类
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class NoneSecurityPrincipalService implements SecurityPrincipalService {
    public Principal getCurrentPrincipal() {
        return null;
    }

    public String[] getPrincipalRoles(Principal princiapl) {
        return new String[0]; 
    }
}
