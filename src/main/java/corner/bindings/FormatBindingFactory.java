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
package corner.bindings;

import java.text.Format;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;

/**
 * 针对格式化的binding工厂类
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 3018 $
 * @since 0.0.2
 */
public class FormatBindingFactory implements BindingFactory {

	private FormatterSource formatterSource;
	public FormatBindingFactory(FormatterSource formatterSource){
		this.formatterSource = formatterSource;
	}
	/**
	 * @see org.apache.tapestry5.services.BindingFactory#newBinding(java.lang.String, org.apache.tapestry5.ComponentResources, org.apache.tapestry5.ComponentResources, java.lang.String, org.apache.tapestry5.ioc.Location)
	 */
	@Override
	public Binding newBinding(String description, ComponentResources container,
			ComponentResources component, String expression, Location location) {
		Format  formatter = formatterSource.getFormatter(expression);
		String toString = String.format("ForamtBinding[%s %s(%s)]", description, container
	                .getCompleteId(), expression);
		return new FormatBinding(formatter,toString,location);
	}

}
