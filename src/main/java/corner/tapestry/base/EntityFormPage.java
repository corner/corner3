/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-10-15
 */
package corner.tapestry.base;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.slf4j.Logger;

import corner.orm.EntityConstants;
import corner.orm.services.EntityService;
import corner.tapestry.ComponentConstants;

/**
 * entity generic form page
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 * @param <T> 实体对象
 */
public class EntityFormPage<T> extends EntityPage<T>{
	@Inject
	private EntityService entityService;
	@Inject
	private PropertyAccess propertyAccess;
	@Inject
	private Logger logger;
	public void onActivate(EventContext context){
		try{
			if(context.getCount()==1){
				this.setEntity((T) context.get(this.getEntityClass(), 0));
			}
		}catch(Exception e){
			logger.warn(e.toString());
		}
	}
	@OnEvent(component = ComponentConstants.ENTITY_FORM, value = EventConstants.SUCCESS)
	public Object doSaveEntityAction()  throws Throwable {
		//execute prefix save action 
		preSaveAction(getEntity());
		//persist entity
		saveEntity();
		//post save action
		postSaveAction(getEntity());
		
		//return 
		return getReturnObject();
	}
	protected void saveEntity(){
		if(propertyAccess.get(getEntity(), EntityConstants.ID_PROPERTY_NAME)==null){
			//new entity,so persist
			entityService.save(getEntity());
		}else{
			//old entity,so modify 
			entityService.update(getEntity());
		}
	}
	protected Object getReturnObject(){
		return null;
	}

	/**
	 * 在保存实体之前的操作
	 * @param entity 实体对象
	 * @since 0.0.2
	 */
	protected void preSaveAction(T entity) throws Throwable{
	}

	/**
	 * 保存实体之后的操作。
	 * @param entity
	 * @since 0.0.2
	 */
	protected void postSaveAction(T entity) throws Throwable{
	}
}
