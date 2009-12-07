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
package corner.http;


import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.Response;

import corner.http.services.CacheHeaderService;
import corner.http.services.impl.CacheHeaderServiceImpl;
import corner.http.services.impl.CacheHeaderWorker;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class HttpModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(CacheHeaderService.class, CacheHeaderServiceImpl.class);

	}

	public static void contributeComponentClassTransformWorker(
			OrderedConfiguration<ComponentClassTransformWorker> configuration,
			Response response, CacheHeaderService service) {
		configuration.add("CacheHeaderWorker", new CacheHeaderWorker(response, service),"before:*");
	}
}
