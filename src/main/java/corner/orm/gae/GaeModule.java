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
package corner.orm.gae;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.LoggerSource;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.Builtin;
import org.apache.tapestry5.ioc.services.ClassFab;
import org.apache.tapestry5.ioc.services.ClassFactory;
import org.apache.tapestry5.ioc.services.ClassNameLocator;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.PropertyShadowBuilder;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ApplicationInitializer;
import org.apache.tapestry5.services.ApplicationInitializerFilter;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.datanucleus.store.appengine.jpa.DatastorePersistenceProvider;
import org.slf4j.Logger;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.SimpleLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Delegate;

import corner.cache.CacheSymbols;
import corner.cache.services.CacheManager;
import corner.orm.gae.impl.EntityManagerSourceImpl;
import corner.orm.gae.impl.GaeCachedEntityServiceImpl;
import corner.orm.gae.impl.JavaCacheManagerImpl;
import corner.orm.gae.impl.JpaEntityServiceImpl;
import corner.orm.gae.impl.JpaEntityValueEncoder;
import corner.orm.gae.impl.JpaTransactionDecoratorImpl;
import corner.orm.gae.impl.TestEnvironment;
import corner.orm.services.EntityService;
import corner.orm.services.impl.CornerEntityPersistentFieldStrategy;
import corner.transaction.services.TransactionDecorator;

/**
 * Google App Engine 相关的module
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class GaeModule {
	private static final String STATIC_REGEX_PATTERN = "^[^$]+\\.(jpg|jpeg|gif|png|ico|css|zip|tgz|gz|rar|bz2|doc|xls|exe|pdf|ppt|txt|tar|mid|midi|wav|bmp|rtf|js|mov)$";

	public static void bind(ServiceBinder binder) {
		binder.bind(EntityService.class, GaeCachedEntityServiceImpl.class);
		binder.bind(TransactionDecorator.class,
				JpaTransactionDecoratorImpl.class);
		binder.bind(CacheManager.class, JavaCacheManagerImpl.class);
	}
	public static EntityService buildEntityService(@Symbol(CacheSymbols.ENABLE_CACHE) boolean enableCache,ObjectLocator locator){
		if(enableCache){
			return locator.autobuild(GaeCachedEntityServiceImpl.class);
		}else{
			return locator.autobuild(JpaEntityServiceImpl.class);
		}
	}

	/**
	 * Contributes the package "&lt;root&gt;.entities" to the configuration, so
	 * that it will be scanned for annotated entity classes.
	 */
	public static void contributeEntityPackageManager(
			Configuration<String> configuration,

			@Inject @Symbol(InternalConstants.TAPESTRY_APP_PACKAGE_PARAM) String appRootPackage) {
		configuration.add(appRootPackage + ".entities");
	}

	// build entity package manager
	public static EntityPackageManager buildEntityPackageManager(
			final Collection<String> packageNames) {
		return new EntityPackageManager() {
			public Collection<String> getPackageNames() {
				return packageNames;
			}
		};
	}

	public static void contributeTypeCoercer(
			Configuration<CoercionTuple> configuration,
			@Builtin final TypeCoercer coercer)

	{
		// from Key => String
		add(configuration, Key.class, String.class,
				new Coercion<Key, String>() {
					public String coerce(Key input) {
						return KeyFactory.keyToString(input);
					}
				});
		// from String=>Key
		add(configuration, String.class, Key.class,
				new Coercion<String, Key>() {
					public Key coerce(String input) {
						return KeyFactory.stringToKey(input);
					}
				});
	}

	private static <S, T> void add(Configuration<CoercionTuple> configuration,
			Class<S> sourceType, Class<T> targetType, Coercion<S, T> coercion) {
		CoercionTuple<S, T> tuple = new CoercionTuple<S, T>(sourceType,
				targetType, coercion);

		configuration.add(tuple);
	}

	public static void contributeValueEncoderSource(
			MappedConfiguration<Class, ValueEncoderFactory> configuration,
			final TypeCoercer typeCoercer, final PropertyAccess propertyAccess,
			final LoggerSource loggerSource,
			final EntityPackageManager packageManager,
			final ClassNameLocator classNameLocator,
			@Local final EntityManager entityManager) {

		ClassLoader contextClassLoader = Thread.currentThread()
				.getContextClassLoader();

		for (String packageName : packageManager.getPackageNames()) {
			for (String className : classNameLocator
					.locateClassNames(packageName)) {
				try {
					final Class<?> entityClass = contextClassLoader
							.loadClass(className);
					if (entityClass.isAnnotationPresent(Entity.class)) {

						ValueEncoderFactory factory = new ValueEncoderFactory() {
							public ValueEncoder create(Class type) {
								return new JpaEntityValueEncoder(entityClass,
										propertyAccess, typeCoercer,
										loggerSource.getLogger(entityClass),
										entityManager);
							}
						};

						configuration.add(entityClass, factory);

					}

				} catch (ClassNotFoundException ex) {
					throw new RuntimeException(ex);
				}
			}
		}

	}

	/**
	 * Contributes the following:
	 * <dl>
	 * <dt>entity</dt>
	 * <dd>Stores the id of the entity and reloads from the
	 * {@link EntityManager}</dd>
	 * </dl>
	 */
	public static void contributePersistentFieldManager(
			MappedConfiguration<String, PersistentFieldStrategy> configuration) {
		configuration.addInstance("entity",
				CornerEntityPersistentFieldStrategy.class);
	}

	public static JpaTemplate buildJpaTemplate(
			@Local EntityManager entityManager) {
		JpaTemplate template = new JpaTemplate(entityManager);
		return template;

	}

	public static EntityManagerFactory buildEntityManagerFactory(
			@Autobuild DatastorePersistenceProvider persistenceProvider) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setPersistenceProvider(persistenceProvider);
		Properties jpaProperties = new Properties();
		jpaProperties.put("datanucleus.NontransactionalRead", "true");
		jpaProperties.put("datanucleus.NontransactionalWrite", "true");
		jpaProperties.put("datanucleus.ConnectionURL", "appengine");
		jpaProperties.put("datanucleus.jpa.addClassTransformer", "false");
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		LoadTimeWeaver loadTimeWeaver = new SimpleLoadTimeWeaver();
		entityManagerFactoryBean.setLoadTimeWeaver(loadTimeWeaver);
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean.getObject();
	}

	@Scope(org.apache.tapestry5.ioc.ScopeConstants.PERTHREAD)
	public static EntityManagerSource buildEntityManagerSource(
			@Local EntityManagerFactory entityManagerFactory, Logger logger,
			PerthreadManager perthreadManager,
			@Autobuild EntityManagerSourceImpl entityManagerSource) {
		perthreadManager.addThreadCleanupListener(entityManagerSource);
		return entityManagerSource;
	}

	public static Delegate buildDelegate(@Builtin ClassFactory classFactory)
			throws Throwable {
		String className = "com.google.appengine.tools.development.ApiProxyLocalImpl";
		ClassFab classFab = classFactory.newClass("MyApiLocalEnvir", Class
				.forName(className));
		classFab.addConstructor(new Class[] { File.class }, new Class[] {},
				"super($1);");
		Class clazz = classFab.createClass();
		return (Delegate) clazz.getConstructor(File.class).newInstance(
				new File("target"));
	}

	public static EntityManager buildEntityManager(
			@Local EntityManagerSource entityManagerSource,
			PropertyShadowBuilder propertyShadowBuilder) {
		return propertyShadowBuilder.build(entityManagerSource,
				"entityManager", EntityManager.class);
	}

	public static PlatformTransactionManager buildPlatformTransactionManager(
			@Local EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager platformTransactionManager = new JpaTransactionManager();
		platformTransactionManager
				.setEntityManagerFactory(entityManagerFactory);
		platformTransactionManager.afterPropertiesSet();
		return platformTransactionManager;
	}

	// initialize  dev thread
	public static void contributeApplicationInitializer(
			OrderedConfiguration<ApplicationInitializerFilter> configuration,
			@Inject @Symbol(SymbolConstants.PRODUCTION_MODE) final boolean product,
			final Delegate delegate) {
		configuration.add("init-gae-dev", new ApplicationInitializerFilter() {

			@Override
			public void initializeApplication(Context context,
					ApplicationInitializer initializer) {
				if (!product) {
					ApiProxy
							.setEnvironmentForCurrentThread(new TestEnvironment());
					ApiProxy.setDelegate(delegate);
				}
				initializer.initializeApplication(context);
			}
		}, "before:*");
	}

	// Open EntityManager In View
	public void contributeRequestHandler(
			OrderedConfiguration<RequestFilter> configuration,
			@Local final EntityManagerSource entityManagerSource) {

		RequestFilter openEntityManagerInView = new RequestFilter() {
			public boolean service(Request request, Response response,
					RequestHandler handler) throws IOException {
				String path = request.getPath();
				// except for dynamic content
				if (path.matches(STATIC_REGEX_PATTERN)) {
					return handler.service(request, response);
				}

				// get entity manager
				// because EntityManagerSource manage the EntityManager
				// lifecycle.
				entityManagerSource.getEntityManager();
				return handler.service(request, response);
			}
		};

		configuration.add("OpenEntityManagerInView", openEntityManagerInView,
				"after:StoreIntoGlobals", "before:EndOfRequest");
	}

}
