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
package corner.tapestry.base;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ValueEncoderSource;

import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;
import corner.tapestry.ComponentConstants;
import corner.tapestry.transform.PageRedirect;

/**
 * entity list common page
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class EntityListPage<T> extends EntityPage<T>{
	@Inject
	private EntityService entityService;
	@Property
	private PaginationOptions options;
	@Inject
	private ValueEncoderSource valueEncoderSource;
	//the query parameter
	public void onActivate(PaginationOptions options){
		this.options = options;
		extractParameter(options.getParameters());
	}
	//解析参数
	protected void extractParameter(JSONObject parameters){
	}
	@Cached
	public PaginationList<T> getEntities(){
		if(options == null){
			options = new PaginationOptions();
			options.setPerPage(30);
		}
		return queryEntitis(options);
	}
	
	protected PaginationList<T> queryEntitis(PaginationOptions options) {
		return entityService.paginate(getEntityClass(), null, null, options);
	}
	
	/**
	 * do delete entity action
	 * @param entity entity object
	 * @since 0.0.2
	 */
	@OnEvent(component = ComponentConstants.DELETE_LINK, value = EventConstants.ACTION)
	@PageRedirect
	public void doDeleteEntityAction(T entity) {
		//TODO because t5 couldn't recognize the generic type.so manully to convert string to entity
		//but if you override the method,if some exception thrown ,you can ignore this.
		Object obj = valueEncoderSource.getValueEncoder(getEntityClass()).toValue(String.valueOf(entity));
		entityService.delete(obj);
	}

}
