/* 
 * Copyright 2009 The Tapestry-GAE team.
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
package corner.orm.gae.impl;

import javax.persistence.EntityManager;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.test.TapestryTestCase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @since 0.1
 */
public class JpaEntityPersistentFieldStrategyTest extends  TapestryTestCase{
	private Registry registry;
	private PropertyAccess access;
	private TypeCoercer typeCoercer;

	@BeforeClass
	public void setup() {
		registry = buildRegistry();
		access = registry.getService(PropertyAccess.class);
		typeCoercer = registry.getService(TypeCoercer.class);
	}

	@AfterClass
	public void cleanup() {
		registry.shutdown();
		registry = null;
		access = null;
		typeCoercer = null;
	}
	@Test
	public void test_jpa_persistent_strategy(){
		
		TestAEntity testA = new TestAEntity();
		EntityManager entityManager = newMock(EntityManager.class);
		expect(entityManager.find(TestAEntity.class, 12L)).andReturn(testA);
		GaeEntityPersistentFieldStrategy strategy = new GaeEntityPersistentFieldStrategy(entityManager,null, access);
		replay();
		
		testA.setId(12L);
		JpaPersistedEntity obj = (JpaPersistedEntity) strategy.convertApplicationValueToPersisted(testA);
		assertEquals("<PersistedEntity: corner.orm.gae.impl.TestAEntity(12)>",obj.toString());
		assertEquals(testA,obj.restore(entityManager));
		
		verify();
	}
}
