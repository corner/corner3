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
package corner.orm.services;

import java.util.Iterator;

import org.springframework.transaction.annotation.Transactional;

import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;

/**
 * 定义EntityService的服务类
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface EntityService {
	/**
	 * 保存一个实体
	 * @param entity 实体对象
	 * @since 3.1
	 */
	@Transactional
	public void save(Object entity);
	/**
	 * 删除一个实体
	 * @param entity 实体对象
	 * @since 3.1
	 */
	@Transactional
	public void delete(Object entity);
	/**
	 * 更新一个实体
	 * @param entity 实体对象
	 * @since 3.1
	 */
	@Transactional
	public void update(Object entity);
	/**
	 * 加载一个实体
	 * @param <T> 加载实体对象
	 * @param clazz 类
	 * @param id id
	 * @return 实体对象，如果数据库，没有则返回null
	 * @since 3.1
	 */
	public <T> T get(Class<T> clazz,Object id);
	/**
	 * 查询一个实体的总数
	 * @param entityClass persistence class
	 * @param conditions query conditions
	 * @return total number
	 * @since 3.1
	 */
	public long count(Class<?> entityClass, Object conditions);
	/**
	 * 根据给定的实体类，和条件以及排序进行查询
	 * @param entityClass persistence class
	 * @param conditions query conditions
	 * @param order query order
	 * @return query result
	 * @since 3.1
	 */
	public Iterator find(Class<?> entityClass, Object conditions, String order);
	/**
	 * 根据给定的实体类，和条件以及排序进行查询
	 * @param entityClass persistence class
	 * @param conditions query conditions
	 * @param order query order
	 * @param start start number
	 * @param offset offset count
	 * @return query result
	 * @since 3.1
	 */
	public Iterator find(Class<?> entityClass, Object conditions,String order, int start, int offset) ;

	/**
	 * 根据给定的实体类、条件、分页参数以及排序进行查询
	 * @param entityClass persistence class
	 * @param conditions query conditions
	 * @param order query order
	 * @param options 分页使用的参数
	 * @return 查询结果集，同时包含了分页使用的参数
	 * @since 3.1
	 */
	public PaginationList paginate(Class<?> entityClass, Object conditions,String order, PaginationOptions options) ;
	/**
	 * 重新load一下实体，通常为了从数据库中再次获取数据
	 * @param entity 实体
	 * @since 3.1
	 */
	public void refresh(Object entity);

}
