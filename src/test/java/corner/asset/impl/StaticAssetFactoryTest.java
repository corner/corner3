package corner.asset.impl;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.internal.services.ContextResource;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.test.TapestryTestCase;
import org.testng.annotations.Test;

import corner.asset.StaticAssetUrlCreator;

public class StaticAssetFactoryTest extends TapestryTestCase{
	@Test
	public void test_resource(){
		StaticAssetUrlCreator urlCreator = newMock(StaticAssetUrlCreator.class);
		Request request = this.mockRequest();
        Context context = mockContext();
        expect(request.getContextPath()).andReturn("/context");
        expect(urlCreator.createUrl("/context", null, "foo/Bar.txt", "")).andReturn("/context/foo/Bar.txt");
        replay();
        
		StaticAssetFactory factory = new StaticAssetFactory(request,urlCreator);
        Resource r = new ContextResource(context, "foo/Bar.txt");
		Asset asset = factory.createAsset(r);
		assertSame(asset.getResource(), r);
	    assertEquals(asset.toClientURL(), "/context/foo/Bar.txt");
	    
	    verify();
	}
}
