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
package corner.tapestry.services.override;

import org.apache.tapestry5.internal.services.templates.PageTemplateLocator;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.ComponentClassResolver;

/**
 * 针对在context中能够寻找html作为模板。
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PageTemplateLocatorWithHtml extends PageTemplateLocator {


	/**
	 * @param contextRoot
	 * @param resolver
	 */
	public PageTemplateLocatorWithHtml(Resource contextRoot,
			ComponentClassResolver resolver) {
		super(contextRoot, resolver);
		
	}
	
}
