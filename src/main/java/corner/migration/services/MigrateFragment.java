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

import java.util.List;

import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.TableMetadata;

/**
 * 升级片段
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
 public interface MigrateFragment {

	/**
	 * 确定对那些表进行过滤
	 * @param table 待处理的表对象
	 * @return 是否进行处理
	 * @since 0.0.2
	 */
	public boolean filteTable(Table table);
	/**
	 * 通过给定的表格对象来产生升级的脚本sql
	 * @param table 表对象
	 * @param tableInfo 表信息
	 * @return 升级的列表SQL
	 * @since 0.0.2
	 */
	List<String> generateMigrationFragments(Table table, TableMetadata tableInfo);
}