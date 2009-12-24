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

import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.annotations.PageAttached;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.springframework.beans.BeanUtils;

/**
 * entity page class
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class EntityPage<T> {
	@Persist("entity")
	private T entity;
	@Inject
	private PropertyConduitSource source;
	private Class entityClass;
	public EntityPage(){
		PropertyConduit pc = source.create(this.getClass(), "entity");
		entityClass = pc.getPropertyType();
		assert entityClass!=null;
	}
	public Class getEntityClass() {
		return entityClass;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	@PageAttached
	void initEntity(){
		if(this.getEntity()==null){
			this.setEntity((T) BeanUtils.instantiateClass(entityClass));
		}
	}
		
}
