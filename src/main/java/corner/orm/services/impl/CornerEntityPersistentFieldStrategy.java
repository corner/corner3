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

import org.apache.tapestry5.internal.services.AbstractSessionPersistentFieldStrategy;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;

import corner.orm.EntityConstants;
import corner.orm.services.EntityService;

/**
 * entity persistence field starategy
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class CornerEntityPersistentFieldStrategy extends AbstractSessionPersistentFieldStrategy{

		private PropertyAccess propertyAccess;
		private EntityService entityService;

		public CornerEntityPersistentFieldStrategy(EntityService entityService, Request request,PropertyAccess propertyAccess)
	    {
	        super("jpa:", request);
	        this.propertyAccess = propertyAccess;
	        this.entityService = entityService;
	    }

	    @Override
	    protected Object convertApplicationValueToPersisted(Object newValue)
	    {
	        Serializable id = (Serializable) propertyAccess.get(newValue, EntityConstants.ID_PROPERTY_NAME);
	        Class  clazz = entityService.getEntityClass(newValue);
            String entityName =  clazz.getName();
            return new PersistedEntity(entityName, id);
	    }

	    @Override
	    protected Object convertPersistedToApplicationValue(Object persistedValue)
	    {
	    	PersistedEntity persisted = (PersistedEntity) persistedValue;
	        return persisted.restore(entityService);
	    }
}
