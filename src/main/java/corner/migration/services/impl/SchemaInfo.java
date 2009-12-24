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
package corner.migration.services.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import corner.orm.base.BaseEntity;


/**
 * 用来保存数据库的表格
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
@Entity
@Table(name=MigrationServiceImpl.SCHEMA_INFO_TABLE_NAME)
public class SchemaInfo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -591712644341275336L;

	private int dbversion;

	private int indexversion;

	/**
	 * @return the dbversion
	 */
	@Column(name="db_version")
	public int getDbversion() {
		return dbversion;
	}

	/**
	 * @param dbversion the dbversion to set
	 */
	public void setDbversion(int dbversion) {
		this.dbversion = dbversion;
	}

	/**
	 * @return the indexversion
	 */
	@Column(name="index_version")
	public int getIndexversion() {
		return indexversion;
	}

	/**
	 * @param indexversion the indexversion to set
	 */
	public void setIndexversion(int indexversion) {
		this.indexversion = indexversion;
	}
}
