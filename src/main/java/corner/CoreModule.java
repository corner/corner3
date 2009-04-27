/* 
 * Copyright 2008 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner;

import java.util.Map;
import java.util.List;

import org.apache.tapestry5.hibernate.HibernateTransactionDecorator;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.internal.services.PageTemplateLocator;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.ServiceLifecycle;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.Builtin;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.json.JSONObject;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import corner.bindings.FormatterModule;
import corner.internal.services.CookiePersistentFieldStrategy;
import corner.internal.services.FlashBindingFactory;
import corner.internal.services.FlashFacadeImpl;
import corner.internal.services.HessianRemoteServiceCaller;
import corner.internal.services.RemoteCallServiceLifecycle;
import corner.internal.services.RemoteResponseResultProcessor;
import corner.internal.services.ServiceLocatorDelegateImpl;
import corner.livevalidator.ValidationModule;
import corner.protobuf.ProtocolBuffersModule;
import corner.services.EntityService;
import corner.services.FlashFacade;
import corner.services.HtmlTemplateProvider;
import corner.services.RemoteResponse;
import corner.services.RemoteServiceCaller;
import corner.services.RemoteServiceCallerSource;
import corner.services.ServiceLocatorDelegate;
import corner.services.asset.StaticAssetModule;
import corner.services.config.ServiceConfigModule;
import corner.services.fckeditor.FckeditorModule;
import corner.services.hadoop.HadoopModule;
import corner.services.impl.EntityServiceImpl;
import corner.services.impl.ForbidViewHtmlTemplate;
import corner.services.impl.PageTemplateLocatorWithHtml;
import corner.services.migration.MigrationModule;
import corner.services.payment.PaymentModule;
import corner.services.security.SecurityModule;
import corner.services.tree.TreeModule;
import corner.transform.PageRedirectWorker;
import corner.model.PaginationList;

/**
 * 定义了Corner的核心module
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 3041 $
 * @since 0.0.1
 */
@SubModule( { ValidationModule.class, StaticAssetModule.class,
		TreeModule.class, SecurityModule.class, ProtocolBuffersModule.class,
		FckeditorModule.class, PaymentModule.class, MigrationModule.class,
		HadoopModule.class, ServiceConfigModule.class,FormatterModule.class })
public class CoreModule {

	/**
	 * 绑定使用的service.
	 * 
	 * @param binder
	 *            Service Binder
	 * @see ServiceBinder
	 */
	public static void bind(ServiceBinder binder) {
		binder.bind(FlashFacade.class, FlashFacadeImpl.class);
		binder.bind(ServiceLocatorDelegate.class,
				ServiceLocatorDelegateImpl.class);
		binder.bind(EntityService.class, EntityServiceImpl.class);
	}

	/**
	 * 组件类。
	 * 
	 * @param configuration
	 *            library mapping configruation
	 */
	public static void contributeComponentClassResolver(
			Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("corner", "corner"));
	}

	/**
	 * 对重定向注释PageRedirect的配置
	 * 
	 * @param configuration
	 *            Module的配置实例
	 * @param resolver
	 *            用于查找Component资源的实例
	 * @param linkFactory
	 *            用于构建PageLink的LinkFactory
	 */
	public static void contributeComponentClassTransformWorker(
			OrderedConfiguration<ComponentClassTransformWorker> configuration,
			ComponentClassResolver resolver, LinkSource linkFactory) {
		configuration.add("pageRedirect", new PageRedirectWorker(resolver,
				linkFactory));
	}

	public static HibernateTemplate buildHibernateTemplate(
			SessionFactory sessionFactory) {
		HibernateTemplate template = new HibernateTemplate(sessionFactory);
		return template;

	}

	// 扩展一个flash前缀的binding
	public static void contributeBindingSource(
			MappedConfiguration<String, BindingFactory> configuration,
			ObjectLocator locator) {
		configuration
				.add("flash", locator.autobuild(FlashBindingFactory.class));
	}

	// 扩展一个可以在客户端进行Persist的保存策略
	public void contributePersistentFieldManager(
			MappedConfiguration<String, PersistentFieldStrategy> configuration,
			Request request, ObjectLocator locator) {
		configuration.add("cookie", locator
				.autobuild(CookiePersistentFieldStrategy.class));

	}

	/**
	 * Contributes the "remote" scope.
	 */
	public void contributeServiceLifecycleSource(
			MappedConfiguration<String, ServiceLifecycle> configuration,
			ObjectLocator locator) {
		configuration.add(CornerConstants.REMOTE_SCOPE, locator
				.autobuild(RemoteCallServiceLifecycle.class));
	}

	/**
	 * 对一些基础配置进行了初步的设置
	 * 
	 * @param configuration
	 *            配置
	 */
	public static void contributeFactoryDefaults(
			MappedConfiguration<String, String> configuration) {
		configuration.add(CornerConstants.REMOTE_SERVER_URL,
				"http://localhost:5180/corner/remote.hessian");
		configuration.add(CornerConstants.DEFAULT_CALLER, "hessian");
		configuration.add(CornerConstants.ENABLE_REMOTE_CALL, "false");

		configuration.add(CornerConstants.ENABLE_HTML_TEMPLATE, "false");
		configuration.add(CornerConstants.ENABLE_HTML_ACCESS, "true");

		// 配置默认的资源引用类型为classpath
		configuration.add(CornerConstants.ASSET_TYPE, "classpath");
		configuration.add(CornerConstants.COMPOENT_TABLEVIEW_ROWS_PERPAGE, "15");
	}

	/**
	 * 对远程调用方法进行扩展.
	 * 
	 * @param configuration
	 *            配置.
	 * @param locator
	 *            对应创建
	 */
	public static void contributeRemoteServiceCallerSource(
			MappedConfiguration<String, RemoteServiceCaller> configuration,
			ObjectLocator locator) {
		configuration.add("hessian", locator
				.autobuild(HessianRemoteServiceCaller.class));
	}

	/**
	 */
	public static RemoteServiceCallerSource build(
			final Map<String, RemoteServiceCaller> configuration) {
		return new RemoteServiceCallerSource() {
			public RemoteServiceCaller get(String remoteType) {
				return configuration.get(remoteType);
			}
		};
	}

	public void contributeComponentEventResultProcessor(
			MappedConfiguration<Class, ComponentEventResultProcessor> configuration,RequestGlobals requestGlobals) {

		configuration.add(RemoteResponse.class,
				new RemoteResponseResultProcessor(requestGlobals));
	}

	@Match("EntityService")
	public static <T> T decorateTransactionally(
			HibernateTransactionDecorator decorator, Class<T> serviceInterface,
			T delegate, String serviceId) {
		return decorator.build(serviceInterface, delegate, serviceId);
	}

	/**
	 * 构建基于HTML的PageTemplateLocator.
	 */
	@Marker(HtmlTemplateProvider.class)
	public PageTemplateLocator buildPageTemplateLocatorWithHtml(
			@ContextProvider
			AssetFactory contextAssetFactory,

			ComponentClassResolver componentClassResolver,
			@Symbol(CornerConstants.ENABLE_HTML_TEMPLATE)
			boolean enableHtmlTemplate) {
		return new PageTemplateLocatorWithHtml(contextAssetFactory
				.getRootResource(), componentClassResolver, enableHtmlTemplate);
	}

	/**
	 * 静止浏览静态模板资源
	 * 
	 * @param configuration
	 *            RequestFilter Configuration
	 * @param context
	 *            webapp context
	 * @param enableHtmlTemplate
	 *            是否打开html模板
	 * @since 0.0.2
	 */
	public void contributeRequestHandler(
			OrderedConfiguration<RequestFilter> configuration, Context context,

			@Symbol(CornerConstants.ENABLE_HTML_TEMPLATE)
			boolean enableHtmlTemplate,@Symbol(CornerConstants.ENABLE_HTML_ACCESS) boolean enableHtmlAccess) {
		if (enableHtmlTemplate && !enableHtmlAccess) {
			RequestFilter foridViewHtmlTempalteFilter = new ForbidViewHtmlTemplate(
					context);
			configuration.add("ForidViewHtmlTempalteFilter",
					foridViewHtmlTempalteFilter, "before:StaticFiles");
		}
	}

	/**
	 * 复写系统提供的Page Template Locator.
	 * 
	 * @param configuration
	 *            配置文件
	 * @param htmlTemplateLocator
	 *            html template locator.
	 * @since 0.0.1
	 */
	public static void contributeAliasOverrides(
			Configuration<AliasContribution<PageTemplateLocator>> configuration,
			@HtmlTemplateProvider
			PageTemplateLocator htmlTemplateLocator) {
		configuration.add(AliasContribution.create(PageTemplateLocator.class,
				htmlTemplateLocator));
	}

    //add PaginationList --> List type coercer
    public static void contributeTypeCoercer(Configuration<CoercionTuple> configuration,

                                             @Builtin final
                                             TypeCoercer coercer)

    {
        add(configuration, PaginationList.class, JSONObject.class,
                new Coercion<PaginationList, JSONObject>()
                {
                    public JSONObject coerce(PaginationList input)
                    {
                        return input.options();
                    }
                });
        add(configuration, PaginationList.class, List.class,
                new Coercion<PaginationList, List>()
                {
                    public List coerce(PaginationList input)
                    {
                        return coercer.coerce(input.collectionObject(),List.class);
                    }
                });
    }
    private static <S, T> void add(Configuration<CoercionTuple> configuration, Class<S> sourceType, Class<T> targetType,
                                   Coercion<S, T> coercion)
    {
        CoercionTuple<S, T> tuple = new CoercionTuple<S, T>(sourceType, targetType, coercion);

        configuration.add(tuple);
    }


}
