/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-12-7
 */
package corner.orm.gae.impl;

import java.util.Collections;

import javax.cache.CacheException;

import org.apache.tapestry5.ioc.annotations.Marker;

import corner.cache.annotations.Memcache;
import corner.cache.services.Cache;
import corner.cache.services.CacheManager;


/**
 * 基于java.cache.manager方式的缓存实现
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
@Marker(Memcache.class)
public class JavaCacheManagerImpl implements CacheManager {

	private JavaCacheImpl<String, Object> cache;

	public JavaCacheManagerImpl(){
		javax.cache.Cache delegateCache;
		try {
			delegateCache = javax.cache.CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
			this.cache = new JavaCacheImpl<String,Object>(delegateCache,"entity");
		} catch (CacheException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * @see corner.cache.services.CacheManager#getCache(java.lang.String)
	 */
	@Override
	public Cache<String, Object> getCache(String name) {
		return cache;
	}

	/**
	 * @see corner.cache.services.CacheManager#start()
	 */
	@Override
	public void start() {
		//do nothing
	}

	/**
	 * @see corner.cache.services.CacheManager#stop()
	 */
	@Override
	public void stop() {
		//do nothing
	}

}
