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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import corner.asset.StaticAssetUrlDomainSequenceHash;
import corner.asset.StaticAssetUrlFactoryDomainImpl;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class StaticAssetUrlFactoryDomainImplTest {
	@Test
	public void test_domain_index() {
		StaticAssetUrlFactoryDomainImpl _factory = new StaticAssetUrlFactoryDomainImpl(
				"img.eweb", false,null);
		String _url = _factory.getUrl(null, "../images/a.gif", null);
		assertEquals(_url, "http://img.eweb/images/a.gif");

		// 测试泛域名的解析支持
		_factory = new StaticAssetUrlFactoryDomainImpl("img.eweb", true,new StaticAssetUrlDomainSequenceHash(10));
		_url = _factory.getUrl(null, "../images/a.gif", null);
		System.out.println(_url);
		_factory = new StaticAssetUrlFactoryDomainImpl("img.eweb:8081", true,new StaticAssetUrlDomainSequenceHash(10));
		for (int i = 0; i < 10; i++) {
			_url = _factory.getUrl(null, "../images/a.gif", null);
			assertEquals(_url, String.format("http://img%s.eweb:8081/images/a.gif", i));
		}
	}

}
