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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import corner.cache.CacheConstants;

/**
 * 缓存的注释类
 * 譬如：缓存某一用户的博客，那么对应的声明如下：
 * <code>
 * @Cacheable(clazz=Blog.class,namespaces={@CacheNsParameter(name="user",keyIndex=1)})
 * public List<Blog> findBlogs(@CacheKeyParameter User user,@CacheKeyParameter int start,@CacheKeyParameter int offset)
 * </code>
 * 1) clazz 			要缓存的实体类
 * 2) namespaces 	 针对此缓存的namespace的定义,其中name="user" 必须为Blog实体的一个属性名称.
 * 
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
	/**
	 *  需要缓存的实体类
	 * @return 实体类
	 * @since 3.1
	 */
	public Class<?> clazz();
	
	/**
	 * 缓存使用的namespace
	 * @return 缓存空间对象集合
	 * @since 3.1
	 */
	public CacheNsParameter [] namespaces() default {};
	/**
	 * 针对key是否匹配的策略类,针对一种类型的缓存通常一个实例即可
	 * @return
	 * @since 3.1
	 */
	public String strategy() default CacheConstants.COMMON_LIST_STRATEGY;
	/**
	 * 缓存使用的自定义参数key，默认为方法名称和参数名称组成的字符串
	 * @return
	 * @since 3.1
	 */
	public String  keyFormat() default ""; 
	
	/**
	 * ttl 时间，单位为秒数
	 * @return
	 * @since 3.1
	 */
	public int ttl() default -1;
}
