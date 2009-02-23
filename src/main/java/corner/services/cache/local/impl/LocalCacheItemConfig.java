package corner.services.cache.local.impl;

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
