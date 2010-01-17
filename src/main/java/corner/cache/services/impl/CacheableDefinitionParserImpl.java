/* 
 * Copyright 2009 The Corner Team.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import corner.cache.annotations.CacheKeyParameter;
import corner.cache.annotations.CacheNsParameter;
import corner.cache.annotations.Cacheable;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheStrategy;
import corner.cache.services.CacheStrategySource;
import corner.cache.services.CacheableDefinitionParser;
import corner.cache.services.impl.CacheableDefine.Definition;
import corner.cache.services.impl.CacheableDefine.Definition.Builder;
import corner.orm.services.EntityService;

/**
 * 针对缓存定义的解析
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheableDefinitionParserImpl implements CacheableDefinitionParser {

	private ValueEncoderSource valueEncoderSource;
	private CacheManager cacheManager;
	private CacheStrategySource source;
	private Logger logger = LoggerFactory.getLogger(CacheableDefinitionParserImpl.class);
	private EntityService entityService;

	public CacheableDefinitionParserImpl (ValueEncoderSource valueEncoderSource,
			 CacheManager cacheManager,
			CacheStrategySource source,
			EntityService entityService
			) {
		this.valueEncoderSource = valueEncoderSource;
		this.cacheManager = cacheManager;
		this.source = source;
		this.entityService =entityService;
	}

	/**
	 * @see corner.cache.services.CacheableDefinitionParser#parseAsKey(org.apache.tapestry5.ioc.Invocation, java.lang.reflect.Method, corner.cache.services.CacheManager)
	 */
	public String  parseAsKey(Invocation invocation,Method method){
		Cacheable cacheable = method.getAnnotation(Cacheable.class);
		if(cacheable == null){
			return null;
		}
		Definition define = null;
		//if (define == null) { // 如果没定义，则进行分析
			Builder defineBuilder = CacheableDefine.Definition.newBuilder();
			Annotation[][] parametersAnnotations = method
					.getParameterAnnotations();
			for (int i = 0; i < parametersAnnotations.length; i++) {
				Annotation[] pa = parametersAnnotations[i];
				for (Annotation a : pa) {
					if (a instanceof CacheKeyParameter) {
						defineBuilder.addParameterIndex(i);
					}
				}
			}
			define = defineBuilder.build();
			
		// 得到缓存的参数
		List<String> keyParameter=new ArrayList<String>();
		Object pObj;
		Class pType;
		for (int i = 0; i < define.getParameterIndexCount(); i++) {
			int pIndex = define.getParameterIndex(i);
			pObj = invocation.getParameter(pIndex);
			pType = null;
			if(pObj!=null){
				pType = entityService.getEntityClass(pObj);
			}
			if(pType == null){
				pType = method.getParameterTypes()[pIndex];
			}
			ValueEncoder encoder = valueEncoderSource.getValueEncoder(pType);
			keyParameter.add(encoder.toClient(pObj));
		}
		//得到缓存的真正key
		String key=null;
		String keyFormat= cacheable.keyFormat();
		logger.debug("key parameter:{}",keyParameter);
		if(!StringUtils.hasText(keyFormat)){
			key = DigestUtils.shaHex(method.toString()+keyParameter.toString());
		}else{
    		key =String.format(keyFormat, keyParameter.toArray(new Object[0]));
		}
		
		CacheStrategy strategy = this.source.findStrategy(cacheable.strategy());
		CacheNsParameter[] nses = cacheable.namespaces();
		if(strategy == null){
			throw new RuntimeException("fail to find cache strategy instance!");
		}
		return strategy.appendNamespace(cacheManager,cacheable.clazz(),nses,key,keyParameter.toArray());
	}
	
}
