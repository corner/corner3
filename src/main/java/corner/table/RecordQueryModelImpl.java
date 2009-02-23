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
package corner.table;

import java.util.Iterator;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;

/**
 * 用于为RecordView提供数据的Model
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class RecordQueryModelImpl implements QueryModel {
	private final ComponentResources resources;
	private final int currentPage;
	private final int rowsPerPage;
	private final RecordQueryCallback callbackk;

	public RecordQueryModelImpl(final ComponentResources resources,
			int currentPage, int rowsPerPage, RecordQueryCallback callback) {
		this.resources = resources;
		this.currentPage = currentPage;
		this.rowsPerPage = rowsPerPage;
		this.callbackk = callback;
	}

	/**
	 * @see corner.table.QueryModel#createActionLink(int)
	 */
	@Override
	public Link createActionLink(int pageIndex) {
		Object[] context = new Object[] { pageIndex };
		Link link = resources.createEventLink(EventConstants.ACTION, context);
		return link;
	}

	/**
	 * @see corner.table.QueryModel#getCurrentPage()
	 */
	@Override
	public int getCurrentPage() {
		return this.currentPage;
	}

	/**
	 * @see corner.table.QueryModel#getCurrentPageRecord()
	 */
	@Override
	public Iterator<?> getCurrentPageRecord() {
		return this.callbackk.getData(this.currentPage, this.rowsPerPage)
				.iterator();
	}

	/**
	 * @see corner.table.QueryModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return this.callbackk.getCount();
	}

	/**
	 * @see corner.table.QueryModel#getRowsPerPage()
	 */
	@Override
	public int getRowsPerPage() {
		return this.rowsPerPage;
	}

}
