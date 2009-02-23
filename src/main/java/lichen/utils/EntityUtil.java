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
package lichen.utils;

/**
 * 用来对实体类进行常规操作的util类.
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 716 $
 * @since 0.0.1
 */
public class EntityUtil {
	/**
	 * 得到持久化类的名称。
	 * 
	 * @param entity
	 *            待处理的持久化类。
	 * @return 持久化类的名称。
	 * @since 0.0.1
	 */
	@SuppressWarnings("unchecked")
	public  static <E> Class<E> getEntityClass(E entity) {
		if (entity.getClass().getName().contains("CGLIB")) {
			return (Class<E>) entity.getClass().getSuperclass();
		}
		return (Class<E>) entity.getClass();
	}

}
