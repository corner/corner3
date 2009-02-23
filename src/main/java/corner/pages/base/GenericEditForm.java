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
package corner.pages.base;


import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.slf4j.Logger;

import corner.exceptions.NoSuchResourceException;
import corner.services.security.ResourceAclOperation;
import corner.services.security.ResourceAclServcie;
import corner.services.security.ResourceSecurity;
import corner.services.security.ResourceSecurityObject;

/**
 * 范性化的表单页面
 * 
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @author <a href="d0ng@ouriba.com">d0ng</a>
 * @version $Revision: 3822 $
 * @since 0.0.1
 */
public class GenericEditForm<T> extends AbstractEntityForm<T> {

	/** logger * */
	@Inject
	private Logger logger;

	@Inject
	private ComponentResources resources;
	@Inject
	private ValueEncoderSource valueEncoderSource;

	/** 是否已经初始化 **/
	private boolean inited = false;
	
	/** 实体的id号 **/
	private String id;

	void onActivate(String id) {
		this.id = id;
	}

	Object onPassivate() {
		return this.getEntity();
	}

	@Override
	public T getEntity() {
		if (!inited) {
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
			this.validateResourceAcl(_entity, ResourceAclOperation.WRITE);
			this.setEntity(_entity);
			this.inited = true;
		}
		return super.getEntity();
	}

	/**
	 * 得到返回的页面，子类可以覆盖
	 * 
	 * @return 得到返回的页面
	 */
	@Override
	protected String getReturnPage() {
		// 得到本页面的名称
		String pageName = resources.getPageName();
		logger.debug("entity new page name:[" + pageName + "]");
		// 构造列表页面的名称
		String listPageName = pageName.replaceAll("([\\w\\/]*[^\\/]*)Edit",
				"$1List");

		logger.debug("list page name:[" + listPageName + "]");

		return listPageName;
	}

	/**
	 * 校验当前的访问是否有权限修改entity
	 * 
	 * @param entity
	 * @param operation
	 * @since 0.0.2
	 */
	@ResourceSecurity(service = ResourceAclServcie.class)
	protected void validateResourceAcl(@ResourceSecurityObject
	T entity, @ResourceSecurityObject
	ResourceAclOperation operation) {

	}

}
