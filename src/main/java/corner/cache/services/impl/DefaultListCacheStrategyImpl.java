/* 
 * Copyright 2008 The Corner Team.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corner.cache.CacheConstants;
import corner.cache.annotations.Cacheable;
import corner.cache.model.CacheEvent;
import corner.cache.model.Operation;
import corner.cache.services.Cache;
import corner.cache.services.CacheManager;

/**
 * 默认的列表缓存事件处理的实现.
 * 此策略是最简单的缓存策略。
 * 缓存所有ID，但是只要数据有插入和删除操作，则立刻清除本缓存
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class DefaultListCacheStrategyImpl  extends AbstractCacheStrategy{
	private Logger logger = LoggerFactory.getLogger(DefaultListCacheStrategyImpl.class);

	@Override
	public <T> void dealCacheEvent(CacheEvent<T> event, CacheManager cacheManager) {
		if(!contains(event.getTargetClass())){
			return;
		}
			//增加或者删除
			if(event.getOperation() == Operation.INSERT||event.getOperation() == Operation.DELETE){
        		//得到namespace的版本
				String targetClassName = event.getTargetClass().getName();
				String namespace=String.format(CacheConstants.COMMON_LIST_KEY_NAMESPACE_FORMATE, targetClassName);
				getNamespaceValue(cacheManager,targetClassName);
        		Cache nsCache = cacheManager.getCache("ns");
        		nsCache.increment(namespace, 1);
			}
	}

	private Object getNamespaceValue(CacheManager cacheManager,
			String targetClassName) {
		String namespace=String.format(CacheConstants.COMMON_LIST_KEY_NAMESPACE_FORMATE, targetClassName);
		//得到namespace的版本
		Cache nsCache = cacheManager.getCache("ns");
		Object obj = nsCache.get(namespace);
		if(obj == null){
			obj = new Long(0);
			nsCache.put(namespace,obj,360000);
		}
		return obj;
	}
	@Override
	public String appendNamespace( CacheManager cacheManager,Cacheable cacheDefine,String[] keys,Object ... args) {
		//把要处理的类加入到定一种
		add(cacheDefine.clazz());
		Object version = getNamespaceValue(cacheManager,cacheDefine.clazz().getName());
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(CacheConstants.COMMON_LIST_KEY_NAMESPACE_VERSION_FORMATE, cacheDefine.clazz().getName(),version));
		for(String key:keys){
			sb.append(key).append("#");
		}
		logger.debug("full cache key :{}" ,sb);
		return sb.toString();
	}
}
