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

import java.sql.SQLException;
import java.util.List;

import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import corner.orm.hibernate.HibernateEntityService;

/**
 * 针对 {@link HibernateEntityService}的操作
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class HibernateEntityServiceImpl implements HibernateEntityService{
	private final HibernateTemplate template;

    public HibernateEntityServiceImpl(final Session session,final TypeCoercer typeCoercer){
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
	}
	@Override
	public int bulkUpdate(String queryString, Object value)
			throws DataAccessException {
		return template.bulkUpdate(queryString,value);
	}

	@Override
	public int bulkUpdate(String queryString, Object[] values)
			throws DataAccessException {
		return template.bulkUpdate(queryString,values);
	}

	@Override
	public int bulkUpdate(String queryString) throws DataAccessException {
		return template.bulkUpdate(queryString);
	}

	@Override
	public List executeFind(HibernateCallback action)
			throws DataAccessException {
		return template.executeFind(action);
	}

	@Override
	public void saveOrUpdate(Object entity) throws DataAccessException {
		template.saveOrUpdate(entity);
	}
	@Override
	public void evict(Object entity) {
		template.evict(entity);
	}
	@Override
	public Object execute(HibernateCallback hibernateCallback) {
		return template.execute(hibernateCallback);
	}

}
