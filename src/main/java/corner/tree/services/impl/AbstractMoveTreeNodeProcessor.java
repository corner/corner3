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
package corner.tree.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.HibernateEntityService;
import corner.orm.services.EntityService;
import corner.tree.base.TreeAdapter;
import corner.utils.EntityUtil;

/**
 * 抽象对树的移动进行的处理
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1 
 */
abstract class AbstractMoveTreeNodeProcessor {
	//隔离元素的HQL
	private final static String ISOLATE_NODE_HQL = "update %s  t set t."+TreeAdapter.LEFT_PRO_NAME+"=0 - t."+TreeAdapter.LEFT_PRO_NAME+",t."+TreeAdapter.RIGHT_PRO_NAME+"=0 - t."+TreeAdapter.RIGHT_PRO_NAME+"  where (t."+TreeAdapter.LEFT_PRO_NAME+" between ? and ?)";
	//更新受影响的节点
	private final static String UPDATE_NODES_AFFECTED = "update %1$s  t set t."+TreeAdapter.LEFT_PRO_NAME+"=t."+TreeAdapter.LEFT_PRO_NAME+"+%2$d,t."+TreeAdapter.RIGHT_PRO_NAME+"=t."+TreeAdapter.RIGHT_PRO_NAME+"+%3$d  where (t."+TreeAdapter.LEFT_PRO_NAME+" between ? and ?)";
	//更新当前的NODE
	private final static String UPDATE_CURRENT_NODE_HQL = "update %1$s  t set t."+TreeAdapter.LEFT_PRO_NAME+"=%2$d-t."+TreeAdapter.LEFT_PRO_NAME+" ,t."+TreeAdapter.RIGHT_PRO_NAME+"=%2$d-t."+TreeAdapter.RIGHT_PRO_NAME+"  where t."+TreeAdapter.LEFT_PRO_NAME+" <?";

	// 父节点
	private TreeAdapter parentNode;

	// 当前操作的节点
	private TreeAdapter node;
	
	private EntityService service;

	// 对应的节点类的名字
	private String treeClassName;
	private HibernateEntityService hibernateEntityService;

	/**
	 * 构造跟节点对象
	 * 
	 * @return 根节点对象
	 */
	protected abstract TreeAdapter constructRootNode();

	/**
	 * 得到当前的节点
	 * 
	 * @return 当前的节点
	 */
	public TreeAdapter getCurrentNode() {
		return this.node;
	}

	/**
	 * 得到操作树的类名
	 * 
	 * @return 树的类名
	 */
	protected String getTreeClassName() {
		return this.treeClassName;
	}
	protected Class getTreeClass() {
		try {
			return Class.forName(treeClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected EntityService getEntityService(){
		return this.service;
	}

	// 得到移动块的左边的起始位置,注意的是：查询的时候是 >=
	protected abstract int getMoveBlockLeftStart();

	// 得到移动块的左边的结束位置,注意的是：查询的时候是 <=
	protected abstract int getMoveBlockLeftEnd();

	// 得到当前节点移动的偏移量
	protected abstract int getOffset();

	// 得到受影响块更新的宽度
	protected abstract int getUpdateWidth();

	// 查询移动到的节点条件
	protected abstract void appendQueryReplaceNodeCriteria(
			Criteria criteria);

	// 获取移动模板的位置信息
	protected abstract void fetchMoveBlockInfo(List<? extends TreeAdapter> list);

	@SuppressWarnings("unchecked")
	public void execute(TreeAdapter node, EntityService service,HibernateEntityService hibernateEntityService, int n, Class<? extends TreeAdapter> clazz) {

		this.node = node;
//		this.ht = ht;
		this.service = service;
		this.hibernateEntityService = hibernateEntityService;

		if (clazz != null) {
			treeClassName = clazz.getName();
		} else {
			treeClassName = EntityUtil.getEntityClass(node).getName();
		}
		// 对当前的node刷新
		service.refresh(node);
		// 查到移动到位置的节点
		List list = fetchReplaceNode(Math.abs(n) - 1);

		// 获取移动模块的位置信息
		fetchMoveBlockInfo(list);

		// 先孤立待移动的节点以及子节点
		insulateCurrentNodeAndChildren();

		// 更新影响到的块
		updateNodesAffected();
		// 更新当前的节点
		updateCurrentNode();

		// 刷新影响的节点
		service.refresh(node);

		if (list.size() > 0) {
			hibernateEntityService.evict(list.get(0));
		}

	}

	/**
	 * 抓取将要替代的节点
	 * 
	 * @param n
	 *            移动的绝对距离
	 * @return 抓取的对象列表
	 */
	private List<?> fetchReplaceNode(final int n) {
		return this.hibernateEntityService.executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(EntityUtil
						.getEntityClass(node));
				appendQueryReplaceNodeCriteria(criteria);
				criteria.add(Restrictions.eq(TreeAdapter.DEPTH_PRO_NAME, node.getDepth()));
				
				criteria.setFirstResult(n);
				criteria.setMaxResults(1);
				return criteria.list();
			}});

	}

	// 孤立当前节点以及子节点
	private void insulateCurrentNodeAndChildren() {
		hibernateEntityService.bulkUpdate(String.format(ISOLATE_NODE_HQL, treeClassName),
				new Object[] { node.getLeft(), node.getRight()});
	}

	// 更新受影响的节点
	private void updateNodesAffected() {

		String updateNodesAffectedHQL = String.format(UPDATE_NODES_AFFECTED,
				treeClassName, getUpdateWidth(), getUpdateWidth());
		hibernateEntityService.bulkUpdate(updateNodesAffectedHQL, new Object[] {
				getMoveBlockLeftStart(), getMoveBlockLeftEnd()});

	}

	// 更新当前的块
	private void updateCurrentNode() {

		String updateCurrentNodeHQL = String.format(UPDATE_CURRENT_NODE_HQL,
				treeClassName, getOffset());
		hibernateEntityService.bulkUpdate(updateCurrentNodeHQL,
				new Object[] {0});
	}

	/**
	 * 得到父节点
	 * 
	 * @return 父节点对象
	 */
	protected TreeAdapter getParentNode() {
		if (this.parentNode != null) {
			return this.parentNode;
		}
		if (node.getDepth() > 1) {// 不为第一级的节点,则从库中获取
			
			List<?> list = hibernateEntityService.executeFind(new HibernateCallback(){

				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Criteria criteria =session.createCriteria(treeClassName);
					/**
					 * 条件为： 上一级 右边大于当前的右边 左边小于当前的左边 并且深度为当前深度 -1
					 */
					criteria.add(Restrictions.gt(TreeAdapter.RIGHT_PRO_NAME, node.getRight())).add(
							Restrictions.lt(TreeAdapter.LEFT_PRO_NAME, node.getLeft()));
					criteria.addOrder(Order.desc(TreeAdapter.LEFT_PRO_NAME));
					
					criteria.setFirstResult(0);
					criteria.setMaxResults(1);
					return criteria.list();
				}});

			if (list.size() != 1) { //
				throw new RuntimeException("非法请求，通过节点(" + node.getLeft()
						+ "," + node.getRight() + ")得到父节点为空");
			}
			parentNode = (TreeAdapter) list.get(0);
		} else { // 为第一级节点,构造顶极根节点对象

			parentNode = constructRootNode();

		}
		return parentNode;
	}

}