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
package corner.tapestry.components;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.test.PageTester;
import org.apache.tapestry5.test.TapestryTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import corner.asset.StaticAssetSymbols;

/**
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class StaticLinkTest extends TapestryTestCase{
	private Logger logger = LoggerFactory.getLogger(StaticLinkTest.class);
	@Test
	public void test_render(){
		PageTester tester = new PageTester("corner.integration.app1","App","src/test/app1");
		Document doc = tester.renderPage("StaticLinkDemo");
		logger.debug(doc.toString());
		assertEquals("/foo/img/src.jpg",doc.getElementById("link1").getAttribute("src"));
		assertEquals("/foo/test/has_version.jpg",doc.getElementById("link2").getAttribute("href"));
		tester.shutdown();
		
	}
	@Test
	public void test_render_domain_static(){
		PageTester tester = new PageTester("corner.integration.app1","App","src/test/app1",DomainAssetModule.class);
		Document doc = tester.renderPage("StaticLinkDemo");
		assertEquals("http://test.web.server/img/src.jpg",doc.getElementById("link1").getAttribute("src"));
		tester.shutdown();
	}
	
	public static class DomainAssetModule{
		public static void contributeApplicationDefaults(
				MappedConfiguration<String, String> configuration) {
			configuration.add(StaticAssetSymbols.DOMAIN_ASSET_MODE, "true");
			configuration.add(StaticAssetSymbols.DOMAIN_NAME, "http://test.web.server");
		}
	}

}
