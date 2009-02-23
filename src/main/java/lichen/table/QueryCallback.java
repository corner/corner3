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
package lichen.table;

import org.hibernate.Criteria;

/**
 * 用来查询使用的回掉
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public interface QueryCallback {
	/**
	 * 创建一个查询对象，通常是 
	 * session.createCriteria(User.class)
	 * @return Hibernate 的查询对象
	 * @see Criteria
	 */
	public Criteria createCriteria();
	/**
	 * 应用查询条件
	 * @param criteria 查询对象
	 */
	public void appendCriteria(Criteria criteria);
	public void appendOrder(Criteria criteria);

}
