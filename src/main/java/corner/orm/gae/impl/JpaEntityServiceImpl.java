/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: JpaEntityServiceImpl.java 6101 2009-10-19 04:20:03Z jcai $
 * created at:2009-09-22
 */

package corner.orm.gae.impl;

import java.util.Iterator;

import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaTemplate;

import corner.orm.EntityConstants;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;

/**
 * provider access JPA.
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 6101 $
 * @since 0.1
 */
public class JpaEntityServiceImpl  implements EntityService{
	private JpaTemplate jpaTemplate;
	private PaginatedJapEntityService paginatedJpaEntityService;
	private PropertyAccess propertyAccess;
	public JpaEntityServiceImpl(JpaTemplate jpaTemplate,final TypeCoercer typeCoercer,PropertyAccess propertyAccess){
		this.jpaTemplate = jpaTemplate;
		paginatedJpaEntityService = new PaginatedJapEntityService(jpaTemplate,typeCoercer);
		this.propertyAccess = propertyAccess;
	}
	
	public Iterator find(Class<?> persistClass, Object conditions,
			String order, int start, int offset) {
		return paginatedJpaEntityService.find(persistClass, conditions, order,
				start, offset);
	}

	public long count(Class<?> persistClass, Object conditions) {
		return paginatedJpaEntityService.count(persistClass, conditions);
	}

	public Iterator find(Class<?> persistClass, Object conditions, String order) {
		return paginatedJpaEntityService.find(persistClass, conditions, order);
	}

	public PaginationList paginate(Class<?> persistClass, Object conditions,
			String order, PaginationOptions options) {
		return paginatedJpaEntityService.paginate(persistClass, conditions,
				order, options);
	}

	
	

	public <T> T get(Class<T> entityClass, Object id)
			throws DataAccessException {
		return this.jpaTemplate.find(entityClass, id);
	}

	
	public void update(Object entity) throws DataAccessException {
		this.jpaTemplate.merge(entity);
	}
	
	public void save(Object entity) throws DataAccessException {
		this.jpaTemplate.persist(entity);
	}
	
	public void refresh(Object entity) throws DataAccessException {
		this.jpaTemplate.refresh(entity);
	}
	
	public void delete(Object entity) throws DataAccessException {
		this.jpaTemplate.remove(entity);
	}

	@Override
	public void saveOrUpdate(Object entity) {
		if(propertyAccess.get(entity, EntityConstants.ID_PROPERTY_NAME)==null){
			//new entity,so persist
			save(entity);
		}else{
			//old entity,so modify 
			update(entity);
		}
	}

	@Override
	public Class getEntityClass(Object entity) {
		return entity.getClass();
	}
}
