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
package corner.cache.services.impl;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import corner.cache.CacheConstants;
import corner.cache.annotations.CacheNsParameter;
import corner.cache.services.CacheStrategy;

/**
 * 抽象的策略类
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public abstract class AbstractCacheStrategy implements CacheStrategy{
	/** 要处理的实体类**/
	private ConcurrentHashMap <Class,CopyOnWriteArraySet<String>> cacheKeyDefines=new ConcurrentHashMap<Class,CopyOnWriteArraySet<String>>();
	/**
	 * 是否包含某一实体类
	 * @param targetClass 实体类
	 * @return 是否包含
	 * @since 3.1
	 */
	protected boolean contains(Class  targetClass) {
		return cacheKeyDefines.containsKey(targetClass);
	}
	protected Iterator<String> getNamespaces(Class targetClass){
		return cacheKeyDefines.get(targetClass).iterator();
	}
	/**
	 * 增加一个处理的实体类
	 * @param targetClass 实体类
	 * @param nses 
	 * @return 是否增加成功
	 * @since 3.1
	 */
	protected boolean add(Class  targetClass, CacheNsParameter[] nses) {
		CopyOnWriteArraySet<String> keys = null;
		if(contains(targetClass)){
			keys = cacheKeyDefines.get(targetClass);
		}else{
    		synchronized(cacheKeyDefines){//锁定类,保证并发的时候，能够正确加入类
    			if(!contains(targetClass)){
    				keys = new CopyOnWriteArraySet<String>();
    				cacheKeyDefines.put(targetClass, keys);
    			}else{
    				keys = cacheKeyDefines.get(targetClass);
    			}
    		}
		}
		//增加默认的缓存namespace
		keys.add(CacheConstants.COMMON_LIST_NAMESPACE);
		for(CacheNsParameter parameter:nses){
			keys.add(parameter.name());
		}
		return true;
	}
}
