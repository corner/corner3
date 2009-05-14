/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-5-14
 */
package corner.services.transaction;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.services.AliasContribution;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * spring transaction module
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class SpringTransactionModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(HibernateTransactionAdvisor.class,SpringTransactionAdvisor.class).withId("SpringTransactionAdvisor");
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
	
}
