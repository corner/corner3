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

import java.lang.reflect.Method;
import java.util.List;

/**
 * 针对业务方法执行后的同处理
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface CacheProcessor<T> {
	/**
	 * 把执行的结果,转换成List
	 * @param object 执行的方法结果
	 * @return 一个List对象，此对象放入到缓存中
	 * @since 3.1
	 */
    List<Object> toCache(T object);
    /**
     * 把从cache中得到的结果转换成，方法期待的结果
     * @param cacheObject 缓存的对象
     * @param method 待执行的方法
     * @return 方法返回结果
     * @since 3.1
     */
    T fromCache(List<Object> cacheObject, Method method);
}
