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

import java.util.Iterator;

import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.internal.services.PageTemplateLocator;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.Builtin;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.AliasContribution;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ContextProvider;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;

import corner.asset.StaticAssetModule;
import corner.config.ConfigurationModule;
import corner.encrypt.EncryptModule;
import corner.hadoop.HadoopModule;
import corner.livevalidator.ValidationModule;
import corner.orm.OrmModule;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.payment.PaymentModule;
import corner.protobuf.ProtocolBuffersModule;
import corner.security.SecurityModule;
import corner.tapestry.bindings.BindingModule;
import corner.tapestry.fckeditor.FckeditorModule;
import corner.tapestry.persistent.CookiePersistentFieldStrategy;
import corner.tapestry.services.HtmlTemplateProvider;
import corner.tapestry.services.override.PageTemplateLocatorWithHtml;
import corner.tapestry.transform.PageRedirectWorker;
import corner.template.TemplateModule;
import corner.transaction.TransactionModule;

/**
 * 定义了Corner的核心module
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 3041 $
 * @since 0.0.1
 */
@SubModule( { ValidationModule.class, StaticAssetModule.class,
		SecurityModule.class, ProtocolBuffersModule.class,
		FckeditorModule.class, PaymentModule.class,
		HadoopModule.class, ConfigurationModule.class,BindingModule.class,TransactionModule.class,
		EncryptModule.class,TemplateModule.class,OrmModule.class})
public class CoreModule {

	/**
	 * 绑定使用的service.
	 * 
	 * @param binder
	 *            Service Binder
	 * @see ServiceBinder
	 */
	public static void bind(ServiceBinder binder) {
	}

	/**
	 * 组件类。
	 * 
	 * @param configuration
	 *            library mapping configruation
	 */
	public static void contributeComponentClassResolver(
			Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("corner", "corner.tapestry"));
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

	

	// 扩展一个flash前缀的binding
	public static void contributeBindingSource(
			MappedConfiguration<String, BindingFactory> configuration,
			ObjectLocator locator) {
		
	}

	// 扩展一个可以在客户端进行Persist的保存策略
	public void contributePersistentFieldManager(
			MappedConfiguration<String, PersistentFieldStrategy> configuration,
			Request request, ObjectLocator locator) {
		configuration.add("cookie", locator
				.autobuild(CookiePersistentFieldStrategy.class));

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
    	 add(configuration, PaginationList.class, PaginationOptions.class,
                 new Coercion<PaginationList, PaginationOptions>()
                 {
                     public PaginationOptions coerce(PaginationList input)
                     {
                         return input.options();
                     }
                 });
    	 add(configuration,Iterator.class,Iterable.class,
                 new Coercion<Iterator, Iterable>()
                 {
                     public Iterable coerce(final Iterator input)
                     {
                         return new Iterable(){

							@Override
							public Iterator iterator() {
								return input;
							}};
                     }
                 });
         add(configuration, PaginationList.class, Iterable.class,
                new Coercion<PaginationList, Iterable>()
                {
                    public Iterable coerce(PaginationList input)
                    {
                        return coercer.coerce(input.collectionObject(),Iterable.class);
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
