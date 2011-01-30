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
package corner.migration.services.impl.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;

/**
 * 针对H2数据库的连接适配器
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class H2ConnectionAdapter extends AbstractConnectionAdapter {
		/**
		 * 修改列
		 */
		private static final String CHAGE_COLUMN_SQL = "ALTER TABLE %1$s ALTER COLUMN %2$s";

		/**
		 * 删除列
		 */
		private static final String DROP_COLUMN_SQL = "ALTER TABLE %1$s DROP COLUMN %2$s";
		
		/**
		 * 修改列名
		 */
		private static final String RENAME_COLUMN_SQL = "ALTER TABLE %1$s ALTER COLUMN %2$s RENAME TO %3$s";

		/**
		 * 删除索引
		 */
		private static final String REMOVE_INDEX_SQL = "DROP INDEX IF EXISTS %1$s";
		

		/**
		 * @see com.bjmaxinfo.piano.database.IConnectionAdapter#changeColumnSQL(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public String changeColumnSQL(String tableName, String newColumnType) {
			return  String.format(CHAGE_COLUMN_SQL, tableName,newColumnType);
		}
		
		/**
		 * @see com.bjmaxinfo.piano.database.IConnectionAdapter#removeColumnSQL(java.lang.String, java.lang.String[])
		 */
		@Override
		public List<String> removeColumnSQL(String tableName, String[] tableColumns) {
			List<String> dropList = null;
			if(tableColumns != null && tableColumns.length>0){
				dropList = new ArrayList<String>();    
				
				for(String s :tableColumns){
					dropList.add(String.format(DROP_COLUMN_SQL, tableName, s));
				}
			} 
			return dropList;
		}

		/**
		 * @see com.bjmaxinfo.piano.database.IConnectionAdapter#renameColumnSQL(java.lang.String, java.lang.String[], java.lang.String[])
		 */
		@Override
		public List<String> renameColumnSQL(String tableName, String[] oldColumnsNames, String[] newColumnNames) {
			List<String> renameList = null;
			if(newColumnNames != null && newColumnNames.length>0 && newColumnNames.length>=oldColumnsNames.length){
				renameList = new ArrayList<String>();
				for(int i=0;i<oldColumnsNames.length;i++){
					renameList.add(String.format(RENAME_COLUMN_SQL, tableName, oldColumnsNames[i], newColumnNames[i]));
				}
			}
			return renameList;
		}

		/**
		 * @see com.bjmaxinfo.piano.database.IConnectionAdapter#removeIndexSQL(java.lang.String, java.lang.String)
		 */
		public List<String> removeIndexSQL(String tableName, String indexName) {
			List<String> removeIndex = null;
			if(InternalUtils.isNonBlank(tableName) && InternalUtils.isNonBlank(indexName)){
				removeIndex = new ArrayList<String>();
				removeIndex.add(String.format(REMOVE_INDEX_SQL, indexName));
			}
			return removeIndex;
		}

}
