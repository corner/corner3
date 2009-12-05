package corner.orm.hibernate;


import org.apache.tapestry5.ioc.ServiceBinder;

import corner.orm.hibernate.impl.EntityServiceImpl;
import corner.orm.hibernate.impl.HibernateEntityServiceImpl;
import corner.orm.services.EntityService;

/**
 * hibernate 操作的模块
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class HibernateModule {
	public static void bind(ServiceBinder binder){
		binder.bind(EntityService.class,EntityServiceImpl.class);
		binder.bind(HibernateEntityService.class,HibernateEntityServiceImpl.class);
	}

}
