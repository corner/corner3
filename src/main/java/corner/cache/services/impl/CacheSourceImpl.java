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
package corner.cache.services.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import corner.cache.model.CacheEvent;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheSource;
import corner.cache.services.CacheStrategy;

/**
 * implements CacheSource
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheSourceImpl implements CacheSource{
	
	private ConcurrentMap<Class,CacheStrategy>  strategies = 
		new ConcurrentHashMap<Class,CacheStrategy>();
	private CacheManager cacheManager;

	@Override
	public <T> CacheStrategy<T> getSecondCacheStrategy(Class<T> clazz,String key) {
		CacheStrategy<T> strategy= strategies.get(clazz);
		if(strategy== null){
			strategy = new SecondListCacheStrategyImpl<T>();
		}
		return strategy;
	}
	public <T>void receiveCacheEvent(CacheEvent<T> event,Object ... args){
		Class<? extends CacheEvent> clazz = event.getClass();
		CacheStrategy strategy = strategies.get(clazz);
		if(strategy != null){
			if(strategy.dealCacheEvent(event, cacheManager, args)){
				//删除缓存定义
				strategies.remove(clazz);
			}
		}
	}
}
