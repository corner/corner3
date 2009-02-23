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
package lichen.internal.services;

import lichen.services.ServiceLocatorDelegate;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectLocator;

/**
 * ObjectLocator的代理类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 2047 $
 * @since 0.0.1
 */
public class ServiceLocatorDelegateImpl implements ServiceLocatorDelegate {
	public ObjectLocator objectLocator;
	public ServiceLocatorDelegateImpl(ObjectLocator locator){
		this.objectLocator = locator;
	}
	/**
	 * @see lichen.services.ServiceLocatorDelegate#getObjectLocator()
	 */
	public ObjectLocator getObjectLocator() {
		return this.objectLocator;
	}
	/**
	 * @param <T>
	 * @param clazz
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#autobuild(java.lang.Class)
	 */
	public <T> T autobuild(Class<T> clazz) {
		return objectLocator.autobuild(clazz);
	}
	/**
	 * @param <T>
	 * @param objectType
	 * @param annotationProvider
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#getObject(java.lang.Class, org.apache.tapestry5.ioc.AnnotationProvider)
	 */
	public <T> T getObject(Class<T> objectType,
			AnnotationProvider annotationProvider) {
		return objectLocator.getObject(objectType, annotationProvider);
	}
	/**
	 * @param <T>
	 * @param serviceInterface
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#getService(java.lang.Class)
	 */
	public <T> T getService(Class<T> serviceInterface) {
		return objectLocator.getService(serviceInterface);
	}
	/**
	 * @param <T>
	 * @param serviceId
	 * @param serviceInterface
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#getService(java.lang.String, java.lang.Class)
	 */
	public <T> T getService(String serviceId, Class<T> serviceInterface) {
		return objectLocator.getService(serviceId, serviceInterface);
	}
	/**
	 * @param <T>
	 * @param interfaceClass
	 * @param implementationClass
	 * @return
	 * @see org.apache.tapestry5.ioc.ObjectLocator#proxy(java.lang.Class, java.lang.Class)
	 */
	public <T> T proxy(Class<T> interfaceClass,
			Class<? extends T> implementationClass) {
		return objectLocator.proxy(interfaceClass, implementationClass);
	}
	

}
