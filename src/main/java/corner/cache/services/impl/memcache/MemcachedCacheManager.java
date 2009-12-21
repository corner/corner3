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
package corner.cache.services.impl.memcache;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.meetup.memcached.ErrorHandler;
import com.meetup.memcached.MemCachedClient;
import com.meetup.memcached.SockIOPool;

import corner.cache.services.Cache;
import corner.cache.services.CacheManager;
import corner.cache.services.impl.memcache.MemcachePoolConfig.Server;


/**
 * Memcahed的CacheManager实现
 * <p>
 * 实现了一个key为String，值为Object的缓存管理
 * </p>
 * 
 * @see CacheManager
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class MemcachedCacheManager implements CacheManager {
	private final MemcacheConfig config;
	private final ErrorHandler errorHandler;
	private final Logger logger;
	private final ConcurrentMap<String, SockIOPool> pools = new ConcurrentHashMap<String, SockIOPool>();
	private final ConcurrentMap<String, MemcacheCacheImpl<Object>> caches = new ConcurrentHashMap<String, MemcacheCacheImpl<Object>>();

	public MemcachedCacheManager(MemcacheConfig config, Logger logger,
			ErrorHandler errorHandler) {
		this.config = config;
		this.logger = logger;
		this.errorHandler = errorHandler;
	}

	/**
	 * @see corner.cache.services.CacheManager#getCache(java.lang.String)
	 */
	@Override
	public Cache<String, Object> getCache(String name) {
		Cache<String, Object> _cache = this.caches.get(name);
		if (_cache == null) {
			_cache = this.caches.get("default");
		}
		return _cache;
	}

	/**
	 * @see corner.cache.services.CacheManager#start()
	 */
	@Override
	public void start() {
		// 初始化pool
		List<MemcachePoolConfig> _pools = this.config.getPool();
		if (_pools == null || _pools.isEmpty()) {
			if (logger.isWarnEnabled()) {
				logger.warn("No pool config");
			}
			return;
		}
		for (MemcachePoolConfig _poolConfig : _pools) {
			initPool(_poolConfig);
		}

		// 初始化client
		List<MemcacheClientConfig> _clients = this.config.getClient();
		if (_clients == null || _clients.isEmpty()) {
			if (logger.isWarnEnabled()) {
				logger.warn("No client config");
			}
			return;
		}
		for (MemcacheClientConfig _clientConfig : _clients) {
			initCache(_clientConfig);
		}
	}

	/**
	 * @see corner.cache.services.CacheManager#stop()
	 */
	@Override
	public void stop() {
		for (String _key : this.pools.keySet()) {
			SockIOPool _pool = this.pools.get(_key);
			_pool.shutDown();
		}
		this.pools.clear();
	}

	/**
	 * 根据MemcachePoolConfig的配置初始化SockIOPool
	 * 
	 * @param poolConfig
	 * @return 返回初始化成功的SockIOPool;失败则返回null
	 * @since 0.0.2
	 */
	private SockIOPool initPool(MemcachePoolConfig poolConfig) {
		if (this.pools.get(poolConfig.getName()) != null) {
			if (logger.isWarnEnabled()) {
				logger.warn("Duplicat pool name [" + poolConfig.getName());
			}
			return null;
		}
		/*
		 * server的配置是必须的,其它参数交由SockIOPool进行校验
		 */
		String[] _servers = null;
		List<Server> _serverList = poolConfig.getServers();
		if (_serverList != null && !_serverList.isEmpty()) {
			List<String> _serverStrList = new LinkedList<String>();
			for (Server _server : _serverList) {
				_serverStrList.add(String.format("%s:%s", _server.getAddress(),
						_server.getPort()));
			}
			_servers = new String[_serverList.size()];
			_serverStrList.toArray(_servers);
		}
		if (_servers == null || _servers.length == 0) {
			if (logger.isWarnEnabled()) {
				logger.warn(String.format("No servers config for [%s]",
						poolConfig.getName()));
			}
			return null;
		}
		SockIOPool _pool = SockIOPool.getInstance(poolConfig.getName());
		_pool.setServers(_servers);
		_pool.setInitConn(poolConfig.getInitConnections());
		_pool.setMinConn(poolConfig.getMinConnections());
		_pool.setMaxConn(poolConfig.getMaxConnections());
		_pool.setMaintSleep(poolConfig.getMaintSleep());
		_pool.setSocketConnectTO(poolConfig.getSocketTimeout());
		_pool.setSocketTO(poolConfig.getSocketReadTimeout());
		_pool.setNagle(poolConfig.isNagle());
		_pool.setFailover(poolConfig.isFailover());
		_pool.setAliveCheck(poolConfig.isAliveCheck());
		_pool.setMaxIdle(poolConfig.getMaxIdle());
		_pool.initialize();
		this.pools.put(poolConfig.getName(), _pool);
		return _pool;
	}

	/**
	 * 初始化Cache
	 * 
	 * @param clientConfig
	 * @since 0.0.2
	 */
	private void initCache(MemcacheClientConfig clientConfig) {
		if (this.pools.get(clientConfig.getPoolName()) != null) {
			MemCachedClient _client = new MemCachedClient(clientConfig
					.getPoolName());
			_client.setCompressEnable(clientConfig.isCompressEnable());
			_client.setDefaultEncoding(clientConfig.getDefaultEncoding());
			_client.setErrorHandler(errorHandler);
			MemcacheCacheImpl<Object> _cache = new MemcacheCacheImpl<Object>(
					clientConfig.getName(), _client, clientConfig
							.isSupportClear());
			this.caches.put(clientConfig.getName(), _cache);
		} else {
			if (logger.isErrorEnabled()) {
				logger.error(String.format(
						"No pool name [%s] for client name[%s].", clientConfig
								.getPoolName(), clientConfig.getName()));
			}
		}
	}
}
