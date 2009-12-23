/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ganshane.net
 * file: $Id$
 * created at:2008-10-08
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
