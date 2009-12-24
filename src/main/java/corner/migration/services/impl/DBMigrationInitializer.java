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
package corner.migration.services.impl;


import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.services.ApplicationInitializer;
import org.apache.tapestry5.services.Context;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;

import corner.migration.services.MigrationService;
import corner.migration.services.impl.console.ConsoleBackgroundColor;
import corner.migration.services.impl.console.ConsoleForegroundColor;
import corner.migration.services.impl.console.IConsole;
import corner.migration.services.impl.console.UnixConsole;


/**
 * 对数据库的初始化操作.

 * @version $Revision$
 * @since 0.9.0
 */
public class DBMigrationInitializer extends AbstractDBMigrationInitializer {
	
	private HibernateSessionManager sessionManager;
    private Logger logger;

	public DBMigrationInitializer(
            MigrationService migrationService, HibernateSessionManager manager, Logger logger) {
		super(migrationService);
		this.sessionManager = manager;

        this.logger = logger;
    }

	private static final String GROOVY_DB_PATH = "/WEB-INF/groovy-db/";

	@Override
	public void initializeApplication(Context context,
				ApplicationInitializer initializer) {
		IConsole console=new UnixConsole();
		Session session =  sessionManager.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			console.resetColors();
			//设定console的颜色
			console.setBackgroundColor(ConsoleBackgroundColor.BLACK);
			console.setForegroundColor(ConsoleForegroundColor.LIGHT_GREEN);
			
			
			//初始化SchemaInfo 表格
			int dbVersion=this.getMigrationService().initSchemaInfo().getDbversion();
			logger.info("get database version :["+dbVersion+"]");
			int maxVersion=0;
			
			//更新public schema下的表
			maxVersion = this.executeDbScript(DB_SCRIPT_TYPE_STR,dbVersion, getPath(context,GROOVY_DB_PATH));
			logger.info("file version :["+maxVersion+"]");
			
			// 把数据库的db字段更新到最大值.
			if(maxVersion>0){
				logger.info("update database version :["+maxVersion+"]");
				this.getMigrationService().updateDbMaxVersion(DB_SCRIPT_TYPE_STR,maxVersion);
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		}finally{
			console.resetColors();
		}
		initializer.initializeApplication(context);
		
	}

	

}
