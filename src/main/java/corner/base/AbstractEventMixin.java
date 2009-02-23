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
package corner.base;

import java.util.List;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author <a href="mailto:homburgs@googlemail.com">S.Homburg</a>
 * @version $Id: AbstractEventMixin.java 2945 2008-11-11 05:01:15Z jcai $
 */
@MixinAfter
@SupportsInformalParameters
abstract public class AbstractEventMixin extends AbstractAjaxEvent implements EventMixin {
	@Inject
	private ComponentResources resources;

	@InjectContainer
	private ClientElement clientElement;


	@SuppressWarnings("unused")
	@Parameter(defaultPrefix = "literal")
	private String attach;

	/**
	 * 
	 * <a href="http://www.prototypejs.org/api/event/stop">Event.stop</a>
	 */
	@SuppressWarnings("unused")
	@Parameter(required = false, defaultPrefix = "literal")
	private boolean stop;

	/**
	 * The context for the link (optional parameter). This list of values will
	 * be converted into strings and included in the URI. The strings will be
	 * coerced back to whatever their values are and made available to event
	 * handler methods.
	 */
	@Parameter
	private List<?> context;

	private Object[] contextArray;

	/**
	 * get the conext parameter(s)
	 * 
	 * @return conext parameter(s)
	 */
	@Override
	public Object[] getContext() {
		return this.contextArray;
	}

	// fix bug:当混全使用的时候,没有把informal的参数输出
	void beginRender(MarkupWriter writer) {
		resources.renderInformalParameters(writer);
	}

	void setupRender() {
		contextArray = context == null ? new Object[0] : context.toArray();
	}

	void afterRender(MarkupWriter writer) {
		super.renderJsLibary(writer);
	}
	
	@Override
	public String getClientId(){
		return clientElement.getClientId();
	}
}
