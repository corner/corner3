package corner.services.cache.memcache.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Memcached Socket Pool的配置
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
@XmlRootElement(name = "pool")
public class MemcachePoolConfig {
	/** pool的名称 * */
	private String name;

	/** 如果server挂了,是否尝试去寻找其它的server * */
	private boolean failover = true;

	/** 是否使用nagle 算法* */
	private boolean nagle = false;

	/** 是否进行活跃检查 * */
	private boolean aliveCheck = true;

	/** 初始的连接数 * */
	private int initConnections = 1;

	/** 最小的连接数 * */
	private int minConnections = 3;

	/** 最大的连接数 * */
	private int maxConnections = minConnections * 3;

	/** 最大的空闲时间,单位毫秒* */
	private int maxIdle = 3 * 1000;

	/** 用于维护的线程时间间隔,单位毫秒 * */
	private int maintSleep;

	/** socket的连接超时 * */
	private int socketTimeout;

	/** socket的读取超时* */
	private int socketReadTimeout;

	/** 服务器列表 * */
	private List<Server> servers;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public boolean isFailover() {
		return failover;
	}

	public void setFailover(boolean failover) {
		this.failover = failover;
	}

	@XmlAttribute
	public boolean isNagle() {
		return nagle;
	}

	public void setNagle(boolean nagle) {
		this.nagle = nagle;
	}

	@XmlAttribute
	public boolean isAliveCheck() {
		return aliveCheck;
	}

	public void setAliveCheck(boolean aliveCheck) {
		this.aliveCheck = aliveCheck;
	}

	@XmlAttribute
	public int getInitConnections() {
		return initConnections;
	}

	public void setInitConnections(int initConnections) {
		this.initConnections = initConnections;
	}

	@XmlAttribute
	public int getMinConnections() {
		return minConnections;
	}

	public void setMinConnections(int minConnections) {
		this.minConnections = minConnections;
	}

	@XmlAttribute
	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	@XmlAttribute
	public int getMaintSleep() {
		return maintSleep;
	}

	public void setMaintSleep(int maintSleep) {
		this.maintSleep = maintSleep;
	}

	@XmlAttribute
	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	@XmlAttribute
	public int getSocketReadTimeout() {
		return socketReadTimeout;
	}

	public void setSocketReadTimeout(int socketReadTimeout) {
		this.socketReadTimeout = socketReadTimeout;
	}
	
	@XmlAttribute
	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	@XmlElement
	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	/**
	 * Memached Server的配置
	 * 
	 * @author dong
	 * @version $Revision$
	 * @since 0.0.2
	 */
	public static class Server {
		/** 服务器的地址 * */
		private String address;
		/** 服务器的端口 * */
		private int port;

		@XmlAttribute
		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		@XmlAttribute
		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

	}


}
