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
package corner.tapestry.mixins;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.test.PageTester;
import org.apache.tapestry5.test.TapestryTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import corner.tapestry.components.StaticLinkTest;

/**
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class FckeditorTest extends TapestryTestCase{
	private Logger logger = LoggerFactory.getLogger(StaticLinkTest.class);
	@Test
	public void test_render(){
		PageTester tester = new PageTester("corner.integration.app1","App","src/test/app1");
		Document doc = tester.renderPage("FckeditorDemo");
		logger.debug(doc.toString());
		assertNotNull(doc.getElementById("fckContent"));
		assertTrue(doc.toString().contains("Tapestry.init({\"createEditor\":[[\"/foo/foofckeditor/\",\"fckContent\",\"100%\",\"400\",\"corner\"]]});"));
		tester.shutdown();
		
	}
}
