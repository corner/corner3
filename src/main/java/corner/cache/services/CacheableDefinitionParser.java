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

import org.apache.tapestry5.ioc.Invocation;


/**
 * cache definition parser
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface CacheableDefinitionParser {

	/**
	 * 针对cache定义来获取一个用于缓存的key
	 * @param invocation 方法的执行invocation
	 * @param method 方法对象
	 * @return 缓存的key
	 * @since 3.1
	 */
	public abstract String parseAsKey(Invocation invocation, Method method);

}