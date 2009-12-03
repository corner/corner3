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
package corner.asset.impl;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import corner.asset.AssetConstants;
import corner.asset.StaticAssetUrlCreator;

/**
 * 从指定的域名构造静态资源的URL
 * 
 * 
 * @author dong
 * @version $Revision: 4906 $
 * @since 0.0.2
 */
public class DomainStaticAssetUrlCreatorImpl implements StaticAssetUrlCreator {
	/** 配置domain * */
	public static final String LICHEN_STATICASSET_DOMAINFACTORY_DOMAIN = "corner.staticasset.domainfactory.domain";
	/** 配置是否使用泛域名解析 * */
	public static final String LICHEN_STATICASSET_DOMAINFACTORY_SUPPORT_MUTIL = "corner.staticasset.domainfactory.mutil";
	private final String domain;
	private final boolean supportMutil;
	private final StaticAssetUrlDomainHash hash;
	private final String host;
	private final String port;

	/**
	 * @param domain
	 *            静态资源所在的域名
	 * @param supportMutil
	 *            是否使用泛域名解析
	 */
	public DomainStaticAssetUrlCreatorImpl(@Inject
	@Symbol(LICHEN_STATICASSET_DOMAINFACTORY_DOMAIN)
	String domain, @Inject
	@Symbol(LICHEN_STATICASSET_DOMAINFACTORY_SUPPORT_MUTIL)
	boolean supportMutil, StaticAssetUrlDomainHash hash) {
		this.domain = domain;
		this.supportMutil = supportMutil;
		try {
			URL _url = new URL(this.domain.startsWith("http://") ? this.domain
					: "http://" + this.domain);
			String _host = _url.getHost();
			int _port = _url.getPort();
			this.host = _host;
			this.port = (_port == -1 || _port == 80) ? "" : ":" + _port;
			this.hash = hash;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see corner.asset.impl.StaticAsseUrlFactory#getUrl(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String createUrl(String context,String protocol,String path,String referPath) {
		//仅仅针对default类型的资源
		if(!AssetConstants.DEFAULT_ASSET_TYPE.equals(protocol)){
			return null;
		}
		String _path = path.replaceFirst("^\\.+", "");
		_path = _path.replaceFirst("^/+", "");
		String _host = this.host;
		if (this.supportMutil) {
			_host = this.hash.hash(this.host);
		}
		return String.format("http://%s%s/%s", _host, this.port, _path);
	}
}
