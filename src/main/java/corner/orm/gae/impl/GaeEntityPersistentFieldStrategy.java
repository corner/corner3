/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: GaeEntityPersistentFieldStrategy.java 5922 2009-09-23 03:22:01Z jcai $
 * created at:2009-09-22
 */

package corner.orm.gae.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.tapestry5.internal.services.AbstractSessionPersistentFieldStrategy;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;

import corner.orm.EntityConstants;

/**
 * entity persistence field starategy
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 5922 $
 * @since 0.1
 */
public class GaeEntityPersistentFieldStrategy extends AbstractSessionPersistentFieldStrategy{

	    private EntityManager entityManager;
		private PropertyAccess propertyAccess;

		public GaeEntityPersistentFieldStrategy(EntityManager entityManager, Request request,PropertyAccess propertyAccess)
	    {
	        super("jpa:", request);
	        this.entityManager = entityManager;
	        this.propertyAccess = propertyAccess;
	    }

	    @Override
	    protected Object convertApplicationValueToPersisted(Object newValue)
	    {
	        Serializable id = (Serializable) propertyAccess.get(newValue, EntityConstants.ID_PROPERTY_NAME);
            String entityName =  newValue.getClass().getName();
            return new JpaPersistedEntity(entityName, id);
	    }

	    @Override
	    protected Object convertPersistedToApplicationValue(Object persistedValue)
	    {
	    	JpaPersistedEntity persisted = (JpaPersistedEntity) persistedValue;
	        return persisted.restore(entityManager);
	    }
}
