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
package corner.tapestry.transform;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentMethodAdvice;
import org.apache.tapestry5.services.ComponentMethodInvocation;
import org.apache.tapestry5.services.TransformMethodSignature;

/**
 *  处理PageRedirect注释,目前只支持返回类型是String和Class的Action
 * @author dong
 * @version $Revision: 5222 $
 * @since 0.0.1
 */
public class PageRedirectWorker implements ComponentClassTransformWorker {

	private final ComponentClassResolver _resolver;
	private final LinkSource _linkFactory;

	private final ComponentMethodAdvice advice = new ComponentMethodAdvice() {
		public void advise(ComponentMethodInvocation invocation) {
			try {
				invocation.proceed();
				Object result = invocation.getResult();
				//返回类型是null,不做处理
				if(result == null){
					return;
				}
				// 返回类型是void,不做处理
				Class<?> resultType = result.getClass();
				if (resultType == java.lang.Void.class) {
					return;
				}
				
				// 返回类型是Class或者String时，尝试查找result对应的Page
				String pageName = null;
				if (resultType == Class.class || resultType == String.class) {
					if (resultType == java.lang.Class.class) {
						Class<?> clazz = (Class<?>)result;
						pageName = _resolver.resolvePageClassNameToPageName(clazz.getName());
					}else if(resultType == String.class){
						pageName = (String)result;
					}
				}
				if (pageName != null) {
    				Link retLink = _linkFactory.createPageRenderLink(pageName, false,new Object[0]);
    				invocation.overrideResult(retLink);
				}
			} catch (RuntimeException ex) {
				throw ex;
			}
		}
	};

	/**
	 * 
	 * @param resolver
	 * @param linkFactory
	 */
	public PageRedirectWorker(ComponentClassResolver resolver, LinkSource linkFactory) {
		this._resolver = resolver;
		this._linkFactory = linkFactory;
	}

	/**
	 * 
	 * @see org.apache.tapestry.services.ComponentClassTransformWorker#transform(org.apache.tapestry.services.ClassTransformation,
	 *      org.apache.tapestry.model.MutableComponentModel)
	 * @since 0.0.1
	 */
	public void transform(ClassTransformation transformation, MutableComponentModel model) {
		for (TransformMethodSignature sig : transformation.findMethodsWithAnnotation(PageRedirect.class)) {
			transformation.advise(sig, advice);
		}
	}

}