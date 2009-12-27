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
package corner.cache.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存空间的参数
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheNsParameter {
	/**
	 * 参数名称，此名称必须为要缓存Clazz的属性名称,
	 * 也就是说是缓存空间表的字段
	 * @return namespace的名字
	 * @since 3.1
	 */
	String name();
	/**
	 * namespace在方法的CacheKeyParameter定义参数上对应的顺序
	 * 执行方法上的缓存参数顺序值,顺序从 1 开始
	 * <code>
	 * @Cacheable(clazz=Test.class,namespaces={@CacheNsParameter(names="member",keyIndex=1)})
	 * public List<Test> find(@CacheKeyParameter Member member);
	 * @return CacheKeyParameter定义的顺序
	 * @since 3.1
	 */
	int keyIndex();
}
