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
package corner.cache.services.impl;

import java.util.Iterator;
import java.util.Map;

import corner.cache.model.CacheEvent;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheStrategy;
import corner.cache.services.CacheStrategySource;

/**
 * cache strategy implemention
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheStrategySourceImpl implements CacheStrategySource {

	private CacheManager cacheManager;
	private Map<String, CacheStrategy> strategies;

	public CacheStrategySourceImpl(CacheManager cacheManager,Map<String,CacheStrategy> configuration) {
		this.cacheManager = cacheManager;
		this.strategies = configuration;
	}

	@Override
	public <T> void catchEvent(CacheEvent<T> event) {
		CacheStrategy strategy;
		Iterator<CacheStrategy> it = strategies.values().iterator();
		while(it.hasNext()){
			strategy = it.next();
			strategy.dealCacheEvent(event, cacheManager);
		}
	}

	@Override
	public CacheStrategy findStrategy(String strategy) {
		return strategies.get(strategy);
	}
}
