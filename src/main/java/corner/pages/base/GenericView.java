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
package corner.pages.base;


import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.apache.tapestry5.services.ValueEncoderSource;

import corner.exceptions.NoSuchResourceException;
import corner.services.security.ResourceAclOperation;
import corner.services.security.ResourceAclServcie;
import corner.services.security.ResourceSecurity;
import corner.services.security.ResourceSecurityObject;

/**
 * 范性化的浏览页面
 * 
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @author <a href="d0ng@ouriba.com">dong</a>
 * @version $Revision: 3822 $
 * @since 0.0.1
 */
public class GenericView<T> {
	/** 用来操作的model对象 * */
	private T entity;

	@Inject
	private ValueEncoderSource valueEncoderSource;

	@Inject
	private PropertyConduitSource source;

	/** 是否已经初始化 * */
	private boolean inited = false;

	/** 实体的id号 * */
	private String id;

	void onActivate(String id) {
		this.id = id;

	}

	Object onPassivate() {
		return this.getEntity();
	}

	/**
	 * @return the entity
	 */
	public T getEntity() {
		if (!this.inited) {
			// 得到对应持久化对象
			ValueEncoder<T> encoder = valueEncoderSource.getValueEncoder(this
					.getEntityClass());
			/*
			 * 校验是否有与id对应的资源并检查当前的访问者是否有权限查看该资源
			 */
			T _entity = encoder.toValue(id);
			if (_entity == null) {
				throw new NoSuchResourceException(id);
			}
			this.validateResourceAcl(_entity, ResourceAclOperation.READ);
			this.setEntity(encoder.toValue(id));
			this.inited = true;
		}
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		return source.create(this.getClass(), "entity").getPropertyType();
	}

	/**
	 * 用于校验访问者是否有权限访问entity这个资源
	 * 
	 * @param entity
	 * @param operation
	 * @throws corner.exceptions.AclException
	 *             如果访问者没有访问资源的权限,则抛出此异常
	 * @since 0.0.2
	 * 
	 */
	@ResourceSecurity(service = ResourceAclServcie.class)
	protected void validateResourceAcl(@ResourceSecurityObject
	T entity, @ResourceSecurityObject
	ResourceAclOperation operation) {

	}
}
