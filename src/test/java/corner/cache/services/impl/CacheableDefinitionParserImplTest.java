package corner.cache.services.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.internal.InternalSymbols;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.TapestryModule;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.test.TapestryTestCase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import corner.cache.CacheModule;
import corner.cache.CacheSymbols;
import corner.cache.annotations.CacheKeyParameter;
import corner.cache.annotations.Cacheable;
import corner.cache.services.CacheStrategySource;
import corner.cache.services.CacheableAdvisor;
import corner.cache.services.CacheableDefinitionParser;
import corner.cache.services.impl.CacheIntegration2Test.TestMember;
import corner.config.ConfigurationModule;
import corner.orm.hibernate.impl.CacheHibernateEntityServiceImpl;
import corner.orm.services.EntityService;

public class CacheableDefinitionParserImplTest extends TapestryTestCase{
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
	public void test_parse() throws SecurityException, NoSuchMethodException{
		TestMember obj = new TestMember(); 
		obj.setId(1L);
		Invocation invocation = newMock(Invocation.class);
		expect(invocation.getParameter(0)).andReturn(obj);
		replay();
		Method method = TestService.class.getMethod("getList", TestMember.class);
		CacheableDefinitionParser parser = registry.getService(CacheableDefinitionParser.class);
		String key = parser.parseAsKey(invocation, method);
		Assert.assertEquals(key, "corner.cache.services.impl.CacheIntegration2Test$TestMember._list_v0.40b13e3aaf1bab85622efac215f0fc6220cff2d0");
		verify();
	}
	@Test
	public void test_parse_object_parameter() throws SecurityException, NoSuchMethodException{
		TestMember obj = new TestMember(); 
		obj.setId(1L);
		Invocation invocation = newMock(Invocation.class);
		expect(invocation.getParameter(0)).andReturn(obj);
		replay();
		Method method = TestService.class.getMethod("getMyList", Object.class);
		CacheableDefinitionParser parser = registry.getService(CacheableDefinitionParser.class);
		String key = parser.parseAsKey(invocation, method);
		Assert.assertEquals(key, "corner.cache.services.impl.CacheIntegration2Test$TestMember._list_v0.48d8c64495f376ab8a58ac66331cad97ab1798a5");
		verify();
	}
	public static interface TestService{
		@Cacheable(clazz=TestMember.class)
		public List<TestMember> getList(@CacheKeyParameter TestMember testMember);
		@Cacheable(clazz=TestMember.class)
		public List<TestMember> getMyList(@CacheKeyParameter Object testMember);
	}
	public static class TestServiceImpl implements TestService{
		@Override
		public List<TestMember> getList(TestMember testMember) {
			return null;
		}
		@Override
		public List<TestMember> getMyList(Object testMember) {
			return null;
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
		 public static void contributeValueEncoderSource(MappedConfiguration<Class, ValueEncoderFactory> configuration,final EntityService entityService){
			 Class<TestMember> entityClass = TestMember.class;
			 ValueEncoderFactory factory = new ValueEncoderFactory()
	            {
	                public ValueEncoder create(Class type)
	                {
	                    return new ValueEncoder<TestMember>(){

							@Override
							public String toClient(TestMember value) {
								if(value.getId()!=null){
									return value.getId().toString();
								}
								return String.valueOf(value);
							}

							@Override
							public TestMember toValue(String clientValue) {
								return entityService.get(TestMember.class,Long.parseLong(clientValue));
							}};
	                }
	            };

	            configuration.add(entityClass, factory);
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
}
