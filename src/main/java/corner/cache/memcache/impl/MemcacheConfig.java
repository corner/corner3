package corner.cache.memcache.impl;

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
