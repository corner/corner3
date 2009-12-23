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


import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.Mapping;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;

import corner.migration.services.ConnectionAdapter;
import corner.migration.services.MigrateFragment;

/**
 * 抽象的数据库升级碎片
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
abstract class AbstractMigrateFragment implements MigrateFragment{
	private Dialect dialect;
	private SessionFactoryImplementor sessionFactory;
	private String tableName;
	private ConnectionAdapter adapter;

	static final String defaultCatalog = Environment.getProperties().getProperty(Environment.DEFAULT_CATALOG);
	static final String defaultSchema = Environment.getProperties().getProperty(Environment.DEFAULT_SCHEMA);
	
	public AbstractMigrateFragment(ConnectionAdapter adapter,Dialect dialect,
			SessionFactoryImplementor sessionFactory, String tableName) {
		this.dialect = dialect;
		this.sessionFactory = sessionFactory;
		this.tableName = tableName;
		this.adapter = adapter;
	}

	@Override
	public boolean filteTable(Table table) {
		return table.getName().equalsIgnoreCase(getTableName());
	}
	/**
	 * @return the dialect
	 */
	public Dialect getDialect() {
		return dialect;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactoryImplementor getSessionFactory() {
		return sessionFactory;
	}
	protected ConnectionAdapter getConnectionAdapter() {
		return this.adapter;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * 通过给定的列对象，来得到改列的SQL类型声明
	 * @param col 列对象
	 * @return SQL 类型声明
	 * @see Table#sqlCreateString(Dialect, Mapping, String, String)
	 */
	protected String getSQLType(Column col){
//		boolean identityColumn = idValue != null && idValue.isIdentityColumn( dialect );
		boolean identityColumn = false;
		StringBuffer buf = new StringBuffer();
		Dialect dialect = getDialect();
		Mapping p = this.getSessionFactory();
		buf.append( col.getQuotedName( dialect) ).append( ' ' );

		String pkname = "id";
		if ( identityColumn && col.getQuotedName( dialect ).equals( pkname  ) ) {
			// to support dialects that have their own identity data type
			if ( dialect.hasDataTypeInIdentityColumn() ) {
				buf.append( col.getSqlType( dialect, p ) );
			}
			buf.append( ' ' )
					.append( dialect.getIdentityColumnString( col.getSqlTypeCode( p ) ) );
		}
		else {
		
			buf.append( col.getSqlType( dialect, p ) );
		
			String defaultValue = col.getDefaultValue();
			if ( defaultValue != null ) {
				buf.append( " default " ).append( defaultValue );
			}
		
			if ( col.isNullable() ) {
				buf.append( dialect.getNullColumnString() );
			}
			else {
				buf.append( " not null" );
			}
		
		}
		
		boolean useUniqueConstraint = col.isUnique() &&
				( !col.isNullable() || dialect.supportsNotNullUnique() );
			if ( useUniqueConstraint ) {
				if ( dialect.supportsUnique() ) {
					buf.append( " unique" );
				}
				else {
//					UniqueKey uk = getOrCreateUniqueKey( col.getQuotedName( dialect ) + '_' );
//					uk.addColumn( col );
				}
			}
			
			if ( col.hasCheckConstraint() && dialect.supportsColumnCheck() ) {
				buf.append( " check (" )
						.append( col.getCheckConstraint() )
						.append( ")" );
			}
			
			String columnComment = col.getComment();
			if ( columnComment != null ) {
				buf.append( dialect.getColumnComment( columnComment ) );
			}
			return buf.toString();
	}
}
