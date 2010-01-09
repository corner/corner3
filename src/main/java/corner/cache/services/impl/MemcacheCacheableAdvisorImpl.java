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
 * memcache advisor
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class MemcacheCacheableAdvisorImpl  implements CacheableAdvisor {
	private CacheManager cacheManager;
	private CacheableDefinitionParser parser;
	private CacheProcessorSource cacheProcessorSource;

	public MemcacheCacheableAdvisorImpl(
			CacheProcessorSource cacheProcessorSource,
			@Memcache
			CacheManager cacheManager,
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
