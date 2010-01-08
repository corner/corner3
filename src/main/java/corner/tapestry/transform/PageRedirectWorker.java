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
package corner.tapestry.transform;

import java.io.IOException;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentMethodAdvice;
import org.apache.tapestry5.services.ComponentMethodInvocation;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.TransformMethodSignature;

/**
 *  处理PageRedirect注释,目前只支持返回类型是String和Class和Link的Action
 * @author dong
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PageRedirectWorker implements ComponentClassTransformWorker {

	private final ComponentClassResolver _resolver;
	private final LinkSource _linkFactory;
	private Response response;

	private final ComponentMethodAdvice advice = new ComponentMethodAdvice() {
		public void advise(ComponentMethodInvocation invocation) {
				invocation.proceed();
				Object result = invocation.getResult();
				// 返回类型是void,不k处理
				String pageName = null;
				
				RESOLVE_PAGE_NAME:{
					//find current page name
    				if ( result == null) {
    					pageName = invocation.getComponentResources().getPageName();
    					break RESOLVE_PAGE_NAME;
    				}
    				Class<?> resultType = result.getClass();
    				
    				if(resultType == java.lang.Void.class){
    					pageName = invocation.getComponentResources().getPageName();
    					break RESOLVE_PAGE_NAME;
    				}
    					
    				// 返回类型是Class或者String时，尝试查找result对应的Page
    				if (resultType == Class.class || resultType == String.class) {
    					if (resultType == java.lang.Class.class) {
    						Class<?> clazz = (Class<?>)result;
    						pageName = _resolver.resolvePageClassNameToPageName(clazz.getName());
    					}else if(resultType == String.class){
    						pageName = (String)result;
    					}
    				}
				}
				if (pageName != null) {
    				Link retLink = _linkFactory.createPageRenderLink(pageName, false,new Object[0]);
					try {
						response.sendRedirect(retLink);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
		}
	};

	/**
	 * 
	 * @param resolver
	 * @param linkFactory
	 */
	public PageRedirectWorker(ComponentClassResolver resolver, LinkSource linkFactory,Response response) {
		this._resolver = resolver;
		this._linkFactory = linkFactory;
		this.response = response;
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