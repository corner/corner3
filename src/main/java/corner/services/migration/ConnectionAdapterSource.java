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


import org.hibernate.dialect.Dialect;

/**
 * 连接适配器的Source
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 2100 $
 * @since 0.0.2
 */
public interface ConnectionAdapterSource {

	/**
	 * 通过给定的Hibernate的方言来得到对应的数据库适配器
	 * @param clazz
	 * @return
	 * @since 0.0.2
	 */
	public ConnectionAdapter getConnectionAdapter(Class<? extends Dialect> clazz);
}
