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
package corner.services.migration;

/**
 * 抽象的基础升级类
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 3143 $
 * @since 0.0.2
 */
public abstract class BaseMigration implements Migration{

	private MigrationService service;
	/**
	 * @see corner.services.migration.Migration#setMigrationService(corner.services.migration.MigrationService)
	 */
	@Override
	public void setMigrationService(MigrationService service) {
		this.service = service;
	}


	/**
	 * @param tableName
	 * @see corner.services.migration.MigrationService#addColumn(java.lang.String)
	 */
	public void add_column(String tableName) {
		service.addColumn(tableName);
	}


	/**
	 * @param tableName
	 * @param columnName
	 * @see corner.services.migration.MigrationService#changeColumn(java.lang.String, java.lang.String)
	 */
	public void change_column(String tableName, String columnName) {
		service.changeColumn(tableName, columnName);
	}


	/**
	 * @param tableName
	 * @see corner.services.migration.MigrationService#createTable(java.lang.String)
	 */
	public void create_table(String tableName) {
		service.createTable(tableName);
	}


	/**
	 * @param tableName
	 * @see corner.services.migration.MigrationService#dropTable(java.lang.String)
	 */
	public void drop_table(String tableName) {
		service.dropTable(tableName);
	}


	/**
	 * @param tableName
	 * @param tableColumns
	 * @see corner.services.migration.MigrationService#removeColumn(java.lang.String, java.lang.String[])
	 */
	public void remove_columns(String tableName, String... tableColumns) {
		service.removeColumn(tableName, tableColumns);
	}


	/**
	 * @param tableName
	 * @param oldColumns
	 * @param newColumns
	 * @see corner.services.migration.MigrationService#renameColumns(java.lang.String, java.lang.String[], java.lang.String[])
	 */
	public void rename_columns(String tableName, String[] oldColumns,
			String[] newColumns) {
		service.renameColumns(tableName, oldColumns, newColumns);
	}


	/**
	 * @see corner.services.migration.Migration#execute_sql(java.lang.String)
	 */
	@Override
	public void execute_sql(String sql) {
		service.executeSql(sql);
	}

}
