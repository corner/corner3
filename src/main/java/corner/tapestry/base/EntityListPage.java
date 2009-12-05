/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-10-15
 */
package corner.tapestry.base;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderSource;

import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;
import corner.tapestry.ComponentConstants;

/**
 * entity list common page
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class EntityListPage<T> extends EntityPage<T>{
	@Inject
	private EntityService entityService;
	@Property
	private PaginationOptions options;
	@Inject
	private ValueEncoderSource valueEncoderSource;
	//the query parameter
	public void onActivate(PaginationOptions options){
		this.options = options;
	}
	@Cached
	public PaginationList<T> getEntities(){
		if(options == null){
			options = new PaginationOptions();
			options.setPerPage(30);
		}
		return queryEntitis(options);
	}
	
	protected PaginationList<T> queryEntitis(PaginationOptions options) {
		return entityService.paginate(getEntityClass(), null, null, options);
	}
	/**
	 * do delete entity action
	 * @param entity entity object
	 * @since 0.0.2
	 */
	@OnEvent(component = ComponentConstants.DELETE_LINK, value = EventConstants.ACTION)
	public void doDeleteEntityAction(T entity) {
		//TODO because t5 couldn't recognize the generic type.so manully to convert string to entity
		//but if you override the method,if some exception thrown ,you can ignore this.
		Object obj = valueEncoderSource.getValueEncoder(getEntityClass()).toValue(String.valueOf(entity));
		entityService.delete(obj);
	}

}
