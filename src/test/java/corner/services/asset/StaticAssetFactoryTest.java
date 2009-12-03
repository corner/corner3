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
package corner.services.asset;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.test.TapestryTestCase;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.asset.StaticAsseUrlFactory;
import corner.asset.StaticAssetFactory;

/**
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 2088 $
 * @since 0.0.2
 */
public class StaticAssetFactoryTest extends TapestryTestCase{

	@Test
	public void test_http_staticAsset(){
		Request request = this.newMock(Request.class);
		StaticAsseUrlFactory factory= this.newMock(StaticAsseUrlFactory.class);
		Resource resource = mockResource();
		EasyMock.expect(request.getContextPath()).andReturn("app").anyTimes();
		EasyMock.expect(request.getPath()).andReturn("/").anyTimes();
		EasyMock.expect(resource.getPath()).andReturn("test.gif").anyTimes();
		EasyMock.expect(factory.getUrl("app","test.gif","")).andReturn("http://img.test.com/app/test.gif").anyTimes();
		EasyMock.expect(factory.getUrl("","test.gif","")).andReturn("http://img.test.com/test.gif").anyTimes();
		Request request2 = this.newMock(Request.class);
		EasyMock.expect(request2.getContextPath()).andReturn("").anyTimes();
		EasyMock.expect(request2.getPath()).andReturn("/").anyTimes();
		replay();
		StaticAssetFactory assetFactory =new StaticAssetFactory(request, factory);
		Asset asset = assetFactory.createAsset(resource);
		assertEquals("http://img.test.com/app/test.gif", asset.toClientURL());
		
		assetFactory =new StaticAssetFactory(request2, factory);
		asset = assetFactory.createAsset(resource);
		assertEquals("http://img.test.com/test.gif", asset.toClientURL());
		verify();
		
	}
	@Test
	public void test_local_staticAsset(){
		Request request = this.newMock(Request.class);
		StaticAsseUrlFactory factory=this.newMock(StaticAsseUrlFactory.class);
		Resource resource = mockResource();
		EasyMock.expect(request.getContextPath()).andReturn("app").anyTimes();
		EasyMock.expect(request.getPath()).andReturn("/").anyTimes();
		EasyMock.expect(resource.getPath()).andReturn("test.gif").anyTimes();
		EasyMock.expect(factory.getUrl("app","test.gif","")).andReturn("/app/test.gif").anyTimes();
		EasyMock.expect(factory.getUrl("","test.gif","")).andReturn("/test.gif").anyTimes();
		Request request2 = this.newMock(Request.class);
		EasyMock.expect(request2.getContextPath()).andReturn("").anyTimes();
		EasyMock.expect(request2.getPath()).andReturn("/").anyTimes();
		
		replay();
		StaticAssetFactory assetFactory =new StaticAssetFactory(request, factory);
		Asset asset = assetFactory.createAsset(resource);
		assertEquals("/app/test.gif", asset.toClientURL());
		
		assetFactory =new StaticAssetFactory(request2, factory);
		asset = assetFactory.createAsset(resource);
		assertEquals("/test.gif", asset.toClientURL());
		
		verify();
	}
}
