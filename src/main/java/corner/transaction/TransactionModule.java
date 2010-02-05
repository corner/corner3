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
package corner.transaction;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Match;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import corner.transaction.services.TransactionAdvisor;
import corner.transaction.services.TransactionDecorator;
import corner.transaction.services.impl.SpringTransactionAdvisor;
import corner.transaction.services.impl.TransactionDecoratorImpl;

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
		binder.bind(TransactionDecorator.class,TransactionDecoratorImpl.class);
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
