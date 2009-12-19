package corner.cache.services.impl;

import java.lang.reflect.Method;

import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.test.TapestryTestCase;
import org.testng.annotations.Test;

import corner.cache.annotations.Cacheable;
import corner.integration.app1.entities.TestA;

public class CacheableDefinitionParserTest extends TapestryTestCase{

	@Cacheable
	public TestA getMember(){
		return null;
	}
	@Test
	public void test_parse() throws SecurityException, NoSuchMethodException{
		ValueEncoderSource valueEncoderSource=newMock(ValueEncoderSource.class);
		Invocation invocation = newMock(Invocation.class);
		CacheableDefinitionParser parser = new CacheableDefinitionParser(valueEncoderSource);
		replay();
		Method method = CacheableDefinitionParserTest.class.getMethod("getMember",null);
		parser.parse(invocation, method);
		verify();
	}
}
