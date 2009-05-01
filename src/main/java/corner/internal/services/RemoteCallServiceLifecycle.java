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
package corner.internal.services;

import static java.lang.String.format;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.ServiceLifecycle;
import org.apache.tapestry5.ioc.ServiceResources;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.Builtin;
import org.apache.tapestry5.ioc.services.ClassFab;
import org.apache.tapestry5.ioc.services.ClassFactory;
import org.apache.tapestry5.ioc.services.MethodSignature;

import corner.CornerConstants;
import corner.services.RemoteServiceCaller;
import corner.services.RemoteServiceCallerSource;

/**
 * 需要远程调用的服务类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3408 $
 * @since 0.0.1
 */
public class RemoteCallServiceLifecycle implements ServiceLifecycle {
	private static final String REMOTE_ACCESS_METHOD_NAME = "_remoteObjectInstance";
	
	private ClassFactory classFactory;

	private RemoteServiceCallerSource callerSource;

	private String defaultCaller;
	/**
	 * 远程调用对象
	 * @param caller 远程调用服务类
	 */
	public RemoteCallServiceLifecycle(
				RemoteServiceCallerSource callerSource,
				@Builtin
            ClassFactory classFactory,
            @Inject
				@Symbol(CornerConstants.DEFAULT_CALLER)
				String defaultCaller
            	){
		
		this.callerSource = callerSource;
		this.classFactory = classFactory;
		this.defaultCaller = defaultCaller;
		
	}
	/**
	 * @see org.apache.tapestry5.ioc.ServiceLifecycle#createService(org.apache.tapestry5.ioc.ServiceResources, org.apache.tapestry5.ioc.ObjectCreator)
	 */
	public Object createService(final ServiceResources resources,
			final ObjectCreator creator) {
		
		Class<?> proxyClass = createProxyClass(resources);
		
		ObjectCreator remoteObjectCreator = new ObjectCreator(){
			public Object createObject() {
				Class<?> clazz=resources.getServiceInterface();
				//得到默认的远程调用方法,是否单态？
				RemoteServiceCaller caller = callerSource.get(defaultCaller);
				return caller.createRemoteServiceObject(clazz,creator);
			}};
			
     
      try
        {
            Constructor<?> ctor = proxyClass.getConstructors()[0];
            return ctor.newInstance(remoteObjectCreator);
        }
        catch (InvocationTargetException ex)
        {
            throw new RuntimeException(ex.getCause());
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
	}
	
	  private Class<?> createProxyClass(ServiceResources resources)
	    {
	        Class<?> serviceInterface = resources.getServiceInterface();

	        ClassFab cf = classFactory.newClass(serviceInterface);

	        cf.addField("_creator", Modifier.PRIVATE | Modifier.FINAL, ObjectCreator.class);

	        // Constructor takes a ServiceCreator

	        cf.addConstructor(new Class[]
	                { ObjectCreator.class }, null, "_creator = $1;");

	        String body = format("return (%s) _creator.createObject();", serviceInterface.getName());

	        MethodSignature sig = new MethodSignature(serviceInterface, REMOTE_ACCESS_METHOD_NAME, null,
	                                                  null);

	        cf.addMethod(Modifier.PRIVATE, sig, body);

	        String toString = format(
	                "<RemoteObject Proxy for %s(%s)>",
	                resources.getServiceId(),
	                serviceInterface.getName());

	        cf.proxyMethodsToDelegate(serviceInterface, REMOTE_ACCESS_METHOD_NAME + "()", toString);

	        return cf.createClass();
	    }
	@Override
	public boolean isSingleton() {
		return true;
	}

}
