/* 
 * Copyright 2008 The Lichen Team.
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
package corner.pages;


import org.apache.tapestry5.ioc.annotations.Inject;

import com.caucho.hessian.server.HessianSkeleton;

import corner.internal.services.HessianRemoteResponse;
import corner.services.RemoteResponse;
import corner.services.ServiceLocatorDelegate;

/**
 * 远程调用使用的页面类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public class Remote {

	
	@Inject
	private ServiceLocatorDelegate serviceLocatorDelegate;
	public RemoteResponse onActionFromHessian(String serviceInterface) throws ClassNotFoundException{
		final Class<?> serviceInterfaceClazz = Class.forName(serviceInterface);
		return new HessianRemoteResponse(){
			@Override
			public HessianSkeleton getSkeleton() {
				return new HessianSkeleton(serviceLocatorDelegate.getService(serviceInterfaceClazz),serviceInterfaceClazz);
			}};
	}
}
