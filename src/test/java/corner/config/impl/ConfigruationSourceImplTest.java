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
package corner.config.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.test.TapestryTestCase;
import org.testng.annotations.Test;

import corner.config.services.impl.ConfigurationSourceImpl;

/**
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class ConfigruationSourceImplTest extends TapestryTestCase{
	@Test
	public void testConfig(){
		Map<Class, Resource> configuration = new HashMap<Class,Resource>();
		configuration.put(TestConfigModel.class, new ClasspathResource("corner/config/impl/test-config.xml"));
		ConfigurationSourceImpl source = new ConfigurationSourceImpl(configuration);
		TestConfigModel model = source.getServiceConfig(TestConfigModel.class);
		assertNotNull(model);
		assertEquals(model.getTestElement1(),"test e1 content");
		assertEquals(model.getTestElement2(),12345);
		assertEquals(model.getInitValue(),"initValue");
	}
}
