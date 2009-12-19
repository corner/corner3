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
package corner.cache.services;

import corner.cache.model.CacheEvent;

/**
 * 缓存策略
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface CacheStrategy<T> {
	/**
	 * 处理缓存事件
	 * @param event 事件
	 * @param cacheManager cacheManager instance
	 * @param args 事件额外的参数
	 * @return 是否本身要在策略容器中删除，true:删除;false:不删除
	 * @since 3.1
	 */
	public boolean dealCacheEvent(CacheEvent<T> event,CacheManager cacheManager,Object ... args);
}