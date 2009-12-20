/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: CacheableAdvisorImpl.java 6733 2009-12-07 05:06:15Z jcai $
 * created at:2009-09-22
 */

package corner.cache.services.impl;

import java.lang.reflect.Method;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ValueEncoderSource;

import corner.cache.annotations.Cacheable;
import corner.cache.annotations.Memcache;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheableAdvisor;
import corner.cache.services.CacheableDefinitionParser;
import corner.orm.services.EntityService;

/**
 * implement cacheableAdvisor
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 6733 $
 * @since 0.1
 */
public class CacheableAdvisorImpl implements CacheableAdvisor {

	private TypeCoercer typeCoercer;
	private ValueEncoderSource valueEncoderSource;
	private EntityService entityService;
	private PropertyAccess propertyAccess;
	private CacheManager cacheManager;
	private CacheableDefinitionParser parser;

	public CacheableAdvisorImpl(
			TypeCoercer typeCoercer, 
			EntityService entityService,
			ValueEncoderSource valueEncoderSource,
			PropertyAccess propertyAccess,
			@Memcache CacheManager cacheManager,
			CacheableDefinitionParser parser) {
		this.typeCoercer = typeCoercer;
		this.valueEncoderSource = valueEncoderSource;
		this.entityService = entityService;
		this.propertyAccess = propertyAccess;
		this.cacheManager = cacheManager;
		this.parser = parser;
	}

	@Override
	public void addCacheableAdvice(MethodAdviceReceiver receiver) {
		for (Method m : receiver.getInterface().getMethods()) {
			Cacheable c = m.getAnnotation(Cacheable.class);
			if (c != null) {
					receiver.adviseMethod(m, new CacheableAdvice(m,
							typeCoercer,  entityService, valueEncoderSource,propertyAccess,cacheManager,parser));
				
			}
		}
	}
}
