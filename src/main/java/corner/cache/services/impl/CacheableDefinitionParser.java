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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corner.cache.annotations.CacheKeyParameter;
import corner.cache.services.impl.CacheableDefine.Definition;
import corner.cache.services.impl.CacheableDefine.Definition.Builder;

/**
 * 针对缓存定义的解析
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheableDefinitionParser {

	private ValueEncoderSource valueEncoderSource;
	private Logger logger = LoggerFactory.getLogger(CacheableDefinitionParser.class);

	public CacheableDefinitionParser(ValueEncoderSource valueEncoderSource) {
		this.valueEncoderSource = valueEncoderSource;
	}

	public String  parse(Invocation invocation,Method method){
		Definition define = null;//(Definition) cache.get(methodDefineKey);

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
			// 缓存定义
//			cache.put(methodDefineKey, define);
		//}
		// 构造真正的缓存Key
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < define.getParameterIndexCount(); i++) {
			int pIndex = define.getParameterIndex(i);
			ValueEncoder encoder = valueEncoderSource.getValueEncoder(method
					.getParameterTypes()[pIndex]);
			sb.append(encoder.toClient(invocation.getParameter(pIndex))).append(",");
		}
		sb.append(method.toString());
		String cacheKey = DigestUtils.shaHex(sb.toString());
		if (logger.isDebugEnabled()) {
			logger.debug("before sha key:[" + sb.toString() + "]");
			logger.debug("cache key:[" + cacheKey + "]");
		}
		return cacheKey;
	}
}
