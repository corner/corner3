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
package corner.cache;


import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.slf4j.Logger;

import com.meetup.memcached.ErrorHandler;

import corner.cache.local.impl.LocalCacheConfig;
import corner.cache.local.impl.LocalCacheManagerImpl;
import corner.cache.memcache.impl.ErrorHandlerImpl;
import corner.cache.memcache.impl.MemcacheConfig;
import corner.cache.memcache.impl.MemcachedCacheManager;
import corner.config.services.ConfigurationSource;

/**
 * Cache的配置,目前提供Memcache和LocalCache的配置:
 * <ul>
 * <li>Memcache memcache-config.xml </li>
 * <li>LocalCache localcache-config.xml </li>
 * </ul>
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class CacheModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(ErrorHandler.class, ErrorHandlerImpl.class);
	}

	/**
	 * 构造基于Memcached的CacheManager,并启动该manager
	 * 
	 * @param configSource
	 *            配置源
	 * @param logger
	 *            日志实例
	 * @param errorHandler
	 *            访问Memcache时的异常处理器
	 * @return
	 * @since 0.0.2
	 */
	public CacheManager buildMemcachedCacheManager(
			ConfigurationSource configSource, Logger logger,
			ErrorHandler errorHandler) {
		MemcacheConfig _config = configSource
				.getServiceConfig(MemcacheConfig.class);
		CacheManager _manager = new MemcachedCacheManager(_config, logger,
				errorHandler);
		_manager.start();
		return _manager;
	}

	/**
	 * 构造基于JVM本机内存的CacheManager,并启动该Manager
	 * 
	 * @param configSource
	 * @return
	 * @since 0.0.2
	 */
	public CacheManager buildLocalCacheManager(ConfigurationSource configSource) {
		LocalCacheConfig _config = configSource
				.getServiceConfig(LocalCacheConfig.class);
		CacheManager _manager = new LocalCacheManagerImpl(_config);
		_manager.start();
		return _manager;
	}

	/**
	 * 向ServiceConfigSource增加缓存的配置文件,目前增加的配置文件有:
	 * <ul>
	 * <li>memcache-config.xml 对Memcached Cache的配置</li>
	 * <li>localcache-config.xml 对Local Cache的配置</li>
	 * </ul>
	 * 以上配置文件应该位于classpath中
	 * 
	 * @param configuration
	 * @since 0.0.2
	 */
	public void contributeConfigurationSource(
			MappedConfiguration<Class<?>, Resource> configuration) {
		// 增加Memcache的配置文件
		configuration.add(MemcacheConfig.class, new ClasspathResource(
				"memcache-config.xml"));
		// 增加LocalCache的配置文件
		configuration.add(LocalCacheConfig.class, new ClasspathResource(
				"localcache-config.xml"));
	}
}
