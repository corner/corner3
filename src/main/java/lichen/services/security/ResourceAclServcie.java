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
package lichen.services.security;

/**
 * 用于校验资源的访问权限的接口
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public interface ResourceAclServcie<T> {
	/**
	 * 校验当前的访问者是否拥有的entity的访问权限
	 * @param entity 被校验的资源实体
	 * @param operation 当前请求的操作
	 * @return true,拥有权限;false 没有权限
	 * @since 0.0.2
	 */
	public boolean aclCheck(T entity,ResourceAclOperation operation);

}
