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
package corner.orm.gae.impl;

import java.util.Iterator;

import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaTemplate;

import corner.orm.EntityConstants;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;

/**
 * provider access JPA.
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class JpaEntityServiceImpl implements EntityService {
	private JpaTemplate jpaTemplate;
	private PaginatedJapEntityService paginatedJpaEntityService;
	private PropertyAccess propertyAccess;

	public JpaEntityServiceImpl(JpaTemplate jpaTemplate,
			final TypeCoercer typeCoercer, PropertyAccess propertyAccess) {
		this.jpaTemplate = jpaTemplate;
		paginatedJpaEntityService = new PaginatedJapEntityService(jpaTemplate,
				typeCoercer);
		this.propertyAccess = propertyAccess;
	}

	/**
	 * @see corner.orm.services.EntityService#find(java.lang.Class,
	 *      java.lang.Object, java.lang.String, int, int)
	 */
	@Override
	public <T> Iterator<T> find(Class<T> persistClass, Object conditions,
			String order, int start, int offset) {
		return paginatedJpaEntityService.find(persistClass, conditions, order,
				start, offset);
	}

	/**
	 * @see corner.orm.services.EntityService#count(java.lang.Class,
	 *      java.lang.Object)
	 */
	@Override
	public long count(Class<?> persistClass, Object conditions) {
		return paginatedJpaEntityService.count(persistClass, conditions);
	}

	/**
	 * @see corner.orm.services.EntityService#find(java.lang.Class,
	 *      java.lang.Object, java.lang.String)
	 */
	@Override
	public <T> Iterator<T> find(Class<T> persistClass, Object conditions,
			String order) {
		return find(persistClass, conditions, order, 0, Integer.MAX_VALUE);
	}

	/**
	 * @see corner.orm.services.EntityService#paginate(java.lang.Class,
	 *      java.lang.Object, java.lang.String,
	 *      corner.orm.model.PaginationOptions)
	 */
	@Override
	public <T> PaginationList<T> paginate(Class<T> persistClass,
			Object conditions, String order, PaginationOptions options) {
		return paginatedJpaEntityService.paginate(persistClass, conditions,
				order, options);
	}

	/**
	 * @see corner.orm.services.EntityService#get(java.lang.Class,
	 *      java.lang.Object)
	 */
	@Override
	public <T> T get(Class<T> entityClass, Object id)
			throws DataAccessException {
		return this.jpaTemplate.find(entityClass, id);
	}

	/**
	 * @see corner.orm.services.EntityService#update(java.lang.Object)
	 */
	@Override
	public <T> void update(T entity) throws DataAccessException {
		this.jpaTemplate.merge(entity);
	}

	/**
	 * @see corner.orm.services.EntityService#save(java.lang.Object)
	 */
	@Override
	public <T> void save(T entity) throws DataAccessException {
		this.jpaTemplate.persist(entity);
	}

	/**
	 * @see corner.orm.services.EntityService#refresh(java.lang.Object)
	 */
	@Override
	public <T> void refresh(T entity) throws DataAccessException {
		this.jpaTemplate.refresh(entity);
	}

	/**
	 * @see corner.orm.services.EntityService#delete(java.lang.Object)
	 */
	@Override
	public <T> void delete(T entity) throws DataAccessException {
		this.jpaTemplate.remove(entity);
	}

	/**
	 * @see corner.orm.services.EntityService#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public <T> void saveOrUpdate(T entity) {
		if (propertyAccess.get(entity, EntityConstants.ID_PROPERTY_NAME) == null) {
			// new entity,so persist
			save(entity);
		} else {
			// old entity,so modify
			update(entity);
		}
	}

	/**
	 * @see corner.orm.services.EntityService#getEntityClass(java.lang.Object)
	 */
	@Override
	public Class<?> getEntityClass(Object entity) {
		return entity.getClass();
	}

	/**
	 * @see corner.orm.services.EntityService#findUnique(java.lang.Class,
	 *      java.lang.Object[])
	 */
	@Override
	public <T> T findUnique(Class<T> clazz, Object[] conditions) {
		Iterator<T> it = this.find(clazz, conditions, null);
		T result = null;
		if (it.hasNext()) {
			result = it.next();
		}
		if (it.hasNext()) {
			throw new ObjectRetrievalFailureException("查询对象不唯一!,请检查查询条件.", null);
		}
		return result;
	}
}
