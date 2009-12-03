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
package corner.tree;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import corner.jpa.TreeAdapter;

/**
 * 向上移动
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 2956 $
 * @since 0.0.1 
 */
class MoveUpProcessor extends AbstractMoveTreeNodeProcessor implements
		MoveTreeNodeProcessor {
	private int moveBlockLeftStart = 0;

	private int moveBlockLeftEnd = Integer.MAX_VALUE;

	private int offset = 0;

	@Override
	protected void appendQueryReplaceNodeCriteria(Criteria criteria) {
		criteria.add(Restrictions.lt(TreeAdapter.LEFT_PRO_NAME, getCurrentNode().getLeft())).add(
				Restrictions.gt(TreeAdapter.LEFT_PRO_NAME, getParentNode().getLeft()));
		criteria.addOrder(Order.desc(TreeAdapter.LEFT_PRO_NAME));

	}

	@Override
	protected void fetchMoveBlockInfo(List<? extends TreeAdapter> list) {
		if (list.size() == 0) { // 未找到了移动的位置，说明已经溢出
			offset = getCurrentNode().getLeft()
					- (getParentNode().getLeft() + 1);
			moveBlockLeftStart = getParentNode().getLeft() + 1;
			moveBlockLeftEnd = getCurrentNode().getLeft() - 1;
		} else {// 找到了移动的位置
			TreeAdapter replaceNode = list.get(0);
			offset = getCurrentNode().getLeft() - replaceNode.getLeft();
			moveBlockLeftStart = replaceNode.getLeft();
			moveBlockLeftEnd = getCurrentNode().getLeft() - 1;
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
		return 0 - this.offset;
	}

	@Override
	protected int getUpdateWidth() {
		return getCurrentNode().getRight() - getCurrentNode().getLeft() + 1;
	}

	@Override
	public TreeAdapter constructRootNode() {
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
		rootNode.setRight(Integer.MAX_VALUE);

		return rootNode;
	}

}
