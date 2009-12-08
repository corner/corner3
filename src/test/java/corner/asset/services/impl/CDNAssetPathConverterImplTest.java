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
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CDNAssetPathConverterImplTest extends TapestryTestCase{
	@Test
	public void test_convert_context(){
		String domain="assets.ganshane.com";
		boolean supportMutil=true;
		String applicationVersion="1.2";
		StaticAssetUrlDomainHash hash=new StaticAssetUrlDomainSequenceHash(3);;
		CDNAssetPathConverterImpl convert = new CDNAssetPathConverterImpl(domain,supportMutil,applicationVersion,hash);
		String path = "/assets/ctx/1.2/images/test.jpg";
		assertEquals("http://assets0.ganshane.com/images/test.jpg?v=1.2",convert.convertAssetPath(path));
	}

}
