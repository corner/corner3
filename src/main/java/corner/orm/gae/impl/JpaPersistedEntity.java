/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: JpaPersistedEntity.java 6398 2009-11-19 05:30:06Z jcai $
 * created at:2009-09-22
 */

package corner.orm.gae.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.tapestry5.annotations.ImmutableSessionPersistedObject;
import org.springframework.beans.BeanUtils;

/**
 * jpa persistence entity
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 6398 $
 * @since 0.1
 */
@ImmutableSessionPersistedObject
public class JpaPersistedEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3310170928184760310L;

	private final String entityName;

	private final Serializable id;

	public JpaPersistedEntity(String entityName, Serializable id) {
		this.entityName = entityName;
		this.id = id;
	}

	Object restore(EntityManager entityManager) {
		try {
			Class<?> clazz = Class.forName(entityName);
			if(id!=null){//persist from store
				return entityManager.find(clazz, id);
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
