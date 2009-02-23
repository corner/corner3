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
package lichen.services.migration.impl;

import lichen.services.migration.MigrationService;
import lichen.services.migration.impl.console.ConsoleBackgroundColor;
import lichen.services.migration.impl.console.ConsoleForegroundColor;
import lichen.services.migration.impl.console.IConsole;
import lichen.services.migration.impl.console.UnixConsole;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.services.ApplicationInitializer;
import org.apache.tapestry5.services.Context;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * 对数据库的初始化操作.

 * @version $Revision: 5160 $
 * @since 0.9.0
 */
public class DBMigrationInitializer extends AbstractDBMigrationInitializer {
	
	private HibernateSessionManager sessionManager;

	public DBMigrationInitializer(
			MigrationService migrationService,HibernateSessionManager manager) {
		super(migrationService);
		this.sessionManager = manager;
		
	}

	private static final String GROOVY_DB_PATH = "/WEB-INF/groovy-db/";

	@Override
	public void initializeApplication(Context context,
				ApplicationInitializer initializer) {
		IConsole console=new UnixConsole();
		Session session =  sessionManager.getSession();
		SessionFactory sessionFactory = session.getSessionFactory();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			console.resetColors();
			//设定console的颜色
			console.setBackgroundColor(ConsoleBackgroundColor.BLACK);
			console.setForegroundColor(ConsoleForegroundColor.LIGHT_GREEN);
			
			
			//初始化SchemaInfo 表格
			int dbVersion=this.getMigrationService().initSchemaInfo().getDbversion();
			System.out.println("get database version :["+dbVersion+"]");
			int maxVersion=0;
			
			//更新public schema下的表
			maxVersion = this.executeDbScript(DB_SCRIPT_TYPE_STR,dbVersion, getPath(context,GROOVY_DB_PATH));
			System.out.println("file version :["+maxVersion+"]");
			
			// 把数据库的db字段更新到最大值.
			if(maxVersion>0){
				System.out.println("update database version :["+maxVersion+"]");
				this.getMigrationService().updateDbMaxVersion(DB_SCRIPT_TYPE_STR,maxVersion);
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		}finally{
			console.resetColors();
			TransactionSynchronizationManager.unbindResource(sessionFactory);
		}
		initializer.initializeApplication(context);
		
	}

	

}
