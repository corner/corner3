package corner.cache.services.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.ioc.internal.services.PropertyAccessImpl;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.test.TapestryTestCase;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.cache.CacheConstants;
import corner.cache.annotations.Cacheable;
import corner.cache.model.CacheEvent;
import corner.cache.model.Operation;
import corner.cache.services.Cache;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheProcessor;
import corner.cache.services.CacheProcessorSource;
import corner.cache.services.CacheStrategy;
import corner.cache.services.CacheStrategySource;
import corner.cache.services.impl.local.LocalCacheConfig;
import corner.cache.services.impl.local.LocalCacheItemConfig;
import corner.cache.services.impl.local.LocalCacheManagerImpl;
import corner.integration.app1.entities.TestA;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;

public class CacheableAdviceTest extends TapestryTestCase{
	PaginationOptions options = new PaginationOptions();
	@Cacheable(clazz=TestA.class)
	public PaginationList<TestA> getList(){
		List<TestA> list = new ArrayList<TestA>();
		TestA a1 = new TestA();
		list.add(a1);
		TestA a2 = new TestA();
		list.add(a2);
	
		options.setPage(1);
		options.setPerPage(10);
		options.setTotalRecord(100);
		PaginationList<TestA> pl = new PaginationList(list,options);
		return pl;
	}
	@Test
	public void test_cache() throws SecurityException, NoSuchMethodException{
		ValueEncoderSource valueEncoderSource=newMock(ValueEncoderSource.class);
		
		LocalCacheConfig config = new LocalCacheConfig();
		List<LocalCacheItemConfig> caches = new ArrayList<LocalCacheItemConfig>();
		LocalCacheItemConfig nsCacheDefine = new LocalCacheItemConfig();
		nsCacheDefine.setName("ns");
		nsCacheDefine.setExpiryInterval(10000);
		caches.add(nsCacheDefine);
		LocalCacheItemConfig entityCacheDefine = new LocalCacheItemConfig();
		entityCacheDefine.setName("entity");
		entityCacheDefine.setExpiryInterval(10000);
		caches.add(entityCacheDefine);
		
		config.setCache(caches);
		
		CacheManager cacheManager = new LocalCacheManagerImpl(config);
		cacheManager.start();
		
		Invocation invocation = newMock(Invocation.class);
		invocation.proceed();
		invocation.proceed();
		final PaginationList<TestA> result = getList();
		expect(invocation.getResult()).andReturn(result).times(2);
		expect(invocation.getResultType()).andReturn(PaginationList.class).times(3);
		Map<String,CacheStrategy> configuration = new HashMap<String,CacheStrategy>();
		configuration.put(CacheConstants.COMMON_LIST_STRATEGY, new DefaultListCacheStrategyImpl());
		CacheStrategySource source = new CacheStrategySourceImpl(cacheManager,configuration);
		CacheableDefinitionParserImpl parser = new CacheableDefinitionParserImpl(valueEncoderSource,cacheManager,source);
		EntityService entityService = newMock(EntityService.class);
		Class clazz = TestA.class;
		expect(entityService.getEntityClass(EasyMock.anyObject())).andReturn(clazz).anyTimes();
		TypeCoercer coercer=newMock(TypeCoercer.class);
		expect(coercer.coerce(result, Iterable.class)).andReturn(new Iterable(){

			@Override
			public Iterator iterator() {
				return ((List<TestA>) result.collectionObject()).iterator();
			}}).times(2);
		
		ValueEncoder<PaginationOptions> encoder = newMock(ValueEncoder.class);
		expect(encoder.toClient(options)).andReturn("10,2,1").times(2);
		expect(encoder.toValue("10,2,1")).andReturn(options).times(3);
		expect(valueEncoderSource.getValueEncoder(PaginationOptions.class)).andReturn(encoder).times(5);
		invocation.overrideResult(EasyMock.anyObject());
		invocation.overrideResult(EasyMock.anyObject());
		invocation.overrideResult(EasyMock.anyObject());
		replay();
		Method method = CacheableAdviceTest.class.getMethod("getList");
		PropertyAccess propertyAccess = new PropertyAccessImpl();
		Map<Class,CacheProcessor> sourceConfig = new HashMap<Class,CacheProcessor>();
		sourceConfig.put(PaginationList.class,new PaginationListCacheProcessor(coercer, entityService, propertyAccess, valueEncoderSource));
		sourceConfig.put(Iterator.class,new IteratorCacheProcessor(entityService, propertyAccess, coercer));
		CacheProcessorSource cacheProcessorSource = new CacheProcessorSourceImpl(sourceConfig, propertyAccess, coercer, entityService);
		CacheableAdvice advice  =new CacheableAdvice(method, cacheManager, parser,cacheProcessorSource);
		
		Cache<String, Object> entityCache = cacheManager.getCache("entity");
		Cache<String, Object> nsCache = cacheManager.getCache("ns");
		//第一次执行
		advice.advise(invocation);
		//namespace 为 0
		assertEquals(nsCache.get("corner.integration.app1.entities.TestA_c_l_ns"),0L);
		assertNotNull(entityCache.get("corner.integration.app1.entities.TestA_c_l_ns_0_4557b623be2a2da3aa45856123e6e8163323bc45#"));
		assertNull(entityCache.get("corner.integration.app1.entities.TestA_c_l_ns_1_4557b623be2a2da3aa45856123e6e8163323bc45#"));
		//第二次从缓存读取
		advice.advise(invocation);
		//发生插入事件
		CacheEvent<TestA> event = new CacheEvent<TestA>(TestA.class,null,Operation.INSERT);
		source.catchEvent(event);
		assertEquals(nsCache.get("corner.integration.app1.entities.TestA_c_l_ns"),1L);
		//再次执行方法
		advice.advise(invocation);
		assertEquals(nsCache.get("corner.integration.app1.entities.TestA_c_l_ns"),1L);
		assertNotNull(entityCache.get("corner.integration.app1.entities.TestA_c_l_ns_1_4557b623be2a2da3aa45856123e6e8163323bc45#"));
		verify();
	}
}
