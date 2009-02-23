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
package corner.components;

import java.util.Iterator;


import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.Heartbeat;

import corner.table.QueryModel;

/**
 * 用来展示表格的视图
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
@SupportsInformalParameters
public class TableRow {


	/**
	 * The element to render. If not null, then the loop will render the
	 * indicated element around its body (on each pass through the loop). The
	 * default is derived from the component template.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String element;

	
	/**
	 * The current value, set before the component renders its body.
	 */
	@SuppressWarnings("unused")
	@Parameter
	private Object value;

	/**
	 * The index into the source items.
	 */
	@Parameter
	private int index;

	private Iterator<?> iterator;

	@Environmental
	private Heartbeat heartbeat;

	@Inject
	private ComponentResources resources;

	@Inject
	private ComponentDefaultProvider componentDefaultProvider;

	@Environmental
	private QueryModel model;




	Binding defaultSource() {
		return componentDefaultProvider.defaultBinding("source", resources);
	}

	String defaultElement() {
		return resources.getElementName();
	}

	@SetupRender
	boolean setup() {
		index = 0;

		
		iterator = this.model.getCurrentPageRecord(); 

		// Only render the body if there is something to iterate over

		boolean result = iterator.hasNext();

		return result;
	}

	/**
	 * Begins a new heartbeat.
	 */
	@BeginRender
	void begin() {
		value = iterator.next();

		startHeartbeat();
	}

	private void startHeartbeat() {
		heartbeat.begin();
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

	/**
	 * Ends the current heartbeat.
	 */
	@AfterRender
	boolean after() {
		endHeartbeat();

		return !iterator.hasNext();
	}

	private void endHeartbeat() {
		heartbeat.end();

		index++;
	}

	

	

}
