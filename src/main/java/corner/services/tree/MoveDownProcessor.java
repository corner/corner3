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
package corner.services.tree;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import corner.entities.base.TreeAdapter;

/**
 * 向下移动的处理程序
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 2956 $
 * @since 0.0.1 
 */
class MoveDownProcessor extends AbstractMoveTreeNodeProcessor
		implements MoveTreeNodeProcessor {

	private int moveBlockLeftStart = 0;

	private int moveBlockLeftEnd = Integer.MAX_VALUE;

	private int offset = 0;

	@Override
	protected void appendQueryReplaceNodeCriteria(Criteria criteria) {
		criteria.add(Restrictions.gt(TreeAdapter.RIGHT_PRO_NAME, getCurrentNode().getRight()))
				.add(Restrictions.lt(TreeAdapter.RIGHT_PRO_NAME, getParentNode().getRight()));
		criteria.addOrder(Order.asc(TreeAdapter.LEFT_PRO_NAME));

	}

	@Override
	protected void fetchMoveBlockInfo(List<? extends TreeAdapter> list) {
		if (list.size() == 0) { // 未找到了移动的位置，说明已经溢出
			offset = (getParentNode().getRight() - 1)
					- getCurrentNode().getRight();
			moveBlockLeftStart = getCurrentNode().getRight() + 1;
			moveBlockLeftEnd = getParentNode().getRight();
		} else {// 找到了移动的位置
			TreeAdapter replaceNode = list.get(0);
			offset = replaceNode.getRight() - getCurrentNode().getRight();
			moveBlockLeftStart = getCurrentNode().getRight() + 1;
			moveBlockLeftEnd = replaceNode.getRight();
		}

	}

	@Override
	protected int getMoveBlockLeftEnd() {
		return this.moveBlockLeftEnd;
	}

	@Override
	protected int getMoveBlockLeftStart() {
		return this.moveBlockLeftStart;
	}

	@Override
	protected int getOffset() {
		return this.offset;
	}

	@Override
	protected int getUpdateWidth() {
		return getCurrentNode().getLeft() - getCurrentNode().getRight() - 1;
	}

	@Override
	protected TreeAdapter constructRootNode() {
		TreeAdapter rootNode;
		try {
			rootNode = (TreeAdapter) BeanUtils
					.instantiateClass(Class.forName(getTreeClassName()));
		} catch (BeanInstantiationException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		rootNode.setLeft(0);

		long rowCount = (Long) getEntityService().find("select count(*) from " + getTreeClassName()).get(0);
		rootNode.setRight((int) (rowCount * 2 + 1));

		return rootNode;
	}
}
