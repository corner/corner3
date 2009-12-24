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
 * 负责管理以及初始化Cache相关资源的接口
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public interface CacheManager {

	/**
	 * 通过给定的名称，来得到对应的Cache对象
	 * @param <K> Cache对象中的Key值。
	 * @param <V> Cache对象中的Value值
	 * @param name 名称
	 * @return Cache 对象
	 * @since 0.0.2
	 * @see Cache
	 */
	public  Cache<String,Object> getCache(String name);

	/**
	 * 启动Cache服务
	 * 
	 * @since 0.0.2
	 */
	public void start();

	/**
	 * 关闭Cache服务
	 * 
	 * @since 0.0.2
	 */
	public void stop();

}
