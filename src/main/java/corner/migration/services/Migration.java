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
package corner.migration.services;

/**
 * 用来数据库升级使用的接口
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public interface Migration {

	public void setMigrationService(MigrationService service);
	
	/**
	 * 升级数据库.
	 *
	 */
	public void self_up();

	/**
	 * 向表增加列
	 * @param tableName
	 * @see corner.migration.services.MigrationService#addColumn(java.lang.String)
	 */
	public void add_column(String tableName);

	/**
	 * 改变列
	 * @param tableName
	 * @param columnName
	 * @see corner.migration.services.MigrationService#changeColumn(java.lang.String, java.lang.String)
	 */
	public void change_column(String tableName, String columnName);

	/**
	 * 创建表
	 * @param tableName
	 * @see corner.migration.services.MigrationService#createTable(java.lang.String)
	 */
	public void create_table(String tableName);

	/**
	 * 删除表
	 * @param tableName
	 * @see corner.migration.services.MigrationService#dropTable(java.lang.String)
	 */
	public void drop_table(String tableName);

	/**
	 * 删除列
	 * @param tableName
	 * @param tableColumns
	 * @see corner.migration.services.MigrationService#removeColumn(java.lang.String, java.lang.String[])
	 */
	public void remove_columns(String tableName, String... tableColumns);

	/**
	 * 重命名列
	 * @param tableName
	 * @param oldColumns
	 * @param newColumns
	 * @see corner.migration.services.MigrationService#renameColumns(java.lang.String, java.lang.String[], java.lang.String[])
	 */
	public void rename_columns(String tableName, String[] oldColumns, String[] newColumns);
	/**
	 * 直接执行SQL语句
	 * @param sql sql语句
	 * @since 0.0.2
	 */
	public void execute_sql(String sql);
	
}
