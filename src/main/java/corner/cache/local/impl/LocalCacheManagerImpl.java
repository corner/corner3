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
package corner.cache.local.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import corner.cache.Cache;
import corner.cache.CacheManager;


/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class LocalCacheManagerImpl implements CacheManager {
	private final LocalCacheConfig config;
	private final ConcurrentMap<String, LocalCacheImpl> caches = new ConcurrentHashMap<String, LocalCacheImpl>();

	public LocalCacheManagerImpl(LocalCacheConfig config) {
		this.config = config;
	}

	/**
	 * @see corner.cache.CacheManager#getCache(java.lang.String)
	 */
	@Override
	public Cache<String,Object> getCache(String name) {
		return this.caches.get(name);
	}

	/**
	 * @see corner.cache.CacheManager#start()
	 */
	@Override
	public void start() {
		for (LocalCacheItemConfig item : config.getCache()) {
			LocalCacheImpl _localCache = new LocalCacheImpl(item.getName(),
					item.getExpiryInterval());
			caches.put(item.getName(), _localCache);
		}
	}

	/**
	 * @see corner.cache.CacheManager#stop()
	 */
	@Override
	public void stop() {
		for (LocalCacheImpl localCache : this.caches.values()) {
			localCache.clear();
		}
		this.caches.clear();
	}
}
