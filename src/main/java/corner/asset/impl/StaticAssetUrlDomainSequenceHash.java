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

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.annotations.Symbol;

import corner.asset.StaticAssetSymbols;

/**
 * 从0开如,按数字的顺序生成域名的散列,每线程一个实例
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
@Scope(org.apache.tapestry5.ioc.ScopeConstants.PERTHREAD)
public class StaticAssetUrlDomainSequenceHash implements
		StaticAssetUrlDomainHash {
	private final int count;
	private int value;

	/**
	 * 散列域名的个数
	 * 
	 * @param count
	 */
	public StaticAssetUrlDomainSequenceHash(@Inject
	@Symbol(StaticAssetSymbols.DOMAIN_SEHASH_COUNT)
	int count) {
		if (count <= 0) {
			throw new IllegalArgumentException(
					"The  count must be greater than 0.");
		}
		this.count = count;
		this.value = 0;
	}

	/**
	 * @see corner.asset.impl.impl.StaticAssetUrlDomainHash#hash(java.lang.String)
	 */
	@Override
	public String hash(String domain) {
		String _host = domain;
		int _index = domain.indexOf('.');
		if (_index > 0) {
			int _randHash = (this.value++) % this.count;
			String _firstPart = domain.substring(0, _index);
			_host = _firstPart + _randHash + domain.substring(_index);
		}
		return _host;
	}

}
