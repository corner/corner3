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
package corner.orm.gae.impl;

import java.util.Collections;

import javax.cache.CacheException;

import org.apache.tapestry5.ioc.annotations.Marker;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import corner.cache.annotations.Memcache;
import corner.cache.services.Cache;
import corner.cache.services.CacheManager;


/**
 * 基于java.cache.manager方式的缓存实现
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
@Marker(Memcache.class)
public class JavaCacheManagerImpl implements CacheManager {

	private JavaCacheImpl<String, Object> cache;
	private MemcacheService memcacheService;

	public JavaCacheManagerImpl(){
		javax.cache.Cache delegateCache;
		try {
			delegateCache = javax.cache.CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
			memcacheService = MemcacheServiceFactory.getMemcacheService();
			this.cache = new JavaCacheImpl<String,Object>(delegateCache,"entity"){

				/**
				 * @see corner.cache.services.Cache#increment(java.lang.String, long)
				 */
				@Override
				public Long increment(String key, long delta) {
					return memcacheService.increment(key, delta);
				}
			};
		} catch (CacheException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * @see corner.cache.services.CacheManager#getCache(java.lang.String)
	 */
	@Override
	public Cache<String, Object> getCache(String name) {
		return cache;
	}

	/**
	 * @see corner.cache.services.CacheManager#start()
	 */
	@Override
	public void start() {
		//do nothing
	}

	/**
	 * @see corner.cache.services.CacheManager#stop()
	 */
	@Override
	public void stop() {
		//do nothing
	}

}
