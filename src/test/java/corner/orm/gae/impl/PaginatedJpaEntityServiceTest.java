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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.ioc.test.IOCTestCase;
import org.springframework.orm.jpa.JpaTemplate;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import corner.orm.gae.GaeModule;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;

/**
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @since 0.1
 */
public class PaginatedJpaEntityServiceTest extends IOCTestCase{

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
	public void test_count(){
		EntityManager entityManager = newMock(EntityManager.class);
		Query query = newMock(Query.class);
		expect(entityManager.createQuery("select count(root) from corner.orm.gae.impl.TestAEntity as root  where name=?")).andReturn(query);
		expect(query.setParameter(0, "acai")).andReturn(query);
		expect(query.getSingleResult()).andReturn(new Integer(1234));
		JpaTemplate jpaTemplate = GaeModule.buildJpaTemplate(entityManager);
		replay();
		PaginatedJapEntityService pjes = new PaginatedJapEntityService(jpaTemplate ,typeCoercer);
		assertEquals(1234,pjes.count(TestAEntity.class, new String[]{"name=?","acai"}));
		verify();
	}
	@Test
	public void test_paginate(){
		EntityManager entityManager = newMock(EntityManager.class);
		Query query = newMock(Query.class);
		Query query2 = newMock(Query.class);
		expect(entityManager.createQuery("select root from corner.orm.gae.impl.TestAEntity as root  where name=:1")).andReturn(query);
		expect(entityManager.createQuery("select count(root) from corner.orm.gae.impl.TestAEntity as root  where name=:1")).andReturn(query2);
		expect(query.setParameter("1", "acai")).andReturn(query);
		expect(query2.setParameter("1", "acai")).andReturn(query2);
		expect(query.setFirstResult(0)).andReturn(query);
		expect(query.setMaxResults(10)).andReturn(query);
		List listValue = Collections.EMPTY_LIST;
		expect(query.getResultList()).andReturn(listValue);
		expect(query2.getSingleResult()).andReturn(new Integer(1234));
		
		JpaTemplate jpaTemplate = GaeModule.buildJpaTemplate(entityManager);
		replay();
		PaginatedJapEntityService pjes = new PaginatedJapEntityService(jpaTemplate ,typeCoercer);
		PaginationOptions options = new PaginationOptions();
		PaginationList pl = pjes.paginate(TestAEntity.class, new String[]{"name=:1","acai"}, null, options);
		assertFalse(((Iterator) pl.collectionObject()).hasNext());
		PaginationOptions optionsR = pl.options();
		assertEquals(1234,optionsR.getTotalRecord());
		verify();
	}	
	@Test
	public void test_paginate_with_order(){
		EntityManager entityManager = newMock(EntityManager.class);
		Query query = newMock(Query.class);
		Query query2 = newMock(Query.class);
		expect(entityManager.createQuery("select root from corner.orm.gae.impl.TestAEntity as root  where name=:1 order by id desc")).andReturn(query);
		expect(entityManager.createQuery("select count(root) from corner.orm.gae.impl.TestAEntity as root  where name=:1")).andReturn(query2);
		expect(query.setParameter("1", "acai")).andReturn(query);
		expect(query2.setParameter("1", "acai")).andReturn(query2);
		expect(query.setFirstResult(0)).andReturn(query);
		expect(query.setMaxResults(10)).andReturn(query);
		List listValue = Collections.EMPTY_LIST;
		expect(query.getResultList()).andReturn(listValue);
		expect(query2.getSingleResult()).andReturn(new Integer(1234));
		
		JpaTemplate jpaTemplate = GaeModule.buildJpaTemplate(entityManager);
		replay();
		PaginatedJapEntityService pjes = new PaginatedJapEntityService(jpaTemplate ,typeCoercer);
		PaginationOptions options = new PaginationOptions();
		PaginationList pl = pjes.paginate(TestAEntity.class, new String[]{"name=:1","acai"}, "id desc", options);
		assertFalse(((Iterator) pl.collectionObject()).hasNext());
		PaginationOptions optionsR = pl.options();
		assertEquals(1234,optionsR.getTotalRecord());
		verify();
	}
}
