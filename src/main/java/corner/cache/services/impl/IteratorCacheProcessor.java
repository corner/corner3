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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;

import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;

import corner.cache.services.CacheProcessor;
import corner.orm.EntityConstants;
import corner.orm.services.EntityService;

/**
 * 针对Iterator返回结果的处理
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class IteratorCacheProcessor implements CacheProcessor<Iterator>{
	private EntityService entityService;
	private TypeCoercer coercer;
	public IteratorCacheProcessor(EntityService entityService,
			PropertyAccess propertyAccess,TypeCoercer coercer) {
		this.entityService = entityService;
		this.propertyAccess = propertyAccess;
		this.coercer = coercer;
	}
	private PropertyAccess propertyAccess;
	@Override
	public List<Object> toCache(Iterator value)  {
		// 针对结果全部作为list处理
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
	@Override
	public Iterator fromCache(List<Object> cacheObject, Method method) {
		final Iterator it = cacheObject.iterator();
		final Class clazz = getEntityClass(method);
		if(clazz.isAnnotationPresent(Entity.class)){
			Iterator wrapperIt = new Iterator() {
				@Override
				public boolean hasNext() {
					return it.hasNext();
				}

				@Override
				public Object next() {
					return entityService.get(clazz, it.next());
				}

				@Override
				public void remove() {
				}
			};
			return wrapperIt;
		}
		else{
			return it;
		}
	}
	//通过分析方法的返回值来得到范性的值,
	//譬如： PaginationList<Member> 得到的结果是Member
	private Class<?> getEntityClass(Method method) {
		Class<?> defaultType = method.getReturnType();
		Type genericType = method.getGenericReturnType();
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			return (Class<?>) pt.getActualTypeArguments()[0];

		}
		return defaultType;
	}
}
