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
package corner.tree.services;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import corner.tree.base.TreeAdapter;

/**
 * 用来操作树的服务类
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 718 $
 * @since 0.0.1
 */
public interface TreeService {

	/**
	 * 保存树型结构的一个节点.此方法只是针对新建树节点时候，
	 *  <p>需要注意的是：在调用改方法之前，一定不能先保存了改节点
	 * 
	 * @param node
	 *            待保存的节点
	 * @param parentNode 父节点,如果parentNode为空，则为插入顶极节点
	 * @param clazz 待保存节点的类,目的是区分有继承关系的实体.
	 */
	@CommitAfter
	public abstract void saveTreeChildNode(TreeAdapter node,
			TreeAdapter parentNode, Class<? extends TreeAdapter> clazz);

	/**
	 * 通过给定的类，来得到对应的树结构
	 * 得到完整树
	 * 
	 * @param clazz     对应的类
	 */
	public abstract List<? extends TreeAdapter> getTree(
			Class<? extends TreeAdapter> clazz);

	/**
	 * 同级移动节点.
	 *  <p>
	 *  当n>0的时候，为向上移动，
	 *  当 n<0的时候，为向下移动.
	 * 
	 * @param node
	 *            节点
	 * @param n        移动的位置,正数，则为向上移动，负数，向下移动
	 * @param clazz  给定的查询类 ,如果为空，则通过 node 实例来得到对应的类
	 */
	@CommitAfter
	public abstract void moveNode(TreeAdapter node, int n, Class<? extends TreeAdapter> clazz);

	/**
	 * 删除一个节点
	 * 如果clazz为空，则操作对象为<code>node</code>对应的实体类.
	 * @param node 节点
	 * @param clazz  给定的查询类 ,如果为空，则通过 node 实例来得到对应的类
	 */
	@CommitAfter
	public abstract void deleteTreeNode(TreeAdapter node, Class<? extends TreeAdapter> clazz);

}