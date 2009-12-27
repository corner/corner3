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

/**
 * 所有 方法结果<-->缓存 相互转换的处理
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface CacheProcessorSource {
	/**
	 * 通过需要处理的类，来获取对象的处理器
	 * @param <T> 方法结果
	 * @param clazz 方法结果的类名
	 * @return 处理器
	 * @since 3.1
	 */
	public <T>CacheProcessor<T> getProcessor(Class<T> clazz);
}
