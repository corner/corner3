/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: CacheableAdvice.java 6733 2009-12-07 05:06:15Z jcai $
 * created at:2009-09-22
 */

package corner.cache.services.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.ioc.MethodAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corner.cache.CacheConstants;
import corner.cache.services.Cache;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheProcessor;
import corner.cache.services.CacheProcessorSource;
import corner.cache.services.CacheableDefinitionParser;

/**
 * 针对Cacheable的Advice
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 6733 $
 * @since 0.1
 */
public class CacheableAdvice implements MethodAdvice {

	private Method method;
	private final static Logger logger = LoggerFactory
			.getLogger(CacheableAdvice.class);
	private Cache cache;
	private CacheableDefinitionParser parser;
	private CacheProcessorSource cacheProcessorSource;

	public CacheableAdvice(
			Method method,
			CacheManager cacheManager,
			CacheableDefinitionParser parser,
			CacheProcessorSource cacheProcessorSource
			) {
		this.method = method;
        cache = cacheManager.getCache(CacheConstants.ENTITY_CACHE_NAME);
        this.parser = parser;
        this.cacheProcessorSource = cacheProcessorSource;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void advise(Invocation invocation) {
		try {
			// 先得到缓存的KEY
			String cacheKey = this.parser.parseAsKey(invocation,method);

			// 从缓存中读取
			Object object = cache.get(cacheKey);

			// 如果没有，则产生
			if (object == null) {
				logger.debug("cache object is null,so execute origion method");
				executeAndCache(invocation, cacheKey, cache);
			
			} else {
				restoreObjectFromCache(invocation, object);
			}
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	/**
	 * 从缓存对象中读取对应的实体
	 * 
	 * @param invocation
	 * @param object
	 * @since 0.0.2
	 */
	@SuppressWarnings("unchecked")
	private void restoreObjectFromCache(Invocation invocation, Object object) {
		if (logger.isDebugEnabled()) {
			logger.debug("get cache object [" + object + "]");
		}
		List list = (List) object;
		Class<?> type = invocation.getResultType();
		CacheProcessor<?> processor = this.cacheProcessorSource.getProcessor(type);
		Object obj = processor.fromCache(list, method);
		invocation.overrideResult(obj);
	}

	/**
	 * 执行方法本身，然后缓存结果
	 * 
	 * @param invocation
	 *            invocation object
	 * @param cacheKey
	 *            cacheKey
	 * @param cache
	 *            缓存对象
	 * @since 0.0.2
	 */
	@SuppressWarnings("unchecked")
	private void executeAndCache(Invocation invocation, String cacheKey,
			Cache cache) {
		Object object;
		invocation.proceed();
		object = invocation.getResult();
		if(object == null){//NULL不进行缓存
			return;
		}
		Class<?> returnType = method.getReturnType();
		CacheProcessor<Object> processor = (CacheProcessor<Object>) this.cacheProcessorSource.getProcessor(returnType);
		List<Object> list = processor.toCache(object);
		//重新读取对象
		restoreObjectFromCache(invocation, list);
		// 放入缓存
		cache.put(cacheKey, list);
	}
}