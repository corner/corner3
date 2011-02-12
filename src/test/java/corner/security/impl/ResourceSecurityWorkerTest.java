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
package corner.security.impl;

import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.TransformMethodSignature;
import org.apache.tapestry5.test.TapestryTestCase;
import org.testng.annotations.Test;

import corner.security.annotations.ResourceSecurity;
import corner.security.annotations.ResourceSecurityObject;
import corner.security.services.impl.ResourceSecurityWorker;


public class ResourceSecurityWorkerTest extends TapestryTestCase {
	@Test
	public void test_transform() {
		ObjectLocator locator = mockObjectLocator();
		ClassTransformation ct = mockClassTransformation();
		MutableComponentModel model = mockMutableComponentModel();
		ResourceSecurity rs = newMock(ResourceSecurity.class);

		ResourceCheck checker = new ResourceCheck(){
			@Override
			public boolean check(int id) {
				return false;
			}
		};
		train_getService(locator, ResourceCheck.class,checker); 
		TransformMethodSignature sig = new TransformMethodSignature(
				Modifier.PUBLIC| Modifier.FINAL, "void", "onDelete",
				new String[] {int.class.getName()}, null);
		List<TransformMethodSignature> methods = new LinkedList<TransformMethodSignature>();
		methods.add(sig);
		train_findMethodsWithAnnotation(ct, ResourceSecurity.class, methods);
		train_getMethodAnnotation(ct, sig, ResourceSecurity.class, rs);
		train_addInjectedField(ct, ResourceCheck.class, "_$checker_for_"+sig.getMethodName(),checker,"_$checker");
		train_getClassName(ct, TestApp.class.getName());
		ResourceSecurityWorker worker = new ResourceSecurityWorker(locator);
		expect(rs.service()).andReturn(ResourceCheck.class);
		expect(rs.checkMethod()).andReturn("check");
		expect(rs.serviceId()).andReturn("");
		replay();
		worker.transform(ct, model);
		verify();
	}

	public static interface ResourceCheck {
		public boolean check(int id);
	}
	
	public static class TestApp{
		
		public void onDelete(@ResourceSecurityObject int id){
			
		}
	}
	
	public static class TestAppService{
		public boolean check(int id){
			return true;
		}
	}
	
}
