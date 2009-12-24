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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
@XmlRootElement(name = "memcache")
public class MemcacheConfig {
	private List<MemcacheClientConfig> client;
	private List<MemcachePoolConfig> pool;

	@XmlElement
	public List<MemcacheClientConfig> getClient() {
		return client;
	}

	public void setClient(List<MemcacheClientConfig> client) {
		this.client = client;
	}

	@XmlElement
	public List<MemcachePoolConfig> getPool() {
		return pool;
	}

	public void setPool(List<MemcachePoolConfig> pool) {
		this.pool = pool;
	}
}
