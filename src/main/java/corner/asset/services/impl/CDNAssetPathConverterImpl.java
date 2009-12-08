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
package corner.asset.services.impl;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.RequestConstants;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetPathConverter;

import corner.asset.StaticAssetSymbols;

/**
 * 支持CDN方式的路径转换
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CDNAssetPathConverterImpl implements AssetPathConverter {
	private String domain;
	private boolean supportMutil;
	private String host;
	private String port;
	private StaticAssetUrlDomainHash hash;
	private String contextPrefix;
	private String applicationVersion;

	public CDNAssetPathConverterImpl(@Inject
			@Symbol(StaticAssetSymbols.DOMAIN_NAME)
			String domain, @Inject
			@Symbol(StaticAssetSymbols.DOMAIN_SUPPORT_MUTIL)
			boolean supportMutil,
			 @Inject @Symbol(SymbolConstants.APPLICATION_VERSION)
             String applicationVersion,
			StaticAssetUrlDomainHash hash){
		
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
		this.applicationVersion = applicationVersion;
		this.contextPrefix = RequestConstants.ASSET_PATH_PREFIX + RequestConstants.CONTEXT_FOLDER
        + applicationVersion + "/";
	}
	/**
	 * @see org.apache.tapestry5.services.AssetPathConverter#convertAssetPath(java.lang.String)
	 */
	@Override
	public String convertAssetPath(String assetPath) {
		if(assetPath.startsWith(contextPrefix)){//context path
			String _path=assetPath.substring(contextPrefix.length());
			_path = _path.replaceFirst("^\\.+", "");
			_path = _path.replaceFirst("^/+", "");
			String _host = this.host;
			if (this.supportMutil) {
				_host = this.hash.hash(this.host);
			}
			return String.format("http://%s%s/%s?v=%s", _host, this.port, _path,this.applicationVersion);
		}
		return assetPath;
	}

	/**
	 * @see org.apache.tapestry5.services.AssetPathConverter#isInvariant()
	 */
	@Override
	public boolean isInvariant() {
		return true;
	}

}
