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
package corner.services.http.impl;

import static org.apache.tapestry5.internal.test.CodeEq.codeEq;
import static org.easymock.EasyMock.eq;

import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.TransformMethodSignature;
import org.apache.tapestry5.test.TapestryTestCase;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.services.http.CacheHeader;
import corner.services.http.CacheHeaderService;
import corner.services.http.CacheHeaderType;
import corner.services.http.impl.CacheHeaderWorker;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class CacheHeaderWorkerTest extends TapestryTestCase {
	@Test
	public void test_transform() {
		ClassTransformation ct = mockClassTransformation();
		MutableComponentModel model = mockMutableComponentModel();
		CacheHeader rs = newMock(CacheHeader.class);
		EasyMock.expect(rs.type()).andReturn(CacheHeaderType.NOCACHE).anyTimes();
		EasyMock.expect(rs.value()).andReturn("").anyTimes();
		Response response = newMock(Response.class);
		CacheHeaderService service = newMock(CacheHeaderService.class);
		train_getAnnotation(ct, CacheHeader.class, rs);
		train_addInjectedField(ct, Response.class, "_$response", response,
				"_$response");
		train_addInjectedField(ct, CacheHeaderService.class, "_$service",
				service, "_$service");
		train_addInjectedField(ct, CacheHeaderType.class, "_$cacheHeaderType",
				CacheHeaderType.NOCACHE, "_$cacheHeaderType");
		train_addInjectedField(ct, String.class, "_$cacheHeaderTypeValue",
				"", "_$cacheHeaderTypeValue");
		TransformMethodSignature sig = TransformConstants.DISPATCH_COMPONENT_EVENT;
		String body = "{";
		body+="\nif ($1.matches(\"activate\", \"\", 0))";
		body+="\n{";
		body+="\ntry";
		body+="\n{";
		body+="\n_$service.setCacheHeader(_$response,_$cacheHeaderType,_$cacheHeaderTypeValue);";
		body+="\n}";
		body+="\ncatch (RuntimeException ex) { throw ex; }";
		body+="\ncatch (Exception ex) { throw new RuntimeException(ex); }";
		body+="\n}";
		body+="\n}";
		ct.extendExistingMethod(eq(sig), codeEq(join(body)));
		CacheHeaderWorker worker = new CacheHeaderWorker(response, service);
		replay();
		worker.transform(ct, model);
		verify();
	}
}
