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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corner.cache.annotations.CacheNsParameter;
import corner.cache.model.CacheEvent;
import corner.cache.model.Operation;
import corner.cache.services.CacheManager;
import corner.cache.services.NamespaceProcessor;

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
	private NamespaceProcessor processor;
	
	public DefaultListCacheStrategyImpl(NamespaceProcessor processor) {
		this.processor = processor;
	}

	@Override
	public <T> void dealCacheEvent(CacheEvent<T> event, CacheManager cacheManager) {
		if(!contains(event.getTargetClass())){
			return;
		}
		//增加或者删除
		if(event.getOperation() == Operation.INSERT||event.getOperation() == Operation.DELETE){
    		//得到namespace的版本
			processor.upgradeNamespaceVersion(cacheManager,event,getNamespaces(event.getTargetClass()));
		}
	}

	@Override
	public String appendNamespace(CacheManager cacheManager,Class targetClass,
			CacheNsParameter[] nses, String key, Object... objects) {
		add(targetClass,nses);
		
		String fullNamespaceValue = processor.getNamespaceNameVersioned(cacheManager,targetClass,nses,objects);
		StringBuilder sb = new StringBuilder();
		sb.append(fullNamespaceValue);
		sb.append(key);
		logger.debug("full cache key :{}" ,sb);
		return sb.toString();
	}
}
