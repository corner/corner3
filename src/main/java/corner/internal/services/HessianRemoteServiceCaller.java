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
package corner.internal.services;

import java.net.MalformedURLException;


import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.caucho.hessian.client.HessianProxyFactory;

import corner.CornerConstants;
import corner.services.RemoteServiceCaller;

/**
 * 通过Hessian协议进行远程调用的方法
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public class HessianRemoteServiceCaller implements RemoteServiceCaller {

	private String url;
	private boolean enableRemoteCall;

	public HessianRemoteServiceCaller(
			@Inject
			@Symbol(CornerConstants.REMOTE_SERVER_URL)
			String hessianUrl, 
			@Inject
			@Symbol(CornerConstants.ENABLE_REMOTE_CALL)
			boolean enableRemoteCall) {
		this.url = hessianUrl;
		this.enableRemoteCall = enableRemoteCall;
	}

	/**
	 * @see corner.services.RemoteServiceCaller#createRemoteServiceObject(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> T createRemoteServiceObject(Class<T> serviceInterface,
			ObjectCreator objectCreator) {
		if (serviceInterface.getConstructors().length > 0) {
			throw new RuntimeException("通过Hessian远程调用的接口需要无参数的默认构造函数!");
		}
		if(!enableRemoteCall){
			return (T) objectCreator.createObject();
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		String serviceName = serviceInterface.getName();
		T remoteObject;
		try {
			remoteObject = (T) factory.create(serviceInterface, url + "/"
					+ serviceName);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return remoteObject;
	}

}
