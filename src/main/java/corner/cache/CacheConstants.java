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
package corner.cache;

/**
 * 常量定义
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheConstants {
	
	/**
	 * 得到namespace的定义
	 * common list namespace
	 */
	public static final String COMMON_LIST_KEY_NAMESPACE_FORMATE = "%s_c_l_ns";
	public static final String COMMON_LIST_STRATEGY = "c_l";
	public static final String ENTITY_CACHE_KEY_FORMAT= "%s{%s}";
	
	public static final String COMMON_LIST_NAMESPACE="_list";
	/**
	 * 实体cache的定义key
	 */
	public static String ENTITY_CACHE_NAME="entity";
	/**
	 * 实体namespace缓存区名称
	 */
	public static String ENTITY_NS_CACHE_NAME="ns";
}
