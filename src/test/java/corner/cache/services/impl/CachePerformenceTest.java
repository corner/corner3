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
package corner.cache.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

import corner.cache.CacheModule;
import corner.cache.CacheSymbols;
import corner.cache.annotations.CacheKeyParameter;
import corner.cache.annotations.CacheNsParameter;
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
public class CachePerformenceTest extends TapestryTestCase {

	public static int testVar1=0;
	public static int testVar2=0;
	private Registry registry;
	private TestService testService;
	private TestMember m1;
	private long start;
	@BeforeClass
	public void setUpEnv(){
		//构建IOC 容器
		registry =this.buildRegistry(TapestryModule.class, CacheModule.class,
				ConfigurationModule.class,TestModule.class);
		registry.performRegistryStartup();
		//获取测试服务类
		testService = registry.getService(TestService.class);
		EntityService entityService = registry.getService(EntityService.class);
		
		//保存两个用户
		 m1 = new TestMember();
		m1.setId(1L);
		TestMember m2 = new TestMember();
		m2.setId(2L);
		entityService.save(m1);
		entityService.save(m2);
		start = new Date().getTime();
	}
	@AfterClass
	public void clearEnv(){
		registry.shutdown();
		long end = new Date().getTime();
		System.out.println("times::: "+(end-start));
	}

	/**
	 * 基于用户的缓存测试
	 * 案例：缓存属于用户的文章列表.
	 * 系统用户m1的文章更新，仅仅清除m1的文章缓存列表，但是m2的缓存列表不更新
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @since 3.1
	 */
//	@Test(invocationCount=10000,threadPoolSize=100)
	public void test_cache() throws SecurityException, NoSuchMethodException {
		testService.getList(m1);
		//从缓存读取
		testService.getList(m1);
		
	}
	public static interface TestService{
		@Cacheable(clazz=TestArticle.class,namespaces={@CacheNsParameter(name="member",keyIndex=1)})
		public List<TestArticle> getList(@CacheKeyParameter TestMember member);
	}
	public static class TestServiceImpl implements TestService{
		private List<TestArticle> list= new ArrayList<TestArticle>();
		@Override
		public List<TestArticle> getList(TestMember member) {
			if(member.getId() ==1){
				testVar1  = testVar1+1;
			}else{
				testVar2  = testVar2+1;
			}
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
			cfg.addAnnotatedClass(TestArticle.class);
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
	public static class TestMember extends BaseEntity{
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
		@Override
		public String toString(){
			return String.valueOf(this.getId());
		}
	};
	@Entity
	public static class TestArticle extends BaseEntity{
		/**
		 * 
		 */
		private static final long serialVersionUID = -5205031106627783849L;
		private TestMember member;
		private String title;
		/**
		 * @param member the member to set
		 */
		public void setMember(TestMember member) {
			this.member = member;
		}
		/**
		 * @return the member
		 */
		@ManyToOne
		public TestMember getMember() {
			return member;
		}
		/**
		 * @param title the title to set
		 */
		public void setTitle(String title) {
			this.title = title;
		}
		/**
		 * @return the title
		 */
		public String getTitle() {
			return title;
		}
		@Override
		public String toString(){
			return String.valueOf(this.getId());
		}
	}
}
