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


import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.internal.services.LinkFactory;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.slf4j.Logger;

import corner.services.EntityService;

/**
 * 抽象的对实体进行操作表单类
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 5171 $
 * @since 0.0.1
 */
public abstract class AbstractEntityForm<T> {

	/** 前端的用来提交的表单页面 **/
	@SuppressWarnings("unused")
	@Component 
	private Form entityForm;

	/** logger **/
	@Inject 
	private Logger logger;

	/** Entity Service Object **/
	@Inject 
	private EntityService entityService;
	@Inject
	private PropertyConduitSource source;


	
	@Inject 
	private LinkFactory linkFactory;
	/** 用来操作的model对象 **/
	private T entity;

	
	/**
	 * 保存当前操作的实体
	 */
	@OnEvent(value = "success", component = "entityForm")
	Object doSaveOrUpdateEntity() {
		logger.debug("save or update entity ");
		this.saveOrUpdate();
		return getReturnObject();
	}
	
	/**
	 * 保存之后的返回对象,默认情况下返回一个由getReturnPage得到页面跳转
	 * @return 保存之后的返回对象,如一个Link或者Page等
	 * @since 0.0.2
	 */
	protected Object getReturnObject(){
		return linkFactory.createPageRenderLink(getReturnPage(), false);
	}

	/**
	 * 当保存后，跳转的页面.
	 * @return 跳转的页面
	 */
	protected abstract String getReturnPage();
	
	

	@OnEvent(value = "validateForm", component = "entityForm")
	boolean doValidateNewEntityForm() {
		validateEntityForm(this.entityForm,this.getEntity());
		return entityForm.isValid();
	}

	/**
	 * 对提交的表单进行手工的校验
	 * @param entityForm
	 * @param entity
	 */
	protected void validateEntityForm(Form entityForm, T entity) {
		
		
	}

	/**保存对象**/
	protected void saveOrUpdate() {
		entityService.saveOrUpdate(this.entity);
	}
	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		return source.create(this.getClass(),"entity").getPropertyType();
	}
	/**
	 * @return the entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

}