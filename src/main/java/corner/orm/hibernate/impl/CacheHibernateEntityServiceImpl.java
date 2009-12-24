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
package corner.orm.hibernate.impl;

import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.hibernate.Session;

import corner.cache.model.CacheEvent;
import corner.cache.model.Operation;
import corner.cache.services.CacheStrategySource;
import corner.orm.EntityConstants;

/**
 * 实现缓存功能的hibernate实体服务类
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheHibernateEntityServiceImpl extends EntityServiceImpl{

	private CacheStrategySource cacheSource;
	private PropertyAccess propertyAccess;
	public CacheHibernateEntityServiceImpl(Session session,
			TypeCoercer typeCoercer, PropertyAccess propertyAccess,
			CacheStrategySource cacheSource) {
		super(session, typeCoercer);
    	this.cacheSource= cacheSource;
    	this.propertyAccess = propertyAccess;
	}
	@Override
	public <T>void delete(T object) {
		super.delete(object);
		//删除缓存
		CacheEvent<T> event = new CacheEvent<T>(getEntityClass(object),object,Operation.DELETE);
		cacheSource.catchEvent(event);
	}
	@Override
	public<T> void save(T object) {
		super.save(object);
		CacheEvent<T> event = new CacheEvent<T>(getEntityClass(object),object,Operation.INSERT);
		cacheSource.catchEvent(event);
	}
	@Override
	public <T>void update(T object) {
		super.update(object);
		CacheEvent<T> event = new CacheEvent<T>(getEntityClass(object),object,Operation.UPDATE);
		cacheSource.catchEvent(event);
	}
	@Override
	public <T>void saveOrUpdate(T entity) {
		if(propertyAccess.get(entity, EntityConstants.ID_PROPERTY_NAME)==null){
			CacheEvent<T> event = new CacheEvent<T>(getEntityClass(entity),entity,Operation.INSERT);
			cacheSource.catchEvent(event);
		}else{
    		CacheEvent<T> event = new CacheEvent<T>(getEntityClass(entity),entity,Operation.UPDATE);
    		cacheSource.catchEvent(event);
		}
		super.saveOrUpdate(entity);
	}
}
