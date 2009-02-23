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
package corner.table;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

/**
 * 用来查询使用的模型
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 2305 $
 * @since 0.0.1
 */
public  abstract class DefaultQueryModel implements QueryModel{

	private QueryCallback callback;
	private int currentPage;
	private int rowsPerPage;

	public DefaultQueryModel(QueryCallback callback,int currentPage,int rowsPerPage) {
		this.callback = callback;
		this.currentPage = currentPage;
		this.rowsPerPage = rowsPerPage;
	}

	
	/**
	 * 
	 * @see corner.table.QueryModel#getCurrentPageRecord()
	 */
	public Iterator<?> getCurrentPageRecord() {
		Criteria criteria = this.callback.createCriteria();
		this.callback.appendCriteria(criteria);
		this.callback.appendOrder(criteria);
		
		//设置查询数据的上下限
		criteria.setFirstResult((currentPage-1)*rowsPerPage);
		criteria.setMaxResults(rowsPerPage);
		
		return criteria.list().iterator();
	}
	/**
	 * 
	 * @see corner.table.QueryModel#getRowCount()
	 */
	public int getRowCount(){
		Criteria criteria = this.callback.createCriteria();
		this.callback.appendCriteria(criteria);
		
		//设置查询范围为行数
		criteria.setProjection(Projections.rowCount());
		
		return (Integer) criteria.list().iterator().next();
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public int getRowsPerPage() {
		return this.rowsPerPage;
	}

	

}
