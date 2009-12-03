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
package corner.migration.impl;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.ioc.IOCUtilities;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.ApplicationInitializer;
import org.apache.tapestry5.services.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corner.migration.MigrationModule;
import corner.migration.MigrationService;

/**
 * 支持手工升级数据库,运行方式一般是:
 * java -Dtapestry.app-package=com.ouriba.eweb -Dupgrade.script.path=webapp/WEB-INF/groovy-db/ corner.migration.impl.DatabaseUpgrade
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class DatabaseUpgrade {
	public static void main(String [] args){
		RegistryBuilder builder = new RegistryBuilder();
		builder.add(MigrationModule.class);
		IOCUtilities.addDefaultModules(builder);

		Registry registry = builder.build();

		registry.performRegistryStartup();
		SymbolSource symbolSource = registry.getService(SymbolSource.class);
		String scriptPath = symbolSource.valueForSymbol("upgrade.script.path");
		MigrationService service = registry.getService(MigrationService.class);
		
		HibernateSessionManager sessionManager = registry.getService(HibernateSessionManager.class);
        Logger logger= LoggerFactory.getLogger(DatabaseUpgrade.class);
        DBMigrationInitializer initializer = new DBMigrationInitializer(service, sessionManager, logger);
		Context context= createMockContext(scriptPath);
		ApplicationInitializer delegateInitializer=new ApplicationInitializer(){
			@Override
			public void initializeApplication(Context context) {
				System.out.println("\n成功更新数据库!");
				
				
			}};
		initializer.initializeApplication(context, delegateInitializer);
		
	}
	
	private static Context createMockContext(final String scriptPath){
		return new Context(){

			@Override
			public Object getAttribute(String name) {
				
				return null;
			}

			@Override
			public List<String> getAttributeNames() {
				
				return null;
			}

			@Override
			public String getInitParameter(String name) {
				
				return null;
			}

			@Override
			public File getRealFile(String path) {
				return new File(scriptPath);
			}

			@Override
			public URL getResource(String path) {
				
				return null;
			}

			@Override
			public List<String> getResourcePaths(String path) {
				
				return null;
			}

			@Override
			public String getMimeType(String file) {
				return "text/plain";
			}};
	}

}
