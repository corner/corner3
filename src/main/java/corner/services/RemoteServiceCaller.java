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
package corner.services;

import org.apache.tapestry5.ioc.ObjectCreator;

/**
 * 通过远程调用的服务类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public interface RemoteServiceCaller {
	/**
	 * 创建远程调用的服务对象
	 * @param <T> 通过远程调用的Interface
	 * @param serviceInterface 服务的接口
	 * @return 远程对象
	 */
	public <T> T createRemoteServiceObject(Class<T> serviceInterface,ObjectCreator creator);
}
