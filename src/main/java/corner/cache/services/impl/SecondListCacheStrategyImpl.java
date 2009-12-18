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

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import corner.cache.CacheConstants;
import corner.cache.model.CacheEvent;
import corner.cache.model.Operation;
import corner.cache.services.Cache;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheStrategy;

/**
 * 默认的列表缓存事件处理的实现.
 * 此策略是最简单的缓存策略。
 * 缓存所有ID，但是只要数据有插入和删除操作，则立刻清除本缓存
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class SecondListCacheStrategyImpl<T> implements CacheStrategy<T>{
	//缓存使用的主键
	private Queue<String > keys;
	public SecondListCacheStrategyImpl(){
		this.keys = new ConcurrentLinkedQueue<String>();
	}
	@Override
	public boolean dealCacheEvent(CacheEvent<T> event, CacheManager cacheManager,Object... args) {
			Cache cache = cacheManager.getCache(CacheConstants.ENTITY_CACHE_NAME);
			//增加或者删除
			if(event.getOperation() == Operation.INSERT||event.getOperation() == Operation.DELETE){
				String key;
				while((key=keys.peek())!=null){
					cache.remove(key);
				}
				return true;
			}
		return false;
	}
	public void addKey(String key){
		keys.add(key);
	}
}
