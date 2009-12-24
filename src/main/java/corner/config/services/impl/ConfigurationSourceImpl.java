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
package corner.config.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.internal.util.Defense;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.util.StrategyRegistry;

import corner.config.services.ConfigInitable;
import corner.config.services.ConfigurationSource;

/**
 * 实现服务配置工厂类.
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */

public class ConfigurationSourceImpl implements ConfigurationSource {

	private final StrategyRegistry<Resource> registry;

	private final Map<Class, Object> cache = CollectionFactory
			.newConcurrentMap();

	public ConfigurationSourceImpl(Map<Class, Resource> configuration) {
		registry = StrategyRegistry.newInstance(Resource.class, configuration);
	}

	/**
	 * 
	 * @see corner.config.services.ConfigurationSource#getServiceConfig(java.lang.Class)
	 */
	@Override
	public <T> T getServiceConfig(Class<T> type) {
		Defense.notNull(type, "type");

		T result = (T) cache.get(type);

		if (result == null) {
			Resource resource = registry.get(type);
			result = createServiceConfigObject(type, resource);
			cache.put(type, result);
		}

		return result;
	}

	private <T> T createServiceConfigObject(Class<T> type, Resource resource) {

		InputStream in;
		try {
			in = resource.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (in == null) {
			throw new RuntimeException("The config source[" + resource
					+ "] can't be found.");
		}
		Reader reader = new InputStreamReader(in);
		T result = load(reader, type);
		if (result instanceof ConfigInitable) {
			((ConfigInitable) result).init();
		}
		return result;

	}
	/**
	 * 从一个输入流里加载clazz类型的对象
	 * 
	 * @param <T>
	 * @param in 操作完成后,会被关闭
	 * @param clazz
	 * @return
	 * @throws RuntimeException
	 *             在加载的过程出现异常,将抛出此异常
	 */
	public static <T> T load(Reader in, Class<T> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller um = context.createUnmarshaller();
			return (T) um.unmarshal(in);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			InternalUtils.close(in);
		}
	}

}
