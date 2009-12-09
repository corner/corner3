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
package corner.asset;

import java.util.List;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.AssetFactory;

import corner.asset.annotations.StaticAssetProvider;
import corner.asset.services.StaticAssetUrlCreator;
import corner.asset.services.impl.DomainStaticAssetUrlCreatorImpl;
import corner.asset.services.impl.HadoopStaticAssetUrlCreatorImpl;
import corner.asset.services.impl.LocalStaticAssetUrlCreatorImpl;
import corner.asset.services.impl.StaticAssetFactory;
import corner.asset.services.impl.StaticAssetUrlDomainHash;
import corner.asset.services.impl.StaticAssetUrlDomainSequenceHash;

/**
 * StaticModule用于提供默认的配置
 * 
 * @author dong
 * @version $Revision: 4906 $
 * @since 0.0.1
 */
public class StaticAssetModule {
	public static void bind(ServiceBinder binder) { binder.bind(StaticAssetUrlDomainHash.class,
				StaticAssetUrlDomainSequenceHash.class);
		binder.bind(AssetFactory.class,StaticAssetFactory.class);
	}
	public StaticAssetUrlCreator buildStaticAssetUrlCreator(List<StaticAssetUrlCreator> configuration,ChainBuilder chainBuilder)
    {
		 return chainBuilder.build(StaticAssetUrlCreator.class, configuration);
    }
	/*
	public static AssetPathConverter buildCDNAssetPathConverter(ObjectLocator locator){
		return locator.autobuild(CDNAssetPathConverterImpl.class);
	}
	public static void contributeServiceOverride(MappedConfiguration<Class,Object> configuration,
			@Local AssetPathConverter converter,
			@Inject
			@Symbol(StaticAssetSymbols.DOMAIN_ASSET_MODE)
			boolean assetModel)
	  {
		if(assetModel){
			configuration.add(AssetPathConverter.class,converter);
		}
	  }
	  */

	public static void contributeFactoryDefaults(
			MappedConfiguration<String, String> configuration) {
		//是否为domain访问
		configuration.add(StaticAssetSymbols.DOMAIN_ASSET_MODE, "false");
		// 默认配置为不支持泛域名解析
		configuration.add(StaticAssetSymbols.DOMAIN_SUPPORT_MUTIL,"false");
		// 配置默认的域名散列个数为3个
		configuration.add(StaticAssetSymbols.DOMAIN_SEHASH_COUNT,"3");
	}

	

	/**
	 * 为Asset增加一种新的类型:static,使用样例:<code>@IncludeStylesheet({ "static:css/style2.css"})</code>
	 * 
	 * @param configuration
	 * @param staticAssetFactory
	 */
	public void contributeAssetSource(
			MappedConfiguration<String, AssetFactory> configuration,
			@StaticAssetProvider
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
	public void contributeStaticAssetUrlCreator(
			OrderedConfiguration<StaticAssetUrlCreator> configuration,
			ObjectLocator locator, @Inject
			@Symbol(StaticAssetSymbols.DOMAIN_ASSET_MODE)
			boolean isDomain) {
		configuration.add("hdfs",locator
				.autobuild(HadoopStaticAssetUrlCreatorImpl.class));
		if (isDomain) {
			configuration.add("default", locator
					.autobuild(DomainStaticAssetUrlCreatorImpl.class));
		}else{
			configuration.add("default", locator
					.autobuild(LocalStaticAssetUrlCreatorImpl.class));
		}
	}
}
