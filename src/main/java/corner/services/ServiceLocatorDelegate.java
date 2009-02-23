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
package corner.services;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectLocator;

/**
 * 针对web服务类的Delegate类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public interface ServiceLocatorDelegate {
	
	/**
	 * 得到ObjectLocator
	 * @return object locator
	 */
	public ObjectLocator getObjectLocator();

	/**
	 * @param <T>
	 * @param clazz
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#autobuild(java.lang.Class)
	 */
	public <T> T autobuild(Class<T> clazz);

	/**
	 * @param <T>
	 * @param objectType
	 * @param annotationProvider
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#getObject(java.lang.Class, org.apache.tapestry5.ioc.AnnotationProvider)
	 */
	public <T> T getObject(Class<T> objectType, AnnotationProvider annotationProvider);

	/**
	 * @param <T>
	 * @param serviceInterface
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#getService(java.lang.Class)
	 */
	public <T> T getService(Class<T> serviceInterface);

	/**
	 * @param <T>
	 * @param serviceId
	 * @param serviceInterface
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#getService(java.lang.String, java.lang.Class)
	 */
	public <T> T getService(String serviceId, Class<T> serviceInterface);

	/**
	 * @param <T>
	 * @param interfaceClass
	 * @param implementationClass
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#proxy(java.lang.Class, java.lang.Class)
	 */
	public <T> T proxy(Class<T> interfaceClass, Class<? extends T> implementationClass);
	
}
