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
package corner.security.services.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.CtClass;

import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.util.BodyBuilder;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.TransformMethodSignature;

import corner.security.annotations.ResourceSecurity;
import corner.security.annotations.ResourceSecurityObject;


/**
 * 对ResourceSecurity增强器.
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.1
 */
public class ResourceSecurityWorker implements ComponentClassTransformWorker {

	/** 用于查找Servcie的实例 * */
	private final ObjectLocator _locator;

	private final static Map<String, Class> primitiveTypes = new HashMap<String, Class>();

	/**
	 * 
	 * @param checker
	 * @param resolver
	 * @param linkFactory
	 */
	public ResourceSecurityWorker(ObjectLocator locator) {
		this._locator = locator;
		primitiveTypes.put(CtClass.booleanType.getName(), Boolean.TYPE);
		primitiveTypes.put(CtClass.byteType.getName(), Byte.TYPE);
		primitiveTypes.put(CtClass.charType.getName(), Character.TYPE);
		primitiveTypes.put(CtClass.doubleType.getName(), Double.TYPE);
		primitiveTypes.put(CtClass.floatType.getName(), Float.TYPE);
		primitiveTypes.put(CtClass.intType.getName(), Integer.TYPE);
		primitiveTypes.put(CtClass.longType.getName(), Long.TYPE);
		primitiveTypes.put(CtClass.shortType.getName(), Short.TYPE);
		primitiveTypes.put(CtClass.voidType.getName(), Void.TYPE);
	}

	/**
	 * 
	 * @see org.apache.tapestry.services.ComponentClassTransformWorker#transform(org.apache.tapestry.services.ClassTransformation,
	 *      org.apache.tapestry.model.MutableComponentModel)
	 * @since 0.0.1
	 */
	public void transform(ClassTransformation transformation,
			MutableComponentModel model) {
		List<TransformMethodSignature> methods = transformation
				.findMethodsWithAnnotation(ResourceSecurity.class);
		if (methods == null || methods.isEmpty())
			return;
		Class clazz = null;
		try {
			clazz = Class.forName(transformation.getClassName());
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		for (TransformMethodSignature _method : methods) {
			ResourceSecurity annotation = transformation.getMethodAnnotation(
					_method, ResourceSecurity.class);
			Method rmethod = getClassMethod(clazz, _method);
			String params = parseSecurityObjcetParams(rmethod);
			processMethod(transformation, annotation, _method, params);
		}
	}

	/**
	 * 查找方法参数声明中有＠ResourceSecurityObject注释的参数,返回javasist的参数序列,如$1,＄3
	 * 
	 * @param rmethod
	 * @return
	 */
	private String parseSecurityObjcetParams(Method rmethod) {
		StringBuilder sb = new StringBuilder();
		Annotation[][] _paramAnnotations = rmethod.getParameterAnnotations();
		for (int i = 0; i < _paramAnnotations.length; i++) {
			Annotation[] _paramAnnotation = _paramAnnotations[i];
			boolean _gotten = false;
			for (Annotation _annotation : _paramAnnotation) {
				if (ResourceSecurityObject.class.isInstance(_annotation)) {
					_gotten = true;
					break;
				}
			}
			if (_gotten) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append("$" + (i + 1));
			}
		}
		return sb.toString();
	}

	/**
	 * 从TransformMethodSignature的对象转换为jdk中Method对象
	 * 
	 * @param clazz
	 * @param _method
	 * @return
	 */
	private Method getClassMethod(Class<?> clazz, TransformMethodSignature _method) {
		try {
			String[] _params = _method.getParameterTypes();
			Class[] _paramTypes = new Class[_params.length];
			for (int i = 0; i < _params.length; i++) {
				String s = _params[i];
				Class _ct = ResourceSecurityWorker.primitiveTypes.get(s);
				if (_ct != null) {
					_paramTypes[i] = _ct;
				} else {
					_paramTypes[i] = Class.forName(s);
				}
			}
			return clazz.getDeclaredMethod(_method.getMethodName(), _paramTypes);
		} catch (Throwable te) {
			throw new RuntimeException(te);
		}
	}

	/**
	 * 
	 * @param transformation
	 * @param annotation
	 * @param method
	 */
	private void processMethod(ClassTransformation transformation,
			ResourceSecurity annotation, TransformMethodSignature method,
			String params) {
		Class<?> service = annotation.service();
		String serviceId = annotation.serviceId();
		String checkMethod = annotation.checkMethod();

		// 获取用于校验的Service实例
		Object serviceObject = null;
		if (serviceId.length() != 0) {
			serviceObject = this._locator.getService(serviceId, service);
		} else {
			serviceObject = this._locator.getService(service);
		}
		String checker = transformation.addInjectedField(service,
				"_$checker_for_" + method.getMethodName(), serviceObject);
		BodyBuilder builder = new BodyBuilder();
		builder
				.addln(
						"try{if(!%s.%s(%s)){throw new corner.security.services.AclException(\"%s\");}}catch(Throwable te){throw new RuntimeException(te);}",
						checker, checkMethod, params, "Can't access");
		transformation.extendMethod(method, builder.toString());
	}
}