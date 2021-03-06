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
package corner.orm.hibernate;


import java.io.IOException;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.services.AliasContribution;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.meetup.memcached.ErrorHandler;

import corner.cache.CacheSymbols;
import corner.cache.annotations.Memcache;
import corner.cache.services.CacheManager;
import corner.cache.services.impl.memcache.MemcacheConfig;
import corner.cache.services.impl.memcache.MemcachedCacheManager;
import corner.config.services.ConfigurationSource;
import corner.migration.MigrationModule;
import corner.orm.hibernate.impl.CacheHibernateEntityServiceImpl;
import corner.orm.hibernate.impl.EntityServiceImpl;
import corner.orm.hibernate.impl.HibernateEntityServiceImpl;
import corner.orm.hibernate.impl.SpringSessionManagerImpl;
import corner.orm.services.EntityService;
import corner.orm.services.impl.CornerEntityPersistentFieldStrategy;
import corner.tree.TreeModule;

/**
 * hibernate 操作的模块
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
@SubModule({MigrationModule.class,TreeModule.class})
public class HibernateModule {
	public static void bind(ServiceBinder binder){
		binder.bind(HibernateEntityService.class,HibernateEntityServiceImpl.class);
	}
	public static EntityService buildEntityService(@Symbol(CacheSymbols.ENABLE_CACHE) boolean enableCache,ObjectLocator locator){
		if(enableCache){
			return locator.autobuild(CacheHibernateEntityServiceImpl.class);
		}else{
			return locator.autobuild(EntityServiceImpl.class);
		}
	}
	public static HibernateTemplate buildHibernateTemplate(
			SessionFactory sessionFactory) {
		HibernateTemplate template = new HibernateTemplate(sessionFactory);
		return template;
	}
	/**
	 * 构造基于Memcached的CacheManager,并启动该manager
	 * 
	 * @param configSource
	 *            配置源
	 * @param logger
	 *            日志实例
	 * @param errorHandler
	 *            访问Memcache时的异常处理器
	 * @return
	 * @since 0.0.2
	 */
	@Marker(Memcache.class)
	public CacheManager buildMemcachedCacheManager(
			ConfigurationSource configSource, Logger logger,
			ErrorHandler errorHandler) {
		MemcacheConfig _config = configSource
				.getServiceConfig(MemcacheConfig.class);
		CacheManager _manager = new MemcachedCacheManager(_config, logger,
				errorHandler);
		_manager.start();
		return _manager;
	}
    /**
     * Contributes the following: <dl> <dt>entity</dt> <dd>Stores the id of the entity and reloads from the {@link
     * Session}</dd> </dl>
     */
    public static void contributePersistentFieldManager(
            MappedConfiguration<String, PersistentFieldStrategy> configuration)
    {
    	configuration.overrideInstance("entity", CornerEntityPersistentFieldStrategy.class);
    }
	/**
	 * 替换由HibernateModule提供的HibernateSessionManager
	 * 
	 * @param sessionSource
	 * @param perthreadManager
	 * @return
	 */
	@Scope(org.apache.tapestry5.ioc.ScopeConstants.PERTHREAD)
	public static HibernateSessionManager buildSpringSessionManager(
			HibernateSessionSource sessionSource,
			PerthreadManager perthreadManager) {
		SpringSessionManagerImpl service = new SpringSessionManagerImpl(
				sessionSource);
		perthreadManager.addThreadCleanupListener(service);
		return service;
	}
	/**
	 * 覆盖由Hibernate Module提供的HibernateSessionManager
	 * 
	 * @param manager
	 * @param configuration
	 */
	public static void contributeAliasOverrides(
			@Local HibernateSessionManager manager,
			Configuration<AliasContribution<HibernateSessionManager>> configuration){
		configuration.add(AliasContribution.create(
				HibernateSessionManager.class, manager));
	}
	
	/**
	 * build spring platform transaction manager
	 * @param sessionSource session source
	 * @param session 
	 * @return
	 * @since 0.0.2
	 */
	public static PlatformTransactionManager buildPlatformTransactionManager(HibernateSessionSource sessionSource){
		HibernateTransactionManager platformTransactionManager = new HibernateTransactionManager();
		platformTransactionManager.setSessionFactory(sessionSource.getSessionFactory());
		platformTransactionManager.afterPropertiesSet();
		return platformTransactionManager;
	}
	
	
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,@Local final HibernateSessionManager hibernateSessionManager)
    {

        RequestFilter openSessionInView = new RequestFilter()
        {
            /**
             * @see org.apache.tapestry5.services.RequestFilter#service(org.apache.tapestry5.services.Request, org.apache.tapestry5.services.Response, org.apache.tapestry5.services.RequestHandler)
             */
        	@Override
            public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException
            {
            	//get session
            	hibernateSessionManager.getSession();
                return handler.service(request, response);
            }
        };
        
        configuration.add("OpenSessionInView",openSessionInView,
        		"after:StoreIntoGlobals","before:EndOfRequest");
    }
}
