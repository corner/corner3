/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-09-22
 */

package corner.cache.services.impl;

import java.lang.reflect.Method;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;

import corner.cache.annotations.Cacheable;
import corner.cache.annotations.Memcache;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheProcessorSource;
import corner.cache.services.CacheableAdvisor;
import corner.cache.services.CacheableDefinitionParser;

/**
 * implement cacheableAdvisor
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class CacheableAdvisorImpl implements CacheableAdvisor {
	private CacheManager cacheManager;
	private CacheableDefinitionParser parser;
	private CacheProcessorSource cacheProcessorSource;

	public CacheableAdvisorImpl(
			CacheProcessorSource cacheProcessorSource,
			@Memcache CacheManager cacheManager,
			CacheableDefinitionParser parser) {
		this.cacheManager = cacheManager;
		this.parser = parser;
		this.cacheProcessorSource = cacheProcessorSource;
	}
	@Override
	public void addCacheableAdvice(MethodAdviceReceiver receiver) {
		for (Method m : receiver.getInterface().getMethods()) {
			Cacheable c = m.getAnnotation(Cacheable.class);
			if (c != null) {
					receiver.adviseMethod(m, new CacheableAdvice(m,cacheManager,parser,cacheProcessorSource));
			}
		}
	}
}
