/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: CacheableAdvisor.java 5915 2009-09-22 04:56:54Z jcai $
 * created at:2009-09-22
 */

package corner.cache.services;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;

/**
 * 
 * cacheable advisor
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 5915 $
 * @since 0.1
 */
public interface CacheableAdvisor {
	/**
	 * 在方法中对缓存进行增强，采用了AOP的方式
	 * @param receiver 方法接受者
	 * @since 0.0.2
	 */
    void addCacheableAdvice(MethodAdviceReceiver receiver);

}
