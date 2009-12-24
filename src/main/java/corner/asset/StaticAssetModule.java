/* 
 * Copyright 2009 The Corner Team.
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

import org.apache.tapestry5.internal.services.IdentityAssetPathConverter;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetPathConverter;

import corner.asset.services.impl.CDNAssetPathConverterImpl;
import corner.asset.services.impl.StaticAssetUrlDomainHash;
import corner.asset.services.impl.StaticAssetUrlDomainSequenceHash;

/**
 * StaticModule用于提供默认的配置
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.1
 */
public class StaticAssetModule {
	public static void bind(ServiceBinder binder){
		binder.bind(StaticAssetUrlDomainHash.class,StaticAssetUrlDomainSequenceHash.class);
		
	}
	public static AssetPathConverter buildCDNAssetPathConverter(ObjectLocator locator,
			@Inject
			@Symbol(StaticAssetSymbols.DOMAIN_ASSET_MODE)
			boolean assetModel
			){
		if(assetModel){
			return locator.autobuild(CDNAssetPathConverterImpl.class);
		}else{
			return locator.autobuild(IdentityAssetPathConverter.class);
		}
	}
	public static void contributeServiceOverride(MappedConfiguration<Class,Object> configuration,
			@Local AssetPathConverter converter,
			ObjectLocator locator
			)
	  {
			configuration.add(AssetPathConverter.class,converter);
	  }

	public static void contributeFactoryDefaults(
			MappedConfiguration<String, String> configuration) {
		//是否为domain访问
		configuration.add(StaticAssetSymbols.DOMAIN_ASSET_MODE, "false");
		// 默认配置为不支持泛域名解析
		configuration.add(StaticAssetSymbols.DOMAIN_SUPPORT_MUTIL,"false");
		// 配置默认的域名散列个数为3个
		configuration.add(StaticAssetSymbols.DOMAIN_SEHASH_COUNT,"3");
	}
}
