package corner.orm.gae.impl;

import javax.persistence.EntityManagerFactory;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.services.ClassFactory;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.ioc.test.IOCTestCase;
import org.datanucleus.store.appengine.jpa.DatastorePersistenceProvider;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.apphosting.api.ApiProxy.Delegate;

import corner.orm.gae.GaeModule;

public class EntityManagerSourceImplTest extends IOCTestCase{
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
	public void test_startup() throws Throwable{
		ClassFactory classFactory = registry.getService(ClassFactory.class);
		Delegate delegate = GaeModule.buildDelegate(classFactory);
		EntityManagerFactory entityManagerFactory = GaeModule.buildEntityManagerFactory(new DatastorePersistenceProvider());
		EntityManagerSourceImpl entityManagerSource = new EntityManagerSourceImpl(false, entityManagerFactory, mockLogger(), delegate);
		assertNotNull(entityManagerSource.getEntityManager());
	}
}
