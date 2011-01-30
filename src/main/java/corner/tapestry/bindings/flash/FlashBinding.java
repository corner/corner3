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
package corner.tapestry.bindings.flash;

import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.Location;

/**
 * flash binding
 * 
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class FlashBinding extends AbstractBinding {
	private String expression;
	private FlashFacade facade;

	public FlashBinding(Location location, String description,
			String expression, FlashFacade facade) {
		super(location);
		this.expression = expression;
		this.facade = facade;
	}

	/**
	 * @see org.apache.tapestry5.Binding#get()
	 */
	@Override
	public Object get() {
		return facade.get(expression);
	}

	@Override
	public boolean isInvariant() {
		return false;
	}
}
