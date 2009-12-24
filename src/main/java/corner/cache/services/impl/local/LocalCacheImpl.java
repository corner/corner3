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
package corner.cache.services.impl.local;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import corner.cache.services.Cache;

/**
 * Cache的本地实现,源自阿里巴巴的开源实现,根据我们自己的需要做了一些调整
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class LocalCacheImpl implements Cache<String, Object> {
	private static final Log logger = LogFactory.getLog(LocalCacheImpl.class);

	private ConcurrentHashMap<String, Object> cache;
	private ConcurrentHashMap<String, Long> expiryCache;

	private ScheduledExecutorService scheduleService;

	private final String name;
	private final String _toString;
	private int expiryInterval;

	public LocalCacheImpl(String name) {
		this(name, 10);
	}

	public LocalCacheImpl(String name, int expiryInterval) {
		this.expiryInterval = expiryInterval;
		this.name = name;
		this._toString = "[TTL]" + this.name;
		if (this.expiryInterval <= 0) {
			this.expiryInterval = 10;
		}
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		cache = new ConcurrentHashMap();

		expiryCache = new ConcurrentHashMap<String, Long>();

		scheduleService = Executors.newScheduledThreadPool(1);

		scheduleService.scheduleAtFixedRate(new CheckOutOfDateSchedule(cache,
				expiryCache), 0, expiryInterval * 60, TimeUnit.SECONDS);

		if (logger.isInfoEnabled())
			logger.info("DefaultCache CheckService is start!");
	}

	@Override
	public boolean clear() {
		if (cache != null) {
			cache.clear();
		}

		if (expiryCache != null)
			expiryCache.clear();

		return true;
	}

	public boolean containsKey(String key) {
		if (!checkValidate(key)) {
			return cache.containsKey(key);
		} else {
			return false;
		}
	}

	public Object get(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("get key:" + key);
		}
		if (!checkValidate(key)) {
			return cache.get(key);
		} else {
			return null;
		}
	}
	@Override
	public Long increment(String key, long delta) {
		Object obj = get(key);
		if(obj==null){
			return null;
		}else{
			long v = Long.parseLong(String.valueOf(obj))+delta;
			put(key,v);
			return v;
		}
	}
	public Set<String> keySet() {
		checkAll();
		return expiryCache.keySet();
	}

	public boolean put(String key, Object value) {
		if (logger.isDebugEnabled()) {
			logger.debug("put key:" + key);
		}
		cache.put(key, value);
		expiryCache.put(key, (long) -1);
		return true;
	}

	public Object put(String key, Object value, Date expiry) {
		if (logger.isDebugEnabled()) {
			logger.debug("put key:" + key);
		}
		cache.put(key, value);
		expiryCache.put(key, expiry.getTime());

		return value;
	}

	public boolean remove(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("remove key:" + key);
		}
		cache.remove(key);
		expiryCache.remove(key);
		return true;
	}

	public int size() {
		checkAll();

		return expiryCache.size();
	}

	public Collection<Object> values() {
		checkAll();
		Collection<Object> values = new ArrayList<Object>();
		values.addAll(cache.values());
		return values;
	}

	private boolean checkValidate(String key) {
		if (expiryCache.get(key) != null && expiryCache.get(key) != -1
				&& new Date(expiryCache.get(key)).before(new Date())) {
			cache.remove(key);
			expiryCache.remove(key);
			return true;
		}
		return false;
	}

	private void checkAll() {
		Iterator<String> iter = expiryCache.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			checkValidate(key);
		}
	}

	class CheckOutOfDateSchedule implements java.lang.Runnable {
		ConcurrentHashMap<String, Object> cache;
		ConcurrentHashMap<String, Long> expiryCache;

		public CheckOutOfDateSchedule(ConcurrentHashMap<String, Object> cache,
				ConcurrentHashMap<String, Long> expiryCache) {
			this.cache = cache;
			this.expiryCache = expiryCache;
		}

		public void run() {
			check();
		}

		public void check() {
			try {
				Iterator<String> keys = cache.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();

					if (expiryCache.get(key) == null)
						continue;

					long date = expiryCache.get(key);

					if ((date > 0) && (new Date(date).before(new Date()))) {
						expiryCache.remove(key);
						cache.remove(key);
					}

				}
			} catch (Exception ex) {
				logger.info("DefaultCache CheckService is start!");
			}
		}

	}

	public String getName() {
		return name;
	}

	public int getExpiryInterval() {
		return expiryInterval;
	}

	public boolean put(String key, Object value, int TTL) {
		cache.put(key, value);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, TTL);
		expiryCache.put(key, calendar.getTime().getTime());
		return true;
	}

	public void destroy() {
		try {
			if (scheduleService != null)
				scheduleService.shutdown();

			scheduleService = null;
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	@Override
	public String toString() {
		return this._toString;
	}



	

}
