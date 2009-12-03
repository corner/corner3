/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ganshane.net
 * file: $Id: ConfigruationModule.java 2424 2008-10-31 17:13:57Z d0ng $
 * created at:2008-10-08
 */

package corner.config;

import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;

import corner.config.impl.ConfigurableObjectProvider;
import corner.config.impl.ConfigruationSourceImpl;

/**
 * ServiceConfig的配置Module
 * 
 * @author dong
 * @version $Revision: 2424 $
 * @since 0.0.1
 */
public class ConfigruationModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(ConfigruationSource.class, ConfigruationSourceImpl.class);
	}

	public static void contributeMasterObjectProvider(
			OrderedConfiguration<ObjectProvider> configuration
			) {
		configuration.addInstance("Configurable", ConfigurableObjectProvider.class, "after:Autobuild");
	}
}
