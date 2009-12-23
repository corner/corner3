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
package corner.migration.services.impl.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.Mapping;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.ColumnMetadata;
import org.hibernate.tool.hbm2ddl.TableMetadata;

import corner.migration.services.ConnectionAdapter;

/**
 * 改变列的碎片
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class ChangeColumnFragment extends AbstractMigrateFragment {

	private String columnName;

	public ChangeColumnFragment(ConnectionAdapter adapter,Dialect dialect,
			SessionFactoryImplementor sessionFactory, String tableName,String columnName) {
		super(adapter, dialect, sessionFactory, tableName);
		this.columnName = columnName;
		
	}

	/**
	 * @see corner.migration.services.MigrateFragment#generateMigrationFragments(org.hibernate.mapping.Table, org.hibernate.tool.hbm2ddl.TableMetadata)
	 */
	@Override
	public List<String> generateMigrationFragments(Table table,
			TableMetadata tableInfo) {
		
		List<String> script = new ArrayList<String>();
		String alterSQL = sqlAlterStrings(table, columnName,getDialect(), getSessionFactory(),tableInfo, defaultCatalog, defaultSchema);
		if(InternalUtils.isNonBlank(alterSQL)){
			script.add(alterSQL);
		} else{
			System.out.println("因为给定的表名或者列名不存在，没有生成对应的DDL语句!");
		}
		return script;
	}

	// 根据不同的数据库生成DDL脚本的方法
	String sqlAlterStrings(Table table,String columnName, Dialect dialect, Mapping p, TableMetadata tableInfo, 
			String defaultCatalog, String defaultSchema) throws HibernateException {
		
		Iterator iter = table.getColumnIterator();
		String alterSQL = null;
		
		while (iter.hasNext()) {
			Column column = (Column) iter.next();
			ColumnMetadata columnInfo = tableInfo.getColumnMetadata(column.getName());
			if (columnInfo != null && columnInfo.getName().toLowerCase().equals(columnName.toLowerCase())) {//列存在,且与给定的列的名字相同
				alterSQL = this.getConnectionAdapter().changeColumnSQL(table.getName(), getSQLType(column)); 
			} 

		}
		return alterSQL;
	}
	

}
