/* 
 * Copyright 2008 The Lichen Team.
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
package corner.pages.base;


import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.hibernate.Criteria;
import org.hibernate.Session;

import corner.components.Pagination;
import corner.components.TableRow;
import corner.components.TableView;
import corner.table.QueryCallback;

/**
 * 只读的范型的列表页面
 * 
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @author <a href="d0ng@ouriba.com">dong</a>
 * @version $Revision: 2088 $
 * @since 0.0.1
 */
public class GenericListView<T> {
	private T entity;
	@Inject
	private PropertyConduitSource source;
	@Inject
	private Session session;
	
	@SuppressWarnings("unused")
	@Component(parameters = { "callback=queryEntityCallback" })
	private TableView entityTable;

	@SuppressWarnings("unused")
	@Component(parameters = { "value=entity" })
	private TableRow entityRow;

	@SuppressWarnings("unused")
	@Component
	private Pagination entityPagination;

	/**
	 * 用来查询的回掉函数
	 * 
	 * @return 回掉函数
	 */
	public QueryCallback getQueryEntityCallback() {
		return new QueryCallback() {
			public void appendCriteria(Criteria criteria) {
				GenericListView.this.appendCriteria(criteria);
			}

			public void appendOrder(Criteria criteria) {
				GenericListView.this.appendOrder(criteria);
			}

			public Criteria createCriteria() {
				return session.createCriteria(getQueryEntityClass());
			}
		};
	}

	protected void appendCriteria(Criteria criteria) {

	}

	protected void appendOrder(Criteria criteria) {

	}

	@SuppressWarnings("unchecked")
	protected Class<T> getQueryEntityClass() {
		return source.create(this.getClass(), "entity").getPropertyType();
	}

	/**
	 * @return the entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}
}
