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
package corner.services.http.impl;


import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ioc.util.BodyBuilder;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.TransformConstants;

import corner.services.http.CacheHeader;
import corner.services.http.CacheHeaderService;
import corner.services.http.CacheHeaderType;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class CacheHeaderWorker implements ComponentClassTransformWorker {
	private final Response response;
	private final CacheHeaderService cacheHeaderService;

	public CacheHeaderWorker(Response response,
			CacheHeaderService cacheHeaderService) {
		this.response = response;
		this.cacheHeaderService = cacheHeaderService;
	}

	/**
	 * 
	 * @see org.apache.tapestry.services.ComponentClassTransformWorker#transform(org.apache.tapestry.services.ClassTransformation,
	 *      org.apache.tapestry.model.MutableComponentModel)
	 * @since 0.0.1
	 */
	public void transform(ClassTransformation transformation,
			MutableComponentModel model) {

		CacheHeader annotation = transformation
				.getAnnotation(CacheHeader.class);

		if (annotation == null)
			return;
		CacheHeaderType type = annotation.type();
		String value = annotation.value();

		if (type == null) {
			return;
		}

		String _response = transformation.addInjectedField(Response.class,
				"_$response", this.response);
		String _service = transformation.addInjectedField(
				CacheHeaderService.class, "_$service", this.cacheHeaderService);
		String _type = transformation.addInjectedField(CacheHeaderType.class,
				"_$cacheHeaderType", type);
		String _value = transformation.addInjectedField(String.class,
				"_$cacheHeaderTypeValue", value);

		BodyBuilder builder = new BodyBuilder();
		builder.begin();
		{
			builder.addln("if ($1.matches(\"%s\", \"%s\", %d))",
					EventConstants.ACTIVATE, "", 0);
			builder.begin();
			{
				builder.addln("try");
				builder.begin();
				{
					builder.addln("%s.setCacheHeader(%s,%s,%s);", _service,
							_response, _type, _value);
				}
				builder.end();
				builder.addln("catch (RuntimeException ex) { throw ex; }");
				builder
						.addln("catch (Exception ex) { throw new RuntimeException(ex); } ");
			}
			builder.end();
		}
		builder.end();

		transformation
				.extendExistingMethod(TransformConstants.DISPATCH_COMPONENT_EVENT,
						builder.toString());
	}
}
