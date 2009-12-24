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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.ioc.util.StrategyRegistry;

import corner.cache.services.CacheProcessor;
import corner.cache.services.CacheProcessorSource;
import corner.orm.EntityConstants;
import corner.orm.services.EntityService;

/**
 * implements RestoreCacheProcessorSource
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheProcessorSourceImpl implements CacheProcessorSource{
	private StrategyRegistry<CacheProcessor> registry;
	private PropertyAccess propertyAccess;
	private TypeCoercer coercer;
	private EntityService entityService;
	public CacheProcessorSourceImpl(
			Map<Class,CacheProcessor> configuration,
			PropertyAccess propertyAccess,
			TypeCoercer coercer,
			EntityService entityService
			){
		 registry = StrategyRegistry.newInstance(CacheProcessor.class, configuration,true);
		 this.propertyAccess = propertyAccess;
		 this.coercer = coercer;
		 this.entityService = entityService;
	}
	@Override
	public <T> CacheProcessor<T> getProcessor(final Class<T> clazz) {
		CacheProcessor processor = registry.get(clazz);
		if(processor !=null ){
			return processor;
		}
		if(clazz.isAnnotationPresent(Entity.class)){
			return new CacheProcessor(){
				@Override
				public Object fromCache(List cacheObject, Method method) {
					//fetch id property class
					Class<?> idClass = propertyAccess.getAdapter(clazz).
						getPropertyAdapter(EntityConstants.ID_PROPERTY_NAME).getType();
					//convert id value
					Object obj = coercer.coerce(cacheObject, idClass);
					//find object 
					obj = entityService.get(clazz,obj);
					return obj;
				}
				@Override
				public List toCache(Object value) {
					return convertAsListObject(value);
				}};
		}else{
			return new CacheProcessor(){
				@Override
				public Object fromCache(List cacheObject, Method method) {
					return coercer.coerce(cacheObject,clazz);
				}
				@Override
				public List toCache(Object value) {
					return convertAsListObject(value);
				}};
		}
	}
	public List<Object> convertAsListObject(Object value){
		Iterable iterable = coercer.coerce(value, Iterable.class);
		Iterator it = iterable.iterator();
		List<Object> list = new ArrayList<Object>();
		while (it.hasNext()) {
			Object obj = it.next();
			//is persistence object
			if (entityService.getEntityClass(obj).isAnnotationPresent(Entity.class)){
				list.add(propertyAccess.get(obj, EntityConstants.ID_PROPERTY_NAME));
			}else{
				list.add(obj);
			}
		}
		return list;
	}
}
