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
package lichen.pages.base;

import java.io.Serializable;

import lichen.components.ConfirmActionLink;
import lichen.components.Pagination;
import lichen.components.TableRow;
import lichen.components.TableView;
import lichen.services.EntityService;
import lichen.services.security.ResourceAclOperation;
import lichen.services.security.ResourceAclServcie;
import lichen.services.security.ResourceSecurity;
import lichen.services.security.ResourceSecurityObject;
import lichen.table.QueryCallback;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.internal.services.LinkFactory;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * 范型的列表页面
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 3789 $
 * @since 0.0.1
 */
public class GenericList<T> {
	private T entity;
	@Inject
	private Logger logger;
	@Inject
	private PropertyConduitSource source;
	@Inject
	private Session session;
	@Inject
	private ValueEncoderSource valueEncoderSource;
	
	@Inject
	private LinkFactory linkFactory;
	
	@Inject
	private ComponentResources resources;
	
	@SuppressWarnings("unused")
	@Component(parameters={"callback=queryEntityCallback"})
	private TableView entityTable;
	
	@SuppressWarnings("unused")
	@Component(parameters={"value=entity"})
	private TableRow entityRow;
	
	@SuppressWarnings("unused")
	@Component
	private Pagination entityPagination;
	
	@SuppressWarnings("unused")
	@Component(parameters={"context=entity","msg=message:delete.link"})
	private ConfirmActionLink entityDeleteLink;
	
	@SuppressWarnings("unused")
	@Component(parameters={"context=entity"})
	private ActionLink editEntityLink;
	
	@Inject
	private EntityService entityService;
	
	@OnEvent(component="entityDeleteLink")
	void doDeleteEntityAction(String id){
		logger.debug("delete entity by Id ["+id+"]");
		ValueEncoder<T> encoder = valueEncoderSource.getValueEncoder(this.getQueryEntityClass());
		
		T tmpObj = encoder.toValue(id);
		this.validateResourceAcl(tmpObj,ResourceAclOperation.WRITE);
		this.deleteEntity(tmpObj);
	}
	protected void deleteEntity(T entity){
		this.entityService.delete(entity);
		
	}
	@OnEvent(component="editEntityLink")
	Object doEditEntityAction(Serializable key){
		return linkFactory.createPageRenderLink(getEditPageName(), false, key);
	}
	protected String getEditPageName() {
		//得到本页面的名称
		String pageName = resources.getPageName();
		logger.debug("list page name:["+pageName+"]");
		//构造列表页面的名称
		String editPageName=pageName.replaceAll("([\\w\\/]*[^\\/]*)List", "$1Edit");
		
		logger.debug("edit page name:["+editPageName+"]");
		
		return editPageName;
	}
	/**
	 * 用来查询的回掉函数
	 * @return 回掉函数
	 */
	public QueryCallback getQueryEntityCallback(){
		return new QueryCallback(){

			public void appendCriteria(Criteria criteria) {
				GenericList.this.appendCriteria(criteria);
			}

			public void appendOrder(Criteria criteria) {
				GenericList.this.appendOrder(criteria);
			}

			public Criteria createCriteria() {
				return session.createCriteria(getQueryEntityClass());
			}};
	}

	protected void appendCriteria(Criteria criteria) {
			
			
	}
	protected void appendOrder(Criteria criteria) {
		
		
	}
	@SuppressWarnings("unchecked")
	protected Class<T> getQueryEntityClass(){
		return  source.create(this.getClass(),"entity").getPropertyType();
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
	
	/**
	 * 校验当前的访问是否有权限删除entity
	 * @param entity
	 * @param operation
	 * @since 0.0.2
	 */
	@ResourceSecurity(service=ResourceAclServcie.class)
	protected void validateResourceAcl(@ResourceSecurityObject T entity,@ResourceSecurityObject ResourceAclOperation operation) {

	}
}
