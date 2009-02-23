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
package corner.pages.base;

import java.util.List;


import org.apache.tapestry5.annotations.Component;

import corner.components.Pagination;
import corner.components.RecordView;
import corner.components.TableRow;
import corner.table.RecordQueryCallback;

/**
 * 只读的范型的列表页面,与GenericListView不同的之处在于使用RecordView代替TableView实现数据的读取
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @author <a href="d0ng@ganshane.net">dong</a>
 * @version $Revision: 2088 $
 * @since 0.0.1
 */
public abstract class GenericRecordListView<T> {
	private T entity;

	@SuppressWarnings("unused")
	@Component(parameters = { "callback=queryEntityCallback" })
	private RecordView entityTable;

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
	public RecordQueryCallback getQueryEntityCallback() {
		return new RecordQueryCallback() {
			@Override
			public int getCount() {
				return GenericRecordListView.this.getCount();
			}

			@Override
			public List<?> getData(int currentPage, int rowsPerPage) {
				return GenericRecordListView.this.getData(currentPage,
						rowsPerPage);
			}
		};
	}

	/**
	 * 查询指定页数上的数据,由子类实现
	 * 
	 * @param currentPage
	 *            当前页数
	 * @param rowsPerPage
	 *            每页的条数
	 * @return
	 */
	protected abstract List<?> getData(int currentPage, int rowsPerPage);

	/**
	 * 取得记录的总条数
	 * 
	 * @return
	 */
	protected abstract int getCount();

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
