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

import corner.cache.model.CacheEvent;

/**
 * 所有缓存的处理器，针对缓存事件的发生作出对应的处理
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface CacheStrategySource {
	/**
	 * 捕获缓存事件
	 * @param <T> 操作的实体对象
	 * @param event event
	 * @since 3.1
	 */
	public <T>void catchEvent(CacheEvent<T> event,Object ... args);

	/**
	 * 找到对应的缓存策略
	 * @param strategy
	 * @since 3.1
	 */
	public CacheStrategy findStrategy(String strategy);
}
