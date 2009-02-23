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
package corner.services.migration;

import corner.services.migration.impl.SchemaInfo;


/**
 * 用来数据库升级的服务类
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 3143 $
 * @since 0.0.2
 */
public interface MigrationService {
	/**
	 * 创建表格
	 * @param tableName 表的名称
	 * @since 0.0.2
	 */
	public void createTable(String tableName);

	public void addColumn(final String tableName);

	/**
	 * 修改Column内容
	 * @param tableName 表名
	 * @param columnName 列名
	 */
	public void changeColumn(final String tableName, final String columnName);

	/**
	 * 删除列的SQL
	 * @param tableName 表名
	 * @param tableColumns 待删除的列名
	 * @since 0.0.2
	 */
	public void removeColumn(String tableName, String... tableColumns);

	/**
	 * 重命名列
	 * @param tableName 表名称
	 * @param oldColumns 旧的列名
	 * @param newColumns 新的列名
	 * @since 0.0.2
	 */
	public void renameColumns(String tableName, String[] oldColumns, String[] newColumns);

	/**
	 * 删除表
	 * @param tableName 表名
	 * @since 0.0.2
	 */
	public void dropTable(String tableName);
	

	/**
	 * 更新数据库的最大版本号
	 * @param dbScriptTypeStr
	 * @param maxVersion
	 * @since 0.0.2
	 */
	public void updateDbMaxVersion(int dbScriptTypeStr, int maxVersion);

	/**
	 * 初始华数据库的 schema信息
	 * @return 
	 * @since 0.0.2
	 */
	public SchemaInfo initSchemaInfo();

	/**
	 * 执行sql语句
	 * @param sql 待执行的SQL语句
	 * @since 0.0.2
	 */
	public void executeSql(String sql);
}
