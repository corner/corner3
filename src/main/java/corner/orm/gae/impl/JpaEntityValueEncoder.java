/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-09-22
 */

package corner.orm.gae.impl;

import javax.persistence.EntityManager;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.PropertyAdapter;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.slf4j.Logger;

import corner.orm.EntityConstants;

/**
 * jpa entity value encoder
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class JpaEntityValueEncoder<E> implements ValueEncoder<E> {

	private Class<?> entityClass;
	private EntityManager entityManager;
	private PropertyAdapter propertyAdapter;
	private TypeCoercer typeCoercer;
	private Logger logger;
	public JpaEntityValueEncoder(Class<?> entityClass,
			PropertyAccess propertyAccess, TypeCoercer typeCoercer,
			Logger logger,EntityManager entityManager) {
		this.entityClass = entityClass;
		this.entityManager = entityManager;
		this.typeCoercer = typeCoercer;
		propertyAdapter = propertyAccess.getAdapter(this.entityClass)
				.getPropertyAdapter(EntityConstants.ID_PROPERTY_NAME);
        this.logger = logger;
	}

	/**
	 * @see org.apache.tapestry5.ValueEncoder#toClient(java.lang.Object)
	 */
	@Override
	public String toClient(E value) {
        if (value == null) return null;

        Object id = propertyAdapter.get(value);

        if (id == null)
            throw new IllegalStateException(String.format(
                    "Entity %s has an %s property of null; this probably means that it has not been persisted yet.",
                    value, EntityConstants.ID_PROPERTY_NAME));

        return typeCoercer.coerce(id, String.class);
	}

	/**
	 * @see org.apache.tapestry5.ValueEncoder#toValue(java.lang.String)
	 */
	@Override
	public E toValue(String clientValue) {
        if (InternalUtils.isBlank(clientValue)) return null;

        Object id = typeCoercer.coerce(clientValue, propertyAdapter.getType());

        E result = (E) entityManager.find(entityClass, id);

        if (result == null)
        {
            // We don't identify the entity type in the message because the logger is based on the entity type.
            logger.error(String.format("Unable to convert client value '%s' into an entity instance.", clientValue));
        }

        return result;
	}

}
