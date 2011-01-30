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
package corner.tree.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.HibernateEntityService;
import corner.orm.services.EntityService;
import corner.tree.base.TreeAdapter;
import corner.tree.services.MoveTreeNodeProcessor;
import corner.tree.services.TreeService;

/**
 * 提供了对tee组件的操作服务类.
 * 
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1 
 */
public class TreeServiceImpl implements TreeService {

	
	//删除节点
	private static final String DELETE_NODE_HSQL="delete %s n where (n."+TreeAdapter.LEFT_PRO_NAME+" between ? and ? )";
	//更新左边节点
	private static final String UPDATE_LEFT_HSQL="update %s n set n."+TreeAdapter.LEFT_PRO_NAME+"=n."+TreeAdapter.LEFT_PRO_NAME+"+%d where n."+TreeAdapter.LEFT_PRO_NAME+">?";
	//更新右边节点
	private static final String UPDATE_RIGHT_HSQL="update %s n set n."+TreeAdapter.RIGHT_PRO_NAME+"=n."+TreeAdapter.RIGHT_PRO_NAME+"+%d where n."+TreeAdapter.RIGHT_PRO_NAME+">?";
	private EntityService entityService;
	private HibernateEntityService hibernateEntityService;
	
	public TreeServiceImpl(EntityService service,HibernateEntityService hibernateEntityService){
		this.entityService = service;
		this.hibernateEntityService = hibernateEntityService;
	}

	
	
	
	
	
	/**
	 * @see corner.tree.services.ouriba.eweb.services.tree.TreeService#saveTreeChildNode(corner.tree.base.ouriba.eweb.entities.base.TreeAdapter, corner.tree.base.ouriba.eweb.entities.base.TreeAdapter, java.lang.Class)
	 */
	@Override
	public void saveTreeChildNode(TreeAdapter node, TreeAdapter parentNode, Class<? extends TreeAdapter> clazz) {
		String treeClassName = getTreeClassName(node,clazz);

		TreeAdapter currentParentNode = parentNode;
		if (currentParentNode == null) { // 插入顶级节点
			currentParentNode = (TreeAdapter) BeanUtils.instantiateClass(entityService.getEntityClass(node));
			currentParentNode.setLeft(0);
			currentParentNode.setDepth(0);
			long rowCount;
			try {
				rowCount = this.entityService.count(Class.forName(treeClassName), null);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			currentParentNode.setRight((int) (rowCount * 2 + 1));
		} else { // reload parent object
			entityService.refresh(currentParentNode);
		}
		// 得到父节点的右边值
		int parentRight = currentParentNode.getRight();
		// 更新大于节点的值
		/*
		 * update table set left=left+2 where left>parentRight update table set
		 * right=right+2 where right>=parentRight
		 */

		String updateLeftHQL = String.format(UPDATE_LEFT_HSQL,treeClassName,2);
		
		hibernateEntityService.bulkUpdate(updateLeftHQL, new Object[] { parentRight});

		String updateRightHQL = String.format(UPDATE_RIGHT_HSQL,treeClassName,2);
		hibernateEntityService.bulkUpdate(updateRightHQL, new Object[] { parentRight - 1});

		// 更新当前节点的值
		node.setLeft(parentRight);
		node.setRight(parentRight + 1);
		node.setDepth(currentParentNode.getDepth() + 1);

		// 保存当前的实体
		hibernateEntityService.saveOrUpdate(node);
//		if (parentNode.getId() != null) {
//			entityService.refresh(parentNode);
//		}
	}

	

	/**
	 * @see corner.tree.services.ouriba.eweb.services.tree.TreeService#getTree(java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<? extends TreeAdapter> getTree(
			final Class<? extends TreeAdapter> clazz) {
		return hibernateEntityService.executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				criteria.addOrder(Order.asc(TreeAdapter.LEFT_PRO_NAME));

				return criteria.list();
			}
			
		});
	}

	/**
	 * @see corner.tree.services.ouriba.eweb.services.tree.TreeService#moveNode(corner.tree.base.ouriba.eweb.entities.base.TreeAdapter, int, java.lang.Class)
	 */
	@Override
	public void moveNode(TreeAdapter node, int n, Class<? extends TreeAdapter> clazz) {
		if (n == 0) {
			return;
		}

		MoveTreeNodeProcessor processor=createSubProcessor(node,n);
		
		processor.execute(node,this.entityService,this.hibernateEntityService,n,clazz);
		
		
	}

	private MoveTreeNodeProcessor createSubProcessor(TreeAdapter node, int n) {
		if(n>0){
			return new MoveUpProcessor();
		}else{
			return new MoveDownProcessor();
		}
		
	}



	private String getTreeClassName(TreeAdapter node, Class<? extends TreeAdapter> clazz) {
		if(clazz!=null){
			return clazz.getName();
		}else{
			return entityService.getEntityClass(node).getName();
		}
		
	}

	/**
	 * @see corner.tree.services.ouriba.eweb.services.tree.TreeService#deleteTreeNode(corner.tree.base.ouriba.eweb.entities.base.TreeAdapter, java.lang.Class)
	 */
	@Override
	public void deleteTreeNode(TreeAdapter node, Class<? extends TreeAdapter> clazz) {
		String treeClassName =getTreeClassName(node,clazz);
		int left = node.getLeft();
		int right = node.getRight();

		int width = left-right - 1;

		// 删除该节点，以及节点下面所属的字节点
		hibernateEntityService.bulkUpdate(String.format(DELETE_NODE_HSQL,treeClassName),
				new Object[] { left, right});

		// 更新其他节点的左右值
		hibernateEntityService.bulkUpdate(String.format(UPDATE_LEFT_HSQL,treeClassName,width), new Object[] {right});
		hibernateEntityService.bulkUpdate(String.format(UPDATE_RIGHT_HSQL,treeClassName,width), new Object[] {right});

	}
}
