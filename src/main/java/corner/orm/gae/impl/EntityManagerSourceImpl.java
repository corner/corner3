/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: EntityManagerSourceImpl.java 5939 2009-09-24 13:51:06Z jcai $
 * created at:2009-09-22
 */

package corner.orm.gae.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ThreadCleanupListener;
import org.slf4j.Logger;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Delegate;

import corner.orm.gae.EntityManagerSource;

/**
 * entity manager source implemention
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 5939 $
 * @since 0.1
 */
public class EntityManagerSourceImpl implements EntityManagerSource,ThreadCleanupListener {
	private boolean participate = false;
	private EntityManagerFactory entityManagerFactory;
	private Logger logger;

	public EntityManagerSourceImpl(@Inject @Symbol(SymbolConstants.PRODUCTION_MODE) boolean product,EntityManagerFactory entityManagerFactory,Logger logger, Delegate delegate) {
		this.entityManagerFactory = entityManagerFactory;
		this.logger = logger;
		if(!product){
			ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
	        ApiProxy.setDelegate(delegate);
		}
		
		if (TransactionSynchronizationManager.hasResource(entityManagerFactory)) {
			// Do not modify the Session: just set the participate flag.
			participate = true;
		}
		else {
			logger.debug("Opening single entity manager  in EntityManagerSourceImpl");
			EntityManager em = entityManagerFactory.createEntityManager();
			TransactionSynchronizationManager.bindResource(entityManagerFactory,
					new EntityManagerHolder(em));
		}

	}

	/**
	 * @see lichen.common.services.jpa.EntityManagerSource#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		EntityManagerHolder emHolder =
			(EntityManagerHolder) TransactionSynchronizationManager.getResource(entityManagerFactory);
		return emHolder.getEntityManager();
	}

	@Override
	public void threadDidCleanup() {
		if (!participate) {
			EntityManagerHolder emHolder = (EntityManagerHolder)
					TransactionSynchronizationManager.unbindResource(entityManagerFactory);
			logger.debug("Closing JPA EntityManager in EntityManagerSourceImpl");
			EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
		}
	}

}
