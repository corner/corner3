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
package corner.cache.services.impl.memcache;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Memcached的Client配置
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
@XmlRootElement(name = "client")
public class MemcacheClientConfig {
	/** client的名称 * */
	private String name;
	/** 使用的pool名称 * */
	private String poolName;
	/** 是否使用压缩 * */
	private boolean compressEnable = true;
	/** 默认的编码 * */
	private String defaultEncoding = "UTF-8";
	/** 是否支持clear操作 * */
	private boolean supportClear = false;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public boolean isCompressEnable() {
		return compressEnable;
	}

	public void setCompressEnable(boolean compressEnable) {
		this.compressEnable = compressEnable;
	}

	@XmlAttribute
	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	@XmlAttribute
	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	/**
	 * @return the supportClear
	 */
	@XmlAttribute
	public boolean isSupportClear() {
		return supportClear;
	}

	/**
	 * @param supportClear
	 *            the supportClear to set
	 */
	public void setSupportClear(boolean supportClear) {
		this.supportClear = supportClear;
	}
}
