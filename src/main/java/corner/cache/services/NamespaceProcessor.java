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

import java.util.Iterator;

import corner.cache.annotations.CacheNsParameter;
import corner.cache.model.CacheEvent;

/**
 * 名称处理
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface NamespaceProcessor {

	/**
	 * 更新namespace的版本号
	 * @param event 事件
	 * @param cacheManager cache manager
	 * @param propertyAccess propertyAccess
	 * @since 3.1
	 */
	void upgradeNamespaceVersion(CacheManager cacheManager,CacheEvent event,Iterator<String> namespaces);
	/**
	 * 根据namespace的定义和参数组成
	 * @param targetClass
	 * @param cacheManager
	 * @param nses
	 * @param objects
	 * @return
	 * @since 3.1
	 */
	String getNamespaceNameVersioned(CacheManager cacheManager,Class targetClass,CacheNsParameter[] nses,Object ... objects);
}
