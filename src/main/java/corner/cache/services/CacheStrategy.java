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
package corner.cache.services;

import corner.cache.annotations.CacheNsParameter;
import corner.cache.model.CacheEvent;

/**
 * 缓存策略接口
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface CacheStrategy {
	/**
	 * 把缓存的key加上namespace标记，
	 * 如果nses为空的，那么则使用默认的c_list标记
	 * @param cacheManager 缓存管理器
	 * @param clazz 缓存的类
	 * @param nses 命名空间定义
	 * @param key 缓存定义的key
	 * @param objects 参数，通常为 {@link corner.cache.annotations.CacheKeyParameter} 定义的参数变量
	 * @return 加上namespace后的完整key
	 * @since 3.1
	 */
	String appendNamespace(CacheManager cacheManager, Class<?> clazz, CacheNsParameter[] nses,
			String key, Object... objects);
	/**
	 * 处理缓存事件
	 * @param event 事件
	 * @param cacheManager cacheManager instance
	 * @since 3.1
	 */
	public <T>void dealCacheEvent(CacheEvent<T> event,CacheManager cacheManager);
}
