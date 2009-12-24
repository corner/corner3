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
package corner.config;

import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.services.Builtin;

import corner.config.services.ConfigurationSource;
import corner.config.services.impl.ConfigurableObjectProvider;
import corner.config.services.impl.ConfigurationSourceImpl;

/**
 * ServiceConfig的配置Module
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.1
 */
public class ConfigurationModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(ConfigurationSource.class, ConfigurationSourceImpl.class).withMarker(Builtin.class);
	}

	public static void contributeMasterObjectProvider(
			OrderedConfiguration<ObjectProvider> configuration
			) {
		configuration.addInstance("Configurable", ConfigurableObjectProvider.class, "after:Autobuild");
	}
}
