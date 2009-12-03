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


import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestGlobals;

import corner.security.impl.NonSecurityPrincipalService;
import corner.security.impl.ResourceSecurityWorker;
import corner.security.impl.SecurityCheckerImpl;
import corner.security.impl.SecurityContextFilter;
import corner.security.impl.SecurityWorker;

/**
 * 定义安全的module
 *
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @author dong
 * @version $Revision: 766 $
 * @since 0.0.1
 */
public class SecurityModule {
    /**
     * 绑定使用的service.
     *
     * @param binder Service Binder
     * @see ServiceBinder
     */
    public static void bind(ServiceBinder binder) {
        binder.bind(SecurityChecker.class, SecurityCheckerImpl.class);
        binder.bind(SecurityPrincipalService.class, NonSecurityPrincipalService.class);
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add("enable-security", "false");
    }
    /**
     * 对安全控制的过滤.
     *
     * @param configuration    配置.
     * @param requestGlobals   request globals object.
     * @param principalService principal service.
     */
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration, RequestGlobals requestGlobals, SecurityPrincipalService principalService) {
        configuration.add("securityFilter", new SecurityContextFilter(requestGlobals, principalService), "after:StoreIntoGlobals");
    }

    /**
     * 对安全的控制的ClassTransform worker
     *
     * @param configuration  Module的配置实例
     * @param checker        安全校验的实现实例
     * @param resolver       用于查找Component资源的实例
     * @param linkFactory    用于构建PageLink的LinkFactory
     * @param isProduct      是否是生产环境
     * @param enableSecurity 是否激活安全校验,当isProduct为true时,此参数不起作用,即生产环境肯定要激活安全校验
     */
    public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker> configuration, SecurityChecker checker, ComponentClassResolver resolver, LinkSource linkFactory, @Inject
    @Symbol(SymbolConstants.PRODUCTION_MODE)
    boolean isProduct, @Inject
    @Symbol(SecurityConstants.ENABLE_SECURITY)
    boolean enableSecurity) {
        /*
           * 1.在生产环境下,激活安全校验 2.在非生产环境下,如果enableSecurity=true,则激活安全校验
           */
        boolean _enableSecurity = isProduct || enableSecurity;
        if (_enableSecurity) {
            configuration.add("security", new SecurityWorker(checker, resolver, linkFactory));
		}
	}
    public static void contributeComponentClassTransformWorker(
            OrderedConfiguration<ComponentClassTransformWorker> configuration,
            ObjectLocator locator)
    {
            	configuration.add("ResourceSecurityChecker", locator.autobuild(ResourceSecurityWorker.class));
    }
}
