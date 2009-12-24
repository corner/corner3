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
package corner.migration.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.util.StrategyRegistry;
import org.hibernate.dialect.Dialect;

import corner.migration.services.ConnectionAdapter;
import corner.migration.services.ConnectionAdapterSource;

/**
 * 数据库连接适配器源的实现
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class ConnectionAdapterSourceImpl implements ConnectionAdapterSource {
	
	private StrategyRegistry<ConnectionAdapter> registry;

	public ConnectionAdapterSourceImpl(Map<Class, ConnectionAdapter> configuration) {

        registry = StrategyRegistry.newInstance(ConnectionAdapter.class,configuration);

	}
	/**
	 * 
	 * @see corner.migration.services.ConnectionAdapterSource#getConnectionAdapter(java.lang.Class)
	 */
	@Override
	public ConnectionAdapter getConnectionAdapter(Class<? extends Dialect> clazz) {
		return registry.get(clazz);
	}
}
