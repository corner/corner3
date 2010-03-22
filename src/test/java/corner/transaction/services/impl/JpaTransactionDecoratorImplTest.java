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
package corner.transaction.services.impl;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.services.AspectDecorator;
import org.apache.tapestry5.ioc.test.IOCTestCase;
import org.easymock.EasyMock;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import corner.transaction.TransactionModule;
import corner.transaction.services.TransactionAdvisor;
import corner.transaction.services.TransactionDecorator;
import corner.transaction.services.impl.SpringTransactionAdvisor;

/**
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @since 0.1
 */
public class JpaTransactionDecoratorImplTest extends IOCTestCase {
	private Registry registry;

	private AspectDecorator aspectDecorator;

	@BeforeClass
	public void setup() {
		registry = buildRegistry();

		aspectDecorator = registry.getService(AspectDecorator.class);
	}

	@AfterClass
	public void shutdown() {
		registry.shutdown();

		aspectDecorator = null;
		registry = null;
	}

	@Test
	public void undecorated() {
		VoidService delegate = newMock(VoidService.class);
		PlatformTransactionManager transactionManager = newMock(PlatformTransactionManager.class);
		TransactionInterceptor transactionInterceptor= TransactionModule.buildTransactionInterceptor(transactionManager);
		TransactionAdvisor advisor = new SpringTransactionAdvisor(transactionInterceptor);
		TransactionDecorator decorator = new TransactionDecoratorImpl(aspectDecorator, advisor);
		VoidService interceptor = decorator.build(VoidService.class, delegate,
				"foo.Bar");

		delegate.undecorated();

		replay();
		interceptor.undecorated();
		verify();
	}

	
	@Test
	public void void_method() {
		VoidService delegate = newMock(VoidService.class);
		PlatformTransactionManager transactionManager = newMock(PlatformTransactionManager.class);
		TransactionInterceptor transactionInterceptor= TransactionModule.buildTransactionInterceptor(transactionManager);
		TransactionAdvisor advisor = new SpringTransactionAdvisor(transactionInterceptor);
		TransactionDecorator decorator = new TransactionDecoratorImpl(aspectDecorator, advisor);
		VoidService interceptor = decorator.build(VoidService.class, delegate,
				"foo.Bar");
		
		
		TransactionStatus transactionStatus = new SimpleTransactionStatus();
		expect(transactionManager.getTransaction(EasyMock.isA(TransactionAttribute.class))).andReturn(transactionStatus);//TransactionDefinition.PROPAGATION_REQUIRED);
		transactionManager.commit(transactionStatus);

		delegate.voidMethod();

		replay();
		interceptor.voidMethod();
		verify();

	}
	

	public interface VoidService {
		void undecorated();

		@Transactional
		void voidMethod();

		@Transactional
		void voidMethodWithParam(long id);
	}
}
