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
package corner.services.asset;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.Request;

/**
 * StaticModule用于提供默认的配置
 * 
 * @author dong
 * @version $Revision: 4906 $
 * @since 0.0.1
 */
public class StaticAssetModule {
	private static final String LOCAL = "local";
	private static final String DOMAIN = "domain";
	private static final String LICHEN_STATICASSET_URLFACTORY_TYPE = "corner.staticasset.urlfactory.type";
	private final Request request;

	public StaticAssetModule(Request request) {
		this.request = request;
	}

	public static void bind(ServiceBinder binder) {
		binder.bind(StaticAsseUrlFactory.class,
				StaticAssetUrlFactoryAdapter.class).withId(
				"StaticAssetUrlFactoryAdapter");
		binder.bind(StaticAssetUrlDomainHash.class,
				StaticAssetUrlDomainSequenceHash.class);

	}

	public static void contributeFactoryDefaults(
			MappedConfiguration<String, String> configuration) {
		configuration.add(LICHEN_STATICASSET_URLFACTORY_TYPE, LOCAL);
		// 默认配置为不支持泛域名解析
		configuration
				.add(
						StaticAssetUrlFactoryDomainImpl.LICHEN_STATICASSET_DOMAINFACTORY_SUPPORT_MUTIL,
						"false");
		// 配置默认的域名散列个数为3个
		configuration
				.add(
						StaticAssetUrlDomainSequenceHash.LICHEN_STATICASSET_DOMAINFACTORY_SEHASH_COUNT,
						"3");
	}

	/**
	 * 建立StaticAssetFactory的实例
	 * 
	 * @param type
	 * @param domainFactory
	 * @return
	 */
	@Marker(StaticProvider.class)
	public AssetFactory buildStaticAssetFactory(
			StaticAsseUrlFactory domainFactory) {
		return new StaticAssetFactory(this.request, domainFactory);
	}

	/**
	 * 为Asset增加一种新的类型:static,使用样例:<code>@IncludeStylesheet({ "static:css/style2.css"})</code>
	 * 
	 * @param configuration
	 * @param staticAssetFactory
	 */
	public void contributeAssetSource(
			MappedConfiguration<String, AssetFactory> configuration,
			@StaticProvider
			AssetFactory staticAssetFactory) {
		configuration.add("static", staticAssetFactory);
	}

	/**
	 * 配置StaticAssetUrlFactoryAdapter处理的类型 目前支持的类型有: file 本地文件 hdfs hdfs文件存取
	 * default 默认使用本地文件 file方式
	 * 
	 * @param configuration
	 * @param locator
	 * @param factoryType
	 *            urlFactory的类型
	 */
	public void contributeStaticAssetUrlFactoryAdapter(
			MappedConfiguration<String, StaticAsseUrlFactory> configuration,
			ObjectLocator locator, @Inject
			@Symbol(LICHEN_STATICASSET_URLFACTORY_TYPE)
			String factoryType) {
		configuration.add("hdfs", locator
				.autobuild(StaticAssetUrlFactoryHadoopImpl.class));
		if (LOCAL.equals(factoryType)) {
			configuration.add("file", locator
					.autobuild(StaticAssetUrlFactoryLocalImpl.class));
			configuration.add("default", locator
					.autobuild(StaticAssetUrlFactoryLocalImpl.class));
		} else if (DOMAIN.equals(factoryType)) {
			configuration.add("file", locator
					.autobuild(StaticAssetUrlFactoryDomainImpl.class));
			configuration.add("default", locator
					.autobuild(StaticAssetUrlFactoryDomainImpl.class));
		}
	}
}
