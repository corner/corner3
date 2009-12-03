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
package corner.config.impl;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ObjectProvider;

import corner.config.Configurable;
import corner.config.ConfigruationSource;

/**
 * 获取配置类实例
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class ConfigurableObjectProvider implements ObjectProvider{
	
	private ConfigruationSource configurationSource;

	public ConfigurableObjectProvider(ConfigruationSource configurationSource){
		this.configurationSource = configurationSource;
		
	}

	@Override
	public <T> T provide(Class<T> objectType,
			AnnotationProvider annotationProvider, ObjectLocator locator) {
		Configurable annotation = annotationProvider.getAnnotation(Configurable.class);
        if (annotation != null)
            return configurationSource.getServiceConfig(objectType);
		return null;
	}

}
