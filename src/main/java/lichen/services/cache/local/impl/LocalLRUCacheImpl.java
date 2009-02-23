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

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lichen.services.cache.Cache;

/**
 * 使用LRU策略的Cache实现
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class LocalLRUCacheImpl implements Cache<String, Object> {
	private final LRUMap lruMap;
	private final String name;

	/**
	 * 
	 * @param name
	 *            Cache的名字
	 * @param maxSize
	 *            Cache中缓存的最大个数
	 */
	public LocalLRUCacheImpl(String name, int maxSize) {
		this.lruMap = new LRUMap(maxSize);
		this.name = name;
	}

	@Override
	public boolean clear() {
		this.lruMap.clear();
		return true;
	}

	@Override
	public Object get(String key) {
		return this.lruMap.get(key);
	}

	@Override
	public boolean put(String key, Object value) {
		this.lruMap.put(key, value);
		return true;
	}

	@Override
	public boolean put(String key, Object value, int ttl) {
		this.lruMap.put(key, value);
		return true;
	}

	@Override
	public boolean remove(String key) {
		this.lruMap.remove(key);
		return true;
	}

	@Override
	public String getName() {
		return "[LRU] " + this.name;
	}

	private static final class LRUMap extends LinkedHashMap implements
			java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private final Lock lock = new ReentrantLock();
		private int maxSize = 0;

		public LRUMap(int maxSize) {
			super(maxSize / 5, 0.75f, true);
			this.maxSize = maxSize;
		}

		/**
		 * @see java.util.LinkedHashMap#clear()
		 */
		@Override
		public void clear() {
			try {
				lock.lock();
				super.clear();
			} finally {
				lock.unlock();
			}
		}

		/**
		 * @see java.util.LinkedHashMap#get(java.lang.Object)
		 */
		@Override
		public Object get(Object key) {
			try {
				lock.lock();
				return super.get(key);
			} finally {
				lock.unlock();
			}
		}

		/**
		 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Object put(Object key, Object value) {
			try {
				lock.lock();
				return super.put(key, value);
			} finally {
				lock.unlock();
			}
		}

		/**
		 * @see java.util.HashMap#remove(java.lang.Object)
		 */
		@Override
		public Object remove(Object key) {
			try {
				lock.lock();
				return super.remove(key);
			} finally {
				lock.unlock();
			}
		}

		/**
		 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
		 */
		@Override
		protected boolean removeEldestEntry(Entry eldest) {
			return this.size() > this.maxSize;
		}

	}

}
