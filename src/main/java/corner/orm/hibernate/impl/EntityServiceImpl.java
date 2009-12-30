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

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import corner.orm.hibernate.Hibernate;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;

/**
 * 公用的实体服务类的实现.
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
@Hibernate
public class EntityServiceImpl  implements EntityService{
	private final HibernateTemplate template;
    private PaginatedEntityService paginatedService;

	
    public EntityServiceImpl(
    		final Session session,
    		final TypeCoercer typeCoercer
    		){
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
	public <T>void delete(T object) {
		this.template.delete(object);
	}

	@Override
	public <T>Iterator<T> find(Class<T> persistClass, Object conditions, String order) {
		return this.find(persistClass, conditions,order,0,Integer.MAX_VALUE);
	}

	@Override
	public<T> Iterator<T> find(Class<T> persistClass, Object conditions,
			String order, int start, int offset) {
		return paginatedService.find(persistClass, conditions, order,start,offset);
	}

	@Override
	public <T> T get(Class<T> clazz, Object id) {
		return (T) this.template.get(clazz, (Serializable) id);
	}

	@Override
	public <T>PaginationList<T> paginate(Class<T> persistClass, Object conditions,
			String order, PaginationOptions options) {
		return paginatedService.paginate(persistClass, conditions, order, options);
	}

	@Override
	public<T> void save(T object) {
		this.template.save(object);
		
	}

	@Override
	public <T>void update(T object) {
		template.update(object);
		
	}

	@Override
	public <T>void refresh(T entity) {
		template.refresh(entity);
	}

	@Override
	public <T>void saveOrUpdate(T entity) {
		template.saveOrUpdate(entity);
	}

	@Override
	public Class getEntityClass(Object entity) {
		return org.hibernate.Hibernate.getClass(entity);
	}

	@Override
	public <T> T findUnique(Class<T> clazz, Object[] conditions) {
		Iterator<T> it = this.find(clazz, conditions, null);
		T result=null;
		if(it.hasNext()){
			result = it.next();
		}
		if(it.hasNext()){
			throw new ObjectRetrievalFailureException("查询对象不唯一!,请检查查询条件.",null);
		}
		return result;
	}
}
