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

import corner.cache.services.Cache;

/**
 * java cache implemention
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public abstract class JavaCacheImpl<K,V> implements Cache<K,V>{
	private javax.cache.Cache delegateCache;
	private String name;

	public JavaCacheImpl(javax.cache.Cache delegateCache,String name){
		this.delegateCache = delegateCache;
		this.name =name;
	}

	@Override
	public boolean clear() {
		delegateCache.clear();
		return true;
	}

	@Override
	public V get(K key) {
		return (V) delegateCache.get(key);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean put(K key, V value) {
		delegateCache.put(key, value);
		return true;
	}

	@Override
	public boolean put(K key, V value, int ttl) {
		delegateCache.put(key, value);
		return true;
	}

	@Override
	public boolean remove(K key) {
		delegateCache.remove(key);
		return true;
	}
}
