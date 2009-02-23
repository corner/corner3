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
package corner.components;


import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Environment;
import org.slf4j.Logger;

import corner.LichenConstants;
import corner.table.QueryModel;
import corner.table.RecordQueryCallback;
import corner.table.RecordQueryModelImpl;

/**
 * 用来展示数据的视图,对TableView的简单修改,使用RecordQueryModelImpl获取数据
 * 
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @author <a href="d0ng@ouriba.com">dong</a>
 * @version $Revision: 2956 $
 * @since 0.0.1
 */
@SupportsInformalParameters
public class RecordView {

	@Parameter(required = true, principal = true)
	private RecordQueryCallback callback;

	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * The element to render. If not null, then the loop will render the
	 * indicated element around its body (on each pass through the loop). The
	 * default is derived from the component template.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String element;

	/** 每页显示的记录数 * */
	@Parameter
	private int rowsPerPage;

	@Inject
	private ComponentResources resources;

	@Inject
	private Environment environment;

	@Inject
	private Logger logger;

	@Inject
	@Symbol(LichenConstants.COMPOENT_TABLEVIEW_ROWS_PERPAGE)
	private String _rowsPerPage;

	private QueryModel model;

	String defaultElement() {
		return resources.getElementName();
	}

	String defaultRowsPerPage() {
		return _rowsPerPage;
	}

	@SetupRender
	void setup() {
		if (this.currentPage < 1) {
			this.currentPage = 1;
		}
		this.model = new RecordQueryModelImpl(resources, this.currentPage,
				this.rowsPerPage, this.callback);
		environment.push(QueryModel.class, model);

	}

	void beforeRenderBody(MarkupWriter writer) {
		if (element != null) {
			writer.element(element);
			resources.renderInformalParameters(writer);
		}
	}

	void afterRenderBody(MarkupWriter writer) {
		if (element != null)
			writer.end();
	}

	void afterRender() {
		logger.debug("pop QueryModel parameter object ");
		environment.pop(QueryModel.class);
	}

	/**
	 * 响应新页请求
	 */
	void onAction(int newPage) {
		if (logger.isDebugEnabled()) {
			logger.debug("newPage:" + newPage);
		}
		this.currentPage = newPage;
	}
}
