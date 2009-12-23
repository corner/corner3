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

import java.util.Iterator;

import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.PropertyAdapter;
import org.apache.tapestry5.services.ValueEncoderSource;

import corner.cache.CacheConstants;
import corner.cache.annotations.CacheNsParameter;
import corner.cache.model.CacheEvent;
import corner.cache.services.Cache;
import corner.cache.services.CacheManager;
import corner.cache.services.NamespaceProcessor;

/**
 * 默认的名称空间处理
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class NamespaceProcessorImpl implements NamespaceProcessor {
	private PropertyAccess propertyAccess;
	private ValueEncoderSource valueEncoderSource;
	public NamespaceProcessorImpl(PropertyAccess propertyAccess,
			ValueEncoderSource valueEncoderSource) {
		this.propertyAccess = propertyAccess;
		this.valueEncoderSource = valueEncoderSource;
	}
	/**
	 * @see corner.cache.services.NamespaceProcessor#getNamespaceNameVersioned(java.lang.Class, corner.cache.services.CacheManager, corner.cache.annotations.CacheNsParameter[], java.lang.Object[])
	 */
	@Override
	public String getNamespaceNameVersioned(CacheManager cacheManager,Class targetClass,
			CacheNsParameter[] nses,
			Object... objects) {
		StringBuilder sb  = new StringBuilder();
		String nsName = null;
		String nsValue =null;
		
		//为默认的缓存列表
		if(nses== null||nses.length == 0){
			nsName= getDefaultListNsName(targetClass);
			sb.append(nsName).append("_v");
			nsValue = getNamespaceValue(cacheManager,nsName);
			sb.append(nsValue).append(".");
			return sb.toString();
		}
		for(CacheNsParameter parameter:nses){
			nsName= getNsName(targetClass,parameter.name(),objects[parameter.keyIndex()-1]);
			sb.append(nsName).append("_v");
			nsValue = getNamespaceValue(cacheManager,nsName);
			sb.append(nsValue).append(".");
		}
		return sb.toString();
	}
	private String getNsName(Class targetClass, String name,Object value){
			return  String.format("%s.%s.%s",targetClass.getSimpleName(),name,value);
	}
	private String getDefaultListNsName(Class targetClass){
			return String.format("%s.%s",targetClass,CacheConstants.COMMON_LIST_NAMESPACE);
	}
	protected String  getNamespaceValue(CacheManager cacheManager,String namespace) {
		//得到namespace的版本
		Cache nsCache = cacheManager.getCache(CacheConstants.ENTITY_NS_CACHE_NAME);
		Object obj = nsCache.get(namespace);
		if(obj == null){
			obj = new Long(0);
			nsCache.put(namespace,obj,360000);
		}
		return String.valueOf(obj);
	}
	@Override
	public void upgradeNamespaceVersion(CacheManager cacheManager,
			CacheEvent event, Iterator<String> namespaces) {
		Object targetObject = event.getTargetObject();
		Class targetClass = event.getTargetClass();
		//upgrdae default list value
		String nsName = getDefaultListNsName(targetClass);
		incrementNamespace(cacheManager,nsName);
		
		//更新其他的缓存数据
		while(namespaces.hasNext()){
			String name = namespaces.next();
			if(name.equals(CacheConstants.COMMON_LIST_NAMESPACE)){
				nsName= getDefaultListNsName(targetClass);
			}else{
    			PropertyAdapter adapter = propertyAccess.getAdapter(targetObject).getPropertyAdapter(name);
    			if(adapter == null){
    				throw new RuntimeException(String.format("未能找到类%s有属性%s",event.getTargetClass(),name));
    			}
    			String strValue = valueEncoderSource.getValueEncoder(adapter.getBeanType()).toClient(adapter.get(targetObject));
    			//得到名字
    			nsName = getNsName(event.getTargetClass(),name,strValue);
			}
			incrementNamespace(cacheManager,nsName);
		}
	}
	protected void incrementNamespace(CacheManager cacheManager, String namespace) {
		Cache nsCache = cacheManager.getCache(CacheConstants.ENTITY_NS_CACHE_NAME);
		nsCache.increment(namespace, 1);
	}
}
