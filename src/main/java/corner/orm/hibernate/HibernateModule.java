package corner.orm.hibernate;


import java.io.IOException;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.services.AliasContribution;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import corner.migration.MigrationModule;
import corner.orm.hibernate.impl.EntityServiceImpl;
import corner.orm.hibernate.impl.HibernateEntityServiceImpl;
import corner.orm.hibernate.impl.SpringSessionManagerImpl;
import corner.orm.hibernate.impl.TapestryHibernateTransactionDecorterImpl;
import corner.orm.services.EntityService;
import corner.transaction.services.TransactionDecorator;
import corner.tree.TreeModule;

/**
 * hibernate 操作的模块
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
@SubModule({MigrationModule.class,TreeModule.class})
public class HibernateModule {
	public static void bind(ServiceBinder binder){
		binder.bind(EntityService.class,EntityServiceImpl.class);
		binder.bind(HibernateEntityService.class,HibernateEntityServiceImpl.class);
		binder.bind(TransactionDecorator.class,TapestryHibernateTransactionDecorterImpl.class);
	}
	public static HibernateTemplate buildHibernateTemplate(
			SessionFactory sessionFactory) {
		HibernateTemplate template = new HibernateTemplate(sessionFactory);
		return template;

	}
	
	/**
	 * 替换由HibernateModule提供的HibernateSessionManager
	 * 
	 * @param sessionSource
	 * @param perthreadManager
	 * @return
	 */
	@Scope(org.apache.tapestry5.ioc.ScopeConstants.PERTHREAD)
	public static HibernateSessionManager buildSpringSessionManager(
			HibernateSessionSource sessionSource,
			PerthreadManager perthreadManager) {
		SpringSessionManagerImpl service = new SpringSessionManagerImpl(
				sessionSource);
		perthreadManager.addThreadCleanupListener(service);
		return service;
	}
	/**
	 * 覆盖由Hibernate Module提供的HibernateSessionManager
	 * 
	 * @param manager
	 * @param configuration
	 */
	public static void contributeAliasOverrides(
			@Local HibernateSessionManager manager,
			Configuration<AliasContribution<HibernateSessionManager>> configuration){
		configuration.add(AliasContribution.create(
				HibernateSessionManager.class, manager));
	}
	
	/**
	 * build spring platform transaction manager
	 * @param sessionSource session source
	 * @param session 
	 * @return
	 * @since 0.0.2
	 */
	public static PlatformTransactionManager buildPlatformTransactionManager(HibernateSessionSource sessionSource){
		HibernateTransactionManager platformTransactionManager = new HibernateTransactionManager();
		platformTransactionManager.setSessionFactory(sessionSource.getSessionFactory());
		platformTransactionManager.afterPropertiesSet();
		return platformTransactionManager;
	}
	
	
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,@Local final HibernateSessionManager hibernateSessionManager)
    {

        RequestFilter openSessionInView = new RequestFilter()
        {
            public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException
            {
            	//get session
            	hibernateSessionManager.getSession();
                return handler.service(request, response);
            }
        };
        
        configuration.add("OpenSessionInView",openSessionInView,
        		"after:StoreIntoGlobals","before:EndOfRequest");
    }
}
