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
package corner.pages.base;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

/**
 * 范性化的表单页面
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 3998 $
 * @since 0.0.1
 */
public class GenericNewForm<T> extends AbstractEntityForm<T> {
	/** Logger **/
	@Inject
	private Logger logger;
	@Inject
	private ComponentResources resources;
	
	/**
	 * 通过范型用来实例化model对象
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@OnEvent(value=EventConstants.PREPARE,component="entityForm")
	void prepareEntityInstance() throws InstantiationException, IllegalAccessException{
		if(this.getEntity()==null){
			Class<T> clazz = this.getEntityClass(); 
			logger.debug("create entity instance by name"+clazz.getName());
			this.setEntity(clazz.newInstance());
		}
	}
	
	/**
	 * 哦得到返回的页面，子类可以覆盖
	 * @return 得到返回的页面
	 */
	@Override
	protected String getReturnPage() {
		//得到本页面的名称
		String pageName = resources.getPageName();
		logger.debug("entity new page name:["+pageName+"]");
		//构造列表页面的名称
		String listPageName=pageName.replaceAll("([\\w\\/]*[^\\/]*)New", "$1List");
		
		logger.debug("list page name:["+listPageName+"]");
		
		return listPageName;
	}

	

}
