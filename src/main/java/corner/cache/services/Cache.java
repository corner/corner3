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
 * 提供Cache服务的接口
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public interface Cache<K, V> {
	/**
	 * 取得缓存的名字
	 * 
	 * @return
	 * @since 0.0.2
	 */
	public String getName();

	/**
	 * 将value对象以key值放入到Cache中
	 * 
	 * @param key
	 *            对象的key值
	 * @param value
	 *            要被保存的值
	 * @since 0.0.2
	 */
	boolean put(K key, V value);

	/**
	 * 将value对象以key值放入到Cache中,在cache中过期时间为ttl
	 * 
	 * @param key
	 *            对象的key值
	 * @param value
	 *            要被保存的值
	 * @param ttl
	 *            过期时间,单位秒
	 * @since 0.0.2
	 */
	boolean put(K key, V value, int ttl);

	/**
	 * 获取以key为键值的对象
	 * 
	 * @param key
	 * @return
	 * @since 0.0.2
	 */
	V get(K key);

	/**
	 * 删除以key为键值的对象
	 * 
	 * @param key
	 * @since 0.0.2
	 */
	boolean remove(K key);

	/**
	 * 清空缓存
	 * 
	 * @since 0.0.2
	 */
	boolean clear();
	/**
	 * 根据某一个键值进行递增操作
	 * @param key 键
	 * @param delta 需要递增
	 * @return
	 * @since 3.1
	 */
	Long	 increment(String key, long delta) ;
}
