/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-5-14
 */
package corner.services.transaction;

import java.io.IOException;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.hibernate.HibernateTransactionDecorator;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.services.AliasContribution;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import corner.services.EntityService;
import corner.services.impl.EntityServiceImpl;
import corner.services.migration.MigrationModule;

/**
 * spring transaction module
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
@SubModule(MigrationModule.class)
public class SpringTransactionModule {
	public static HibernateTemplate buildHibernateTemplate(
			SessionFactory sessionFactory) {
		HibernateTemplate template = new HibernateTemplate(sessionFactory);
		return template;

	}
	public static void bind(ServiceBinder binder) {
		binder.bind(HibernateTransactionAdvisor.class,SpringTransactionAdvisor.class).withId("SpringTransactionAdvisor");
		binder.bind(EntityService.class, EntityServiceImpl.class);
	}
	/**
	 * build spring transaction interceptor
	 * @param transactionManager transaction manager
	 * @return transaction manager instance
	 * @since 0.0.2
	 */
	public static TransactionInterceptor buildTransactionInterceptor(PlatformTransactionManager transactionManager){
		AnnotationTransactionAttributeSource attributeSource = new AnnotationTransactionAttributeSource();
    	return new TransactionInterceptor(transactionManager,attributeSource);
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
	public static void contributeAliasOverrides(
			@Local HibernateTransactionAdvisor advisor,
			Configuration<AliasContribution<HibernateTransactionAdvisor>> advisorConfiguration) {
		advisorConfiguration.add(AliasContribution.create(HibernateTransactionAdvisor.class, advisor));
	}
	
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,@Local final HibernateSessionManager hibernateSessionManager)
    {

        RequestFilter openSessionInView = new RequestFilter()
        {
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
	@Match("EntityService")
	public static <T> T decorateTransactionally(
			HibernateTransactionDecorator decorator, Class<T> serviceInterface,
			T delegate, String serviceId) {
		return decorator.build(serviceInterface, delegate, serviceId);
	}
}
