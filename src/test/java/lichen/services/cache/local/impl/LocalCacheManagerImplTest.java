/* 
 * Copyright 2008 The Lichen Team.
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
package lichen.services.cache.local.impl;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import lichen.services.cache.Cache;

import org.testng.annotations.Test;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class LocalCacheManagerImplTest {
	@Test
	public void test_init_cache() throws Exception {
		LocalCacheConfig config = new LocalCacheConfig();
		List<LocalCacheItemConfig> items = new LinkedList<LocalCacheItemConfig>();
		config.setCache(items);
		for (int i = 0; i < 2; i++) {
			LocalCacheItemConfig _item = new LocalCacheItemConfig();
			_item.setName("name" + i);
			_item.setExpiryInterval(1 * (i+1));
			items.add(_item);
		}
		LocalCacheManagerImpl _manager = new LocalCacheManagerImpl(config);
		_manager.start();
		Cache _cache1 = _manager.getCache("name0");
		Cache _cache2 = _manager.getCache("name1");
		Cache _cache3 = _manager.getCache("name2");
		assertNotNull(_cache1);
		assertNotNull(_cache2);
		assertNull(_cache3);
		assertTrue(_cache1.put("a", "b"));
		assertTrue(_cache1.get("a").equals("b"));
		assertTrue(_cache1.put("a", "b",2));
		assertTrue(_cache1.put("a1", "b"));
		Thread.sleep(3*1000);
		assertNull(_cache1.get("a"));
		assertTrue(_cache1.get("a1").equals("b"));
		
	}
}
