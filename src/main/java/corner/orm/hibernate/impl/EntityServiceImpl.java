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
package corner.orm.hibernate.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import corner.orm.hibernate.Hibernate;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;

/**
 * 公用的实体服务类的实现.
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 5477 $
 * @since 0.0.1
 */
@Hibernate
public class EntityServiceImpl  implements EntityService{
	private final HibernateTemplate template;
    private PaginatedEntityService paginatedService;

    public EntityServiceImpl(final Session session,final TypeCoercer typeCoercer){
		this.template = new HibernateTemplate(){
			/**
			 * @see org.springframework.orm.hibernate3.HibernateTemplate#execute(org.springframework.orm.hibernate3.HibernateCallback, boolean)
			 */
			@Override
			public Object doExecute(HibernateCallback action,boolean enforceNewSession, boolean enforceNativeSessio) throws DataAccessException {
				try {
					return action.doInHibernate(session);
				} catch (HibernateException ex) {
					throw convertHibernateAccessException(ex);
				} catch (SQLException ex) {
					throw convertJdbcAccessException(ex);
				}
			}
		};
        this.paginatedService = new PaginatedEntityService(template,typeCoercer);
	}

	@Override
	public long count(Class<?> persistClass, Object conditions) {
		return paginatedService.count(persistClass, conditions);
	}

	@Override
	public void delete(Object object) {
		this.template.delete(object);
	}

	@Override
	public Iterator find(Class<?> persistClass, Object conditions, String order) {
		return paginatedService.find(persistClass, conditions,order);
	}

	@Override
	public Iterator find(Class<?> persistClass, Object conditions,
			String order, int start, int offset) {
		return paginatedService.find(persistClass, conditions, order,start,offset);
	}

	@Override
	public <T> T get(Class<T> clazz, Object id) {
		return (T) this.template.get(clazz, (Serializable) id);
	}

	@Override
	public PaginationList paginate(Class<?> persistClass, Object conditions,
			String order, PaginationOptions options) {
		return paginatedService.paginate(persistClass, conditions, order, options);
	}

	@Override
	public void save(Object object) {
		this.template.save(object);
	}

	@Override
	public void update(Object object) {
		template.update(object);
	}

	@Override
	public void refresh(Object entity) {
		template.refresh(entity);
	}

}
