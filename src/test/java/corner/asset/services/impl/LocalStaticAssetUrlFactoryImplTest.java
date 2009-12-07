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
package corner.asset.services.impl;

import org.apache.tapestry5.test.TapestryTestCase;
import org.slf4j.Logger;
import org.testng.annotations.Test;

import corner.asset.StaticAssetConstants;

/**
 * @author dong
 * @version $Revision: 2088 $
 * @since 0.0.2
 */
public class LocalStaticAssetUrlFactoryImplTest extends TapestryTestCase {
	@Test
	public void test_local_index(){
		Logger logger = this.mockLogger();
		stub_isDebugEnabled(logger,false);
		replay();
		LocalStaticAssetUrlCreatorImpl urlfactory = new LocalStaticAssetUrlCreatorImpl(logger);
		String url = urlfactory.createUrl("/app", StaticAssetConstants.DEFAULT_ASSET_TYPE,"/../images/a.gif", "uesr/login");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"images/a.gif", "Index");
		assertEquals(url,"/images/a.gif");
		url = urlfactory.createUrl("app", StaticAssetConstants.DEFAULT_ASSET_TYPE, "images/a.gif", "Index");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("app",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"../images/a.gif", "Index");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("app", StaticAssetConstants.DEFAULT_ASSET_TYPE, "../../images/a.gif", "Index");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("/app", StaticAssetConstants.DEFAULT_ASSET_TYPE, ".././images/a.gif", "Index");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("/app",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"/images/a.gif", "Index");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("/app",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"/images/a.gif", "uesr/login");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("/app",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"/images/a.gif", "uesr/list/view");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("/app",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"/../../../images/a.gif", "user/list/view");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("/app",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"../images/a.gif", "user/list/view");
		assertEquals(url,"/app/user/images/a.gif");
		url = urlfactory.createUrl("/app", StaticAssetConstants.DEFAULT_ASSET_TYPE, "../images/a.gif", "user");
		assertEquals(url,"/app/images/a.gif");
		url = urlfactory.createUrl("", StaticAssetConstants.DEFAULT_ASSET_TYPE, "../images/a.gif", "user");
		assertEquals(url,"/images/a.gif");
		url = urlfactory.createUrl("",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"/home/dong/a.txt", "home/dong/a");
		assertEquals(url,"/home/dong/a.txt");
		url = urlfactory.createUrl("", StaticAssetConstants.DEFAULT_ASSET_TYPE, "/home/../dong/a.txt", "home");
		assertEquals(url,"/dong/a.txt");
		url = urlfactory.createUrl("", StaticAssetConstants.DEFAULT_ASSET_TYPE, "../dong/a.txt", "home/dong/aaa");
		assertEquals(url,"/home/dong/a.txt");
		url = urlfactory.createUrl("", StaticAssetConstants.DEFAULT_ASSET_TYPE, "/home/../dong/a.txt", "home/dong/aaa");
		assertEquals(url,"/dong/a.txt");
		url = urlfactory.createUrl("",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"../dong/a.txt", "home/dong/aaa");
		assertEquals(url,"/home/dong/a.txt");
		url = urlfactory.createUrl("",  StaticAssetConstants.DEFAULT_ASSET_TYPE,"/home/../dong/a.txt", "");
		assertEquals(url,"/dong/a.txt");
	}
}
