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

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.ioc.test.IOCTestCase;
import org.testng.annotations.Test;

import corner.config.annotations.Configurable;
import corner.config.impl.TestConfigModel;

public class ConfigurationSourceTest extends IOCTestCase{

	@Test
	public void test_configobject(){
		//IOCUtilities.buildDefaultRegistry()
		Registry registry = this.buildRegistry(ConfigurationModule.class,TestConfigModule.class);
		TestService service = registry.getService(TestService.class);
		assertNotNull(service);
		TestConfigModel model = service.getModel();
		assertEquals(model.getTestElement1(),"test e1 content");
		assertEquals(model.getTestElement2(),12345);
		
	}
	public static class TestService{
		private TestConfigModel model;

		public TestService(@Configurable TestConfigModel model){
			this.setModel(model);
		}

		public void setModel(TestConfigModel model) {
			this.model = model;
		}

		public TestConfigModel getModel() {
			return model;
		}
		
	}
	public static class TestConfigModule{
		public static void bind(ServiceBinder binder){
			binder.bind(TestService.class);
			
		}
		public static void contributeConfigurationSource(MappedConfiguration<Class, Resource> configuration){
			configuration.add(TestConfigModel.class, new ClasspathResource("corner/config/impl/test-config.xml"));
		}
	}
	
}
