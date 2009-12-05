/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-10-15
 */
package corner.tapestry.base;

import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PropertyConduitSource;

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

}
