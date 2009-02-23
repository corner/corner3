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
package corner.services.migration.impl.adapter;


import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.TableMetadata;

import corner.services.migration.ConnectionAdapter;

/**
 * 抽象的连接适配器
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 2095 $
 * @since 0.0.2
 */
public abstract class AbstractConnectionAdapter implements ConnectionAdapter{
	@Override
	public TableMetadata fetchTableInfo(DatabaseMetadata meta, Table table, String defaultCatalog, String defaultSchema) {
		TableMetadata tableInfo = meta.getTableMetadata(
					table.getName(),
					( table.getSchema() == null ) ? defaultSchema : table.getSchema(),
					( table.getCatalog() == null ) ? defaultCatalog : table.getCatalog(),
							table.isQuoted());
			
			return tableInfo;
	}
}
