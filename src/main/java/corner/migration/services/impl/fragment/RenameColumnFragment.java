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
package corner.migration.services.impl.fragment;

import java.util.List;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.TableMetadata;

import corner.migration.services.ConnectionAdapter;

/**
 * 重新命名列的碎片
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class RenameColumnFragment extends AbstractMigrateFragment {

	private String []  oldColumnsNames;
	private String[] newColumnNames;

	public RenameColumnFragment(ConnectionAdapter adapter, Dialect dialect,
			SessionFactoryImplementor sessionFactory, String tableName,
			String[] oldColumnsNames,String[] newColumnNames) {
		super(adapter, dialect, sessionFactory, tableName);
		this.oldColumnsNames = oldColumnsNames;
		this.newColumnNames = newColumnNames;
		
	}

	/**
	 * @see corner.migration.services.MigrateFragment#generateMigrationFragments(org.hibernate.mapping.Table, org.hibernate.tool.hbm2ddl.TableMetadata)
	 */
	@Override
	public List<String> generateMigrationFragments(Table table,
			TableMetadata tableInfo) {
		return  this.getConnectionAdapter().renameColumnSQL(getQualifiedTableName(table), oldColumnsNames, newColumnNames);

	}
}
