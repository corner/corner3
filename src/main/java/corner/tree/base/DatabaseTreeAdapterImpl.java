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
package corner.tree.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import corner.orm.base.BaseEntity;

/**
 * 抽象的树的实现
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
@MappedSuperclass
public class DatabaseTreeAdapterImpl extends BaseEntity implements TreeAdapter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1377094227176045779L;
	/**
	 * 左边值
	 */
	private int left;
	/**
	 * 右边值
	 */
	private int right;
	/**
	 * 深度
	 */
	private int depth;

	/**
	 * @see corner.tree.base.ouriba.eweb.entities.base.TreeAdapter#getLeft()
	 */
	@Override
	@Column(name = "TREE_LEFT")
	public int getLeft() {
		return left;
	}

	/**
	 * @see corner.tree.base.ouriba.eweb.entities.base.TreeAdapter#setLeft(int)
	 */
	@Override
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * @see corner.tree.base.ouriba.eweb.entities.base.TreeAdapter#getRight()
	 */
	@Override
	@Column(name = "TREE_RIGHT")
	public int getRight() {
		return right;
	}

	/**
	 * @see corner.tree.base.ouriba.eweb.entities.base.TreeAdapter#setRight(int)
	 */
	@Override
	public void setRight(int right) {
		this.right = right;
	}

	/**
	 * @see corner.tree.base.ouriba.eweb.entities.base.TreeAdapter#getDepth()
	 */
	@Override
	@Column(name = "TREE_DEPTH")
	public int getDepth() {
		return depth;
	}

	/**
	 * @see corner.tree.base.ouriba.eweb.entities.base.TreeAdapter#setDepth(int)
	 */
	@Override
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @see corner.tree.base.TreeAdapter#getIndentStr()
	 */
	@Override
	@Transient
	public String getIndentStr() {
		StringBuffer sb = new StringBuffer();
		int i = this.getDepth();
		while (i > 1) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			i--;
		}
		return sb.toString();
	}

	/* bean properties begin */
	public static final String LEFT_PRO_NAME = "left";
	public static final String RIGHT_PRO_NAME = "right";
	public static final String DEPTH_PRO_NAME = "depth";
	/* bean properties end */

}
