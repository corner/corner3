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
package corner.hibernate;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;


import org.apache.tapestry5.json.JSONObject;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Type;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.classic.Session;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import corner.entities.base.BaseModel;

/**
 * 针对JSON字符串的测试
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class JSONStringTypeTest extends Assert{

	private Session session;
	private Transaction transaction;
	@BeforeMethod
	void setUpHibernateEnv(){
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.addAnnotatedClass(TestModel.class);
		cfg.setProperty( Environment.HBM2DDL_AUTO, "create-drop");
		
		SessionFactory sessionFactory = cfg.configure().buildSessionFactory();
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	@Test
	public void test_JSONType(){
		
		TestModel model = new TestModel();
		JSONObject json = new JSONObject();
		json.put("test", "value");
		model.setJsonProperty(json);
		
		session.persist(model);
		
		session.evict(model);
		
		List<Object> list = session.createQuery("from "+TestModel.class.getName()).list();
		assertEquals(list.size(),1);
		model = (TestModel) list.get(0);
		assertEquals(model.getJsonProperty(),json);
		session.delete(model);
		
	}
	@Test
	public void test_update_JSONType(){
		
		TestModel model = new TestModel();
		JSONObject json = new JSONObject();
		json.put("test", "value");
		model.setJsonProperty(json);
		
		session.persist(model);
		session.evict(model);
		
		this.transaction.commit();
		transaction = session.beginTransaction();
		
		
		List<Object> list = session.createQuery("from "+TestModel.class.getName()).list();
		assertEquals(list.size(),1);
		model = (TestModel) list.get(0);
//		JSONObject newJson = new JSONObject(model.getJsonProperty().toString());
		JSONObject newJson = model.getJsonProperty();//new JSONObject(model.getJsonProperty().toString());
		
		assertEquals(model.getJsonProperty(),json);
		newJson.put("test","Hello");
		
		model.setJsonProperty(newJson);
		session.update(model);
		this.transaction.commit();
		session.evict(model);
		this.transaction = session.beginTransaction();
		model = (TestModel) session.load(TestModel.class, model.getId());
		assertEquals(model.getJsonProperty().get("test"),"Hello");
		
		session.delete(model);
		
	}
	@AfterMethod
	void cleanHibernateEnv(){
		transaction.commit();
		session.close();
	}
	
	@Entity
	@Table(name="test_model")
	public static class TestModel extends BaseModel{
		private JSONObject jsonProperty;

		/**
		 * @return the jsonProperty
		 */
		@Type(type="corner.hibernate.JSONStringType")
		public JSONObject getJsonProperty() {
			return jsonProperty;
		}

		/**
		 * @param jsonProperty the jsonProperty to set
		 */
		public void setJsonProperty(JSONObject jsonProperty) {
			this.jsonProperty = jsonProperty;
		}
	}
}
