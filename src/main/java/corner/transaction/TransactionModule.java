/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-5-14
 */
package corner.transaction;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Match;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import corner.transaction.services.TransactionAdvisor;
import corner.transaction.services.TransactionDecorator;
import corner.transaction.services.impl.SpringTransactionAdvisor;

/**
 * spring transaction module
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class TransactionModule {
	public static void bind(ServiceBinder binder){
		binder.bind(TransactionAdvisor.class,SpringTransactionAdvisor.class);
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

	@Match("EntityService")
	public static <T> T decorateTransactionally(
			TransactionDecorator decorator, Class<T> serviceInterface,
			T delegate, String serviceId) {
		return decorator.build(serviceInterface, delegate, serviceId);
	}
}
