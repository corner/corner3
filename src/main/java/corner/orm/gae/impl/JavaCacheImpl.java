/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-12-7
 */
package corner.orm.gae.impl;

import corner.cache.services.Cache;

/**
 * java cache implemention
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public abstract class JavaCacheImpl<K,V> implements Cache<K,V>{
	private javax.cache.Cache delegateCache;
	private String name;

	public JavaCacheImpl(javax.cache.Cache delegateCache,String name){
		this.delegateCache = delegateCache;
		this.name =name;
	}

	@Override
	public boolean clear() {
		delegateCache.clear();
		return true;
	}

	@Override
	public V get(K key) {
		return (V) delegateCache.get(key);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean put(K key, V value) {
		delegateCache.put(key, value);
		return true;
	}

	@Override
	public boolean put(K key, V value, int ttl) {
		delegateCache.put(key, value);
		return true;
	}

	@Override
	public boolean remove(K key) {
		delegateCache.remove(key);
		return true;
	}
}
