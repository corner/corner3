package lichen.services.cache.local.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * LocalCache的配置
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
@XmlRootElement(name = "localcache")
public class LocalCacheConfig {
	private List<LocalCacheItemConfig> cache;

	@XmlElement
	public List<LocalCacheItemConfig> getCache() {
		return cache;
	}

	public void setCache(List<LocalCacheItemConfig> cache) {
		this.cache = cache;
	}

}
