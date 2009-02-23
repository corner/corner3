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
package lichen.components;

import lichen.table.QueryModel;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * 用来分页使用的组件
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 447 $
 * @since 0.0.1
 */
@IncludeStylesheet("Pagination.css")
public class Pagination {
	
	

	private int lastIndex;

	private int maxPages;



	@Environmental
	private QueryModel model;
	@Inject
	private Messages messages;

	
	

	@Parameter("5")
	private int range;

	void beginRender(MarkupWriter writer) {
		int availableRows = model.getRowCount();

		maxPages = ((availableRows - 1) / model.getRowsPerPage()) + 1;

		if (maxPages < 2)
			return;

		writer.element("div", "class", "pagination");

		lastIndex = 0;

		for (int i = 1; i <= 2; i++)
			writePageLink(writer, i);

		int low = model.getCurrentPage() - range;
		int high = model.getCurrentPage() + range;

		if (low < 1) {
			low = 1;
			high = 2 * range + 1;
		} else {
			if (high > maxPages) {
				high = maxPages;
				low = high - 2 * range;
			}
		}

		for (int i = low; i <= high; i++)
			writePageLink(writer, i);

		for (int i = maxPages - 1; i <= maxPages; i++)
			writePageLink(writer, i);

		writer.end();
	}

	private void writePageLink(MarkupWriter writer, int pageIndex) {
		if (pageIndex < 1 || pageIndex > maxPages)
			return;

		if (pageIndex <= lastIndex)
			return;

		if (pageIndex != lastIndex + 1) {
			writer.element("span", "class", "gap");
			writer.write(" ... ");
			writer.end();
		}

		lastIndex = pageIndex;

		if (pageIndex == model.getCurrentPage()) {
			writer.element("span", "class", "current");
			writer.write(Integer.toString(pageIndex));
			writer.end();
			return;
		}

		

		Link link=model.createActionLink(pageIndex);
		

		writer.element("a", "href", link, "title", messages.format("goto-page",
				pageIndex));

		writer.write(Integer.toString(pageIndex));
		writer.end();

	}



}
