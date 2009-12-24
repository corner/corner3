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
package corner.cache.services.impl.local;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * LocalCache的配置
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
@XmlRootElement(name = "client")
public class LocalCacheItemConfig {
	/** client的名称 * */
	private String name;
	/** 缓存的过期检查的时间间隔 * */
	private int expiryInterval;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public int getExpiryInterval() {
		return expiryInterval;
	}

	public void setExpiryInterval(int expiryInterval) {
		this.expiryInterval = expiryInterval;
	}

}
