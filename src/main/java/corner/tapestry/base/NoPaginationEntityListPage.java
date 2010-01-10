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
package corner.tapestry.base;

import java.util.Iterator;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderSource;

import corner.orm.services.EntityService;
import corner.tapestry.ComponentConstants;
import corner.tapestry.transform.PageRedirect;

/**
 * 抽象的实体列表页面
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class NoPaginationEntityListPage<T> extends EntityPage<T> {
	@Inject
	private EntityService entityService;

	@Inject
	private ValueEncoderSource valueEncoderSource;


	@Cached
	public Iterator<T> getEntities(){
		return entityService.find(getEntityClass(), null, null);
	}

	
	/**
	 * do delete entity action
	 * @param entity entity object
	 * @since 0.0.2
	 */
	@OnEvent(component = ComponentConstants.DELETE_LINK, value = EventConstants.ACTION)
	@PageRedirect
	void doDeleteEntityAction(T entity) {
		//TODO because t5 couldn't recognize the generic type.so manully to convert string to entity
		//but if you override the method,if some exception thrown ,you can ignore this.
		T obj = valueEncoderSource.getValueEncoder(getEntityClass()).toValue(String.valueOf(entity));
		deleteEntity(obj);
	}
	protected void deleteEntity(T entity){
		entityService.delete(entity);
	}
}
