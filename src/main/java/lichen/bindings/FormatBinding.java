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
package lichen.bindings;

import java.text.Format;

import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.Location;

/**
 * 针对格式化的一些前缀
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 2088 $
 * @since 0.0.2
 */
public class FormatBinding extends AbstractBinding {

	private String toString;
	private Format formatter;
	public FormatBinding(Format formatter,String toString, Location location) {
		super(location);
		this.toString = toString;
		this.formatter = formatter;
	}

	/**
	 * @see org.apache.tapestry5.Binding#get()
	 */
	@Override
	public Object get() {
		return formatter;
	}
    @Override
    public String toString()
    {
        return toString;
    }
}
