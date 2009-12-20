package corner.cache.services.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.test.TapestryTestCase;
import org.testng.annotations.Test;

import corner.cache.annotations.CacheKeyParameter;
import corner.cache.annotations.Cacheable;
import corner.cache.services.Cache;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheStrategySource;
import corner.cache.services.impl.local.LocalCacheImpl;
import corner.integration.app1.entities.TestA;

public class CacheableDefinitionParserTest extends TapestryTestCase{

	@Cacheable(clazz=TestA.class,cacheStrategy=DefaultListCacheStrategyImpl.class)
	public List<TestA> getMember(){
		return null;
	}
	@Cacheable(clazz=TestA.class,cacheStrategy=DefaultListCacheStrategyImpl.class,keyFormats={"limit:%s,%s"})
	public List<TestA> getMember(@CacheKeyParameter int start,@CacheKeyParameter int offset,String otherParemter){
		return null;
	}
	@Test
	public void test_parse() throws SecurityException, NoSuchMethodException{
		ValueEncoderSource valueEncoderSource=newMock(ValueEncoderSource.class);
		Invocation invocation = newMock(Invocation.class);
		CacheManager cacheManager = newMock(CacheManager.class);
		CacheStrategySource source = newMock(CacheStrategySource.class);
		CacheableDefinitionParserImpl parser = new CacheableDefinitionParserImpl(valueEncoderSource,cacheManager,source);
		replay();
		Method method = CacheableDefinitionParserTest.class.getMethod("getMember");
		String [] cacheKeys = parser.parseKeys(invocation, method);
		assertEquals(cacheKeys.length,1);
		assertArraysEqual(cacheKeys, new String[]{"b2c2618674e9030156177a6d0f63b3945bc9f968"});
		verify();
	}
	@Test
	public void test_parse_parameter() throws SecurityException, NoSuchMethodException{
		ValueEncoderSource valueEncoderSource=newMock(ValueEncoderSource.class);
		ValueEncoder<Integer> encoder = newMock(ValueEncoder.class);
		expect(encoder.toClient(12)).andReturn("12");
		expect(encoder.toClient(30)).andReturn("30");
		expect(valueEncoderSource.getValueEncoder(int.class)).andReturn(encoder).times(2);
	
		Invocation invocation = newMock(Invocation.class);
		expect(invocation.getParameter(0)).andReturn(12);//start =12
		expect(invocation.getParameter(1)).andReturn(30);//offset =30
		
		CacheManager cacheManager = newMock(CacheManager.class);
		CacheStrategySource source = newMock(CacheStrategySource.class);
		CacheableDefinitionParserImpl parser = new CacheableDefinitionParserImpl(valueEncoderSource,cacheManager,source);
		replay();
		Method method = CacheableDefinitionParserTest.class.getMethod("getMember",int.class,int.class,String.class);
		String [] cacheKeys = parser.parseKeys(invocation, method);
		assertEquals(cacheKeys.length,1);
		assertArraysEqual(cacheKeys, new String[]{"limit:12,30"});
		verify();
	}
	@Test
	public void test_parse_key() throws SecurityException, NoSuchMethodException{
		ValueEncoderSource valueEncoderSource=newMock(ValueEncoderSource.class);
		ValueEncoder<Integer> encoder = newMock(ValueEncoder.class);
		expect(encoder.toClient(12)).andReturn("12");
		expect(encoder.toClient(30)).andReturn("30");
		expect(valueEncoderSource.getValueEncoder(int.class)).andReturn(encoder).times(2);
	
		Invocation invocation = newMock(Invocation.class);
		expect(invocation.getParameter(0)).andReturn(12);//start =12
		expect(invocation.getParameter(1)).andReturn(30);//offset =30
		
		CacheManager cacheManager = newMock(CacheManager.class);
		CacheStrategySource source = newMock(CacheStrategySource.class);
		source.registerStrategyClass(DefaultListCacheStrategyImpl.class);
		CacheableDefinitionParserImpl parser = new CacheableDefinitionParserImpl(valueEncoderSource,cacheManager,source);
		
		Cache cache = new LocalCacheImpl("ns");
		expect(cacheManager.getCache("ns")).andReturn(cache);
				
		replay();
		Method method = CacheableDefinitionParserTest.class.getMethod("getMember",int.class,int.class,String.class);
		String cacheKey = parser.parseAsKey(invocation, method);
		assertEquals(cacheKey,"corner.integration.app1.entities.TestA_c_l_ns_0_limit:12,30#");
		verify();
	}
}
