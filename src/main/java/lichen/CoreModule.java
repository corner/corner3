/* 
 * Copyright 2008 The Lichen Team.
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
package lichen;

import java.util.Map;

import lichen.bindings.FormatterModule;
import lichen.internal.services.CookiePersistentFieldStrategy;
import lichen.internal.services.FlashBindingFactory;
import lichen.internal.services.FlashFacadeImpl;
import lichen.internal.services.HessianRemoteServiceCaller;
import lichen.internal.services.RemoteCallServiceLifecycle;
import lichen.internal.services.RemoteResponseResultProcessor;
import lichen.internal.services.ServiceLocatorDelegateImpl;
import lichen.livevalidator.ValidationModule;
import lichen.protobuf.ProtocolBuffersModule;
import lichen.services.EntityService;
import lichen.services.FlashFacade;
import lichen.services.HtmlTemplateProvider;
import lichen.services.RemoteResponse;
import lichen.services.RemoteServiceCaller;
import lichen.services.RemoteServiceCallerSource;
import lichen.services.ServiceLocatorDelegate;
import lichen.services.asset.StaticAssetModule;
import lichen.services.config.ServiceConfigModule;
import lichen.services.fckeditor.FckeditorModule;
import lichen.services.hadoop.HadoopModule;
import lichen.services.impl.EntityServiceImpl;
import lichen.services.impl.ForbidViewHtmlTemplate;
import lichen.services.impl.PageTemplateLocatorWithHtml;
import lichen.services.migration.MigrationModule;
import lichen.services.payment.PaymentModule;
import lichen.services.security.SecurityModule;
import lichen.services.tree.TreeModule;
import lichen.transform.PageRedirectWorker;

import org.apache.tapestry5.hibernate.HibernateTransactionDecorator;
import org.apache.tapestry5.internal.services.LinkFactory;
import org.apache.tapestry5.internal.services.PageTemplateLocator;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.ServiceLifecycle;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AliasContribution;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.ContextProvider;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestGlobals;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 定义了Lichen的核心module
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

	private RequestGlobals requestGlobals;

	public CoreModule(RequestGlobals requestGlobals) {
		this.requestGlobals = requestGlobals;

	}

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
		configuration.add(new LibraryMapping("lichen", "lichen"));
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
			ComponentClassResolver resolver, LinkFactory linkFactory) {
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
		configuration.add(LichenConstants.REMOTE_SCOPE, locator
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
		configuration.add(LichenConstants.REMOTE_SERVER_URL,
				"http://localhost:5180/lichen/remote.hessian");
		configuration.add(LichenConstants.DEFAULT_CALLER, "hessian");
		configuration.add(LichenConstants.ENABLE_REMOTE_CALL, "false");

		configuration.add(LichenConstants.ENABLE_HTML_TEMPLATE, "false");
		configuration.add(LichenConstants.ENABLE_HTML_ACCESS, "true");

		// 配置默认的资源引用类型为classpath
		configuration.add(LichenConstants.ASSET_TYPE, "classpath");
		configuration.add(LichenConstants.COMPOENT_TABLEVIEW_ROWS_PERPAGE, "15");
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

	@SuppressWarnings("unchecked")
	public void contributeComponentEventResultProcessor(
			MappedConfiguration<Class, ComponentEventResultProcessor> configuration) {

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
			@Symbol(LichenConstants.ENABLE_HTML_TEMPLATE)
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

			@Symbol(LichenConstants.ENABLE_HTML_TEMPLATE)
			boolean enableHtmlTemplate,@Symbol(LichenConstants.ENABLE_HTML_ACCESS) boolean enableHtmlAccess) {
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



}
