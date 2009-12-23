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
package corner.cache.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.apache.tapestry5.internal.InternalSymbols;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.TapestryModule;
import org.apache.tapestry5.test.TapestryTestCase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import corner.cache.CacheModule;
import corner.cache.CacheSymbols;
import corner.cache.annotations.Cacheable;
import corner.cache.services.CacheStrategySource;
import corner.cache.services.CacheableAdvisor;
import corner.config.ConfigurationModule;
import corner.orm.base.BaseEntity;
import corner.orm.hibernate.impl.CacheHibernateEntityServiceImpl;
import corner.orm.services.EntityService;

/**
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheIntegration2Test extends TapestryTestCase {
	public static int testVar=0;
	private Registry registry;
	@BeforeClass
	public void setUpEnv(){
		//构建IOC 容器
		registry =this.buildRegistry(TapestryModule.class, CacheModule.class,
				ConfigurationModule.class,TestModule.class);
		registry.performRegistryStartup();
	}
	@AfterClass
	public void clearEnv(){
		registry.shutdown();
	}

	@Test
	public void test_cache() throws SecurityException, NoSuchMethodException {
		
		//获取测试服务类
		TestService testService = registry.getService(TestService.class);
		int r = testVar;
		//第一次执行方法
		testService.getList();
		assertEquals(r+1,testVar);
		//从缓存读取
		testService.getList();
		assertEquals(r+1,testVar);
		testService.getList();
		assertEquals(r+1,testVar);
		
		//当增加操作发生,缓存被清除
		EntityService entityService = registry.getService(EntityService.class);
		TestMember member = new TestMember();
		entityService.save(member);
		//,需要重新执行方法
		testService.getList();
		assertEquals(r+2,testVar);
		
		//当发生更新操作则不更新
		entityService = registry.getService(EntityService.class);
		entityService.update(member);
		//,需要重新执行方法
		testService.getList();
		assertEquals(r+2,testVar);
		
		//当发生删除操作则更新缓存
		entityService = registry.getService(EntityService.class);
		entityService.delete(member);
		//,需要重新执行方法
		testService.getList();
		assertEquals(r+3,testVar);
	}
	public static interface TestService{
		@Cacheable(clazz=TestMember.class)
		public List<TestMember> getList();
	}
	public static class TestServiceImpl implements TestService{
		private List<TestMember> list= new ArrayList<TestMember>();
		public List<TestMember> getList() {
			testVar = testVar+1;
			return list;
		}
	}
	public static class TestModule{
		public static void bind(ServiceBinder binder){
			binder.bind(TestService.class,TestServiceImpl.class);
		}
		@Match("*TestService")
		public static void adviseCacheable(
				CacheableAdvisor advisor, MethodAdviceReceiver receiver) {
				advisor.addCacheableAdvice(receiver);
		}
		public static EntityService buildEntityService(TypeCoercer typeCoercer,
				PropertyAccess propertyAccess, 
				CacheStrategySource cacheSource){
			AnnotationConfiguration cfg = new AnnotationConfiguration();
			cfg.addAnnotatedClass(TestMember.class);
			//测试的时候使用内存数据库
			cfg.setProperty("hibernate.connection.url","jdbc:h2:mem:target/testdb");
			cfg.setProperty( Environment.HBM2DDL_AUTO, "create-drop");
			SessionFactory sessionFactory = cfg.configure().buildSessionFactory();
			org.hibernate.classic.Session session = sessionFactory.openSession();
			
			CacheHibernateEntityServiceImpl service = new CacheHibernateEntityServiceImpl(session,
					typeCoercer,propertyAccess,cacheSource);
			return service;
		}
		public static void contributeApplicationDefaults(
				MappedConfiguration<String, String> configuration
		) {
			configuration.add(InternalSymbols.ALIAS_MODE, "test");		
			configuration.add(CacheSymbols.ENABLE_CACHE, "true");
		}
	}
	@Entity
	class TestMember extends BaseEntity{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6257648766417644500L;
		private String userName;

		/**
		 * @return the userName
		 */
		public String getUserName() {
			return userName;
		}

		/**
		 * @param userName the userName to set
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}
	};
	
}
