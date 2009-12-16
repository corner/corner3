/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: CornerEntityPersistentFieldStrategy.java 5922 2009-09-23 03:22:01Z jcai $
 * created at:2009-09-22
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
 * @version $Revision: 5922 $
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
