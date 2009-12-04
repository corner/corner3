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

/**
 * 资源的一些配置参数
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class StaticAssetSymbols {
	/** 
	 * 配置domain 
	 * 配置可为 http://asset.fepss.com
	 * 
	 **/
	public static final String DOMAIN_NAME = "corner.asset.domain.name";
	/** 配置是否使用泛域名解析 * */
	public static final String DOMAIN_SUPPORT_MUTIL = "corner.asset.domain.mutil";
	/** 配置用于散列的域名个数 * */
	public static final String DOMAIN_SEHASH_COUNT = "corner.asset.domain.sehash.count";
	//静态资源是否为domain方式，默认为false
	public static final String DOMAIN_ASSET_MODE= "corner.asset.domain.model";

}
