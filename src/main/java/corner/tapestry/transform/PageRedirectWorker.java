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

import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentMethodAdvice;
import org.apache.tapestry5.services.ComponentMethodInvocation;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.TransformMethodSignature;

import corner.tapestry.ComponentConstants;

/**
 *  处理PageRedirect注释,目前只支持返回类型是String和Class和Link的Action
 * @author dong
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PageRedirectWorker implements ComponentClassTransformWorker {

	private Request request;

	private final ComponentMethodAdvice advice = new ComponentMethodAdvice() {
		/**
		 * @see org.apache.tapestry5.services.ComponentMethodAdvice#advise(org.apache.tapestry5.services.ComponentMethodInvocation)
		 */
		@Override
		public void advise(ComponentMethodInvocation invocation) {
				invocation.proceed();
				invocation.getResult();
				request.setAttribute(ComponentConstants.REQUIRED_REDIRECT,"true");
		}
	};

	/**
	 * 
	 * @param resolver
	 * @param linkFactory
	 */
	public PageRedirectWorker(Request request) {
		this.request = request;
	}

	/**
	 * 
	 * @see org.apache.tapestry.services.ComponentClassTransformWorker#transform(org.apache.tapestry.services.ClassTransformation,
	 *      org.apache.tapestry.model.MutableComponentModel)
	 * @since 0.0.1
	 */
	@Override
	public void transform(ClassTransformation transformation, MutableComponentModel model) {
		for (TransformMethodSignature sig : transformation.findMethodsWithAnnotation(PageRedirect.class)) {
			transformation.advise(sig, advice);
		}
	}
}