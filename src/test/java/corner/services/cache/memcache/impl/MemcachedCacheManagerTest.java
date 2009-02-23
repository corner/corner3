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
package corner.services.cache.memcache.impl;

import static org.testng.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.persistence.Column;


import org.apache.tapestry5.json.JSONObject;
import org.hibernate.annotations.Type;
import org.testng.annotations.Test;

import corner.services.cache.Cache;
import corner.services.cache.memcache.impl.ErrorHandlerImpl;
import corner.services.cache.memcache.impl.MemcacheConfig;
import corner.services.cache.memcache.impl.MemcachedCacheManager;
import corner.services.config.impl.JAXBUtil;


/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class MemcachedCacheManagerTest {

	@Test
	public void test_write_get() {
		try {
			InputStream in = this.getClass()
					.getResourceAsStream("memcache.xml");
			InputStreamReader reader = new InputStreamReader(in);
			MemcachedCacheManager manager = new MemcachedCacheManager(JAXBUtil
					.load(reader, MemcacheConfig.class),
					org.slf4j.LoggerFactory
							.getLogger(MemcachedCacheManager.class),
					new ErrorHandlerImpl());
			manager.start();
			Cache cache = manager.getCache("test");
			assertNotNull(cache);
			assertNull(manager.getCache("test2"));
			String key = "_aa"+System.currentTimeMillis();
			A v = new B();
			v.setUrl("http://aa");
			A o = (A)cache.get(key);
			assertNull(o);
			cache.put(key, v);
			o = (A)cache.get(key);
			assertNotNull(o);
			assertEquals(o.getUrl(),v.getUrl());
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public static class A implements java.io.Serializable {
		/** 图片的地址 * */
		private String url;
		/** 图片的相关属性 * */
		private JSONObject meta;

		@Column(nullable = false)
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		@Type(type = "corner.hibernate.JSONStringType")
		public JSONObject getMeta() {
			return meta;
		}

		public void setMeta(JSONObject meta) {
			this.meta = meta;
		}
		private static final long serialVersionUID = 1L;
	}
	
	public static class B extends A{
		
	}
}
