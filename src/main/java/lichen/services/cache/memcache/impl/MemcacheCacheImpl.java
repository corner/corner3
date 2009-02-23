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
package lichen.services.cache.memcache.impl;

import com.meetup.memcached.MemCachedClient;

import lichen.services.cache.Cache;

/**
 * 基于Memcache实现的Cache
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class MemcacheCacheImpl<V> implements Cache<String, V> {
	private final MemCachedClient client;
	private final boolean supportClear;
	private final String name;
	private final String _toString;

	public MemcacheCacheImpl(String name, MemCachedClient client,
			boolean supportClear) {
		this.name = name;
		this.client = client;
		this.supportClear = supportClear;
		this._toString = "[Memcached]" + this.name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @see lichen.services.cache.Cache#get(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(String key) {
		return (V) client.get(key);
	}

	/**
	 * @see lichen.services.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean put(String key, V value) {
		return client.set(key, value);
	}

	/**
	 * @see lichen.services.cache.Cache#put(java.lang.Object, java.lang.Object,
	 *      int)
	 */
	@Override
	public boolean put(String key, V value, int ttl) {
		return client.set(key, value, new java.util.Date(System
				.currentTimeMillis()
				+ ttl * 1000));
	}

	/**
	 * @see lichen.services.cache.Cache#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(String key) {
		return client.delete(key);
	}

	@Override
	public String toString() {
		return this._toString;
	}

	@Override
	public boolean clear() {
		if (this.supportClear) {
			return this.client.flushAll();
		}
		return false;
	}

}
