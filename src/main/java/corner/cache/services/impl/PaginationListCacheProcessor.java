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

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ValueEncoderSource;

import corner.cache.services.CacheProcessor;
import corner.orm.EntityConstants;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;

/**
 * 针对PaginationList返回结果的处理
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class PaginationListCacheProcessor implements CacheProcessor<PaginationList>{
	private TypeCoercer coercer;
	private EntityService entityService;
	private PropertyAccess propertyAccess;
	private ValueEncoderSource valueEncoderSource;
	public PaginationListCacheProcessor(TypeCoercer coercer,
			EntityService entityService, PropertyAccess propertyAccess,
			ValueEncoderSource valueEncoderSource) {
		super();
		this.coercer = coercer;
		this.entityService = entityService;
		this.propertyAccess = propertyAccess;
		this.valueEncoderSource = valueEncoderSource;
	}
	@Override
	public List<Object> toCache(PaginationList value)  {
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
		ValueEncoder<PaginationOptions> encoder = valueEncoderSource
					.getValueEncoder(PaginationOptions.class);
		String options = encoder.toClient(value.options());
		list.add(0, options);
		return list;
	}
	@Override
	public PaginationList fromCache(List<Object> cacheObject, Method method) {
		final Iterator it = cacheObject.iterator();
		ValueEncoder encoder = valueEncoderSource
				.getValueEncoder(PaginationOptions.class);
		PaginationOptions options = (PaginationOptions) encoder
				.toValue((String) it.next());
		final Class clazz = getEntityClass(method);
		List result = new ArrayList();
		while(it.hasNext()){
			Object obj = entityService.get(clazz,it.next());
			if(obj!=null) // 如果发现已经为空，则不进行处理
				result.add(obj);
			
		}
		return new PaginationList(result.iterator(), options);
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
