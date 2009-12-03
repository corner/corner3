/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ganshane.net
 * file: $Id: ConfigruationSourceImpl.java 4355 2008-12-05 02:39:02Z d0ng $
 * created at:2008-10-08
 */

package corner.config.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.internal.util.Defense;
import org.apache.tapestry5.ioc.util.StrategyRegistry;

import corner.config.ConfigInitable;
import corner.config.ConfigruationSource;

/**
 * 实现服务配置工厂类.
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 4355 $
 * @since 0.0.1
 */

public class ConfigruationSourceImpl implements ConfigruationSource {

	private final StrategyRegistry<Resource> registry;

	private final Map<Class, Object> cache = CollectionFactory
			.newConcurrentMap();

	public ConfigruationSourceImpl(Map<Class, Resource> configuration) {
		registry = StrategyRegistry.newInstance(Resource.class, configuration);
	}

	/**
	 * 
	 * @see corner.config.ConfigruationSource#getServiceConfig(java.lang.Class)
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
		T result = JAXBUtil.load(reader, type);
		if (result instanceof ConfigInitable) {
			((ConfigInitable) result).init();
		}
		return result;

	}

}
