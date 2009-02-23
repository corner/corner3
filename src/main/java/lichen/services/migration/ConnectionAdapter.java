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
package lichen.services.migration;

import java.util.List;

import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.TableMetadata;

/**
 * 针对数据库的适配器
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 2088 $
 * @since 0.0.2
 */
public interface ConnectionAdapter {

	/**
	 * 抓取表格信息
	 * @since 0.0.2
	 */
	TableMetadata fetchTableInfo(DatabaseMetadata meta, Table table,
			String defaultCatalog, String defaultSchema);

	//修改列的SQL脚本
	String changeColumnSQL(String name, String type);

	//删除列的SQL脚本
	List<String> removeColumnSQL(String tableName, String[] tableColumns);

	//重新命名列名
	List<String> renameColumnSQL(String tableName, String[] oldColumnsNames,
			String[] newColumnNames);

}
