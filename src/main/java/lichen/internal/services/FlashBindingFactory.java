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
package lichen.internal.services;

import lichen.services.FlashFacade;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.internal.bindings.LiteralBinding;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;

/**
 * 提供前端的flash的展示bing
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public class FlashBindingFactory implements BindingFactory {
	private FlashFacade facade;
	public FlashBindingFactory(FlashFacade facade){
		this.facade = facade;
	}
	/**
	 * @see org.apache.tapestry5.services.BindingFactory#newBinding(java.lang.String, org.apache.tapestry5.ComponentResources, org.apache.tapestry5.ComponentResources, java.lang.String, org.apache.tapestry5.ioc.Location)
	 */
	public Binding newBinding(String description, ComponentResources container,
			ComponentResources component, String expression, Location location) {
		String messageValue = this.facade.get(expression);
		return new LiteralBinding(description, messageValue, location);
	}

}
