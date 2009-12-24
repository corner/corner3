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
package corner.orm.services.impl;

import java.io.Serializable;

import org.apache.tapestry5.annotations.ImmutableSessionPersistedObject;
import org.springframework.beans.BeanUtils;

import corner.orm.services.EntityService;

/**
 * jpa persistence entity
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
@ImmutableSessionPersistedObject
public class PersistedEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3310170928184760310L;

	private final String entityName;

	private final Serializable id;

	public PersistedEntity(String entityName, Serializable id) {
		this.entityName = entityName;
		this.id = id;
	}

	Object restore(EntityService entityService) {
		try {
			Class<?> clazz = Class.forName(entityName);
			if(id!=null){//persist from store
				return entityService.get(clazz, id);
			}else{//new class
				return BeanUtils.instantiateClass(clazz);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		return String.format("<PersistedEntity: %s(%s)>", entityName, id);
	}
}
