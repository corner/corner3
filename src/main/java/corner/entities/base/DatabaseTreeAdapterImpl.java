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
package corner.entities.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 抽象的树的实现
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 5183 $
 * @since 0.0.1
 */
@MappedSuperclass
public class DatabaseTreeAdapterImpl extends BaseModel implements TreeAdapter {
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
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#getLeft()
	 */
	@Column(name="TREE_LEFT")
	public int getLeft() {
		return left;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#setLeft(int)
	 */
	public void setLeft(int left) {
		this.left = left;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#getRight()
	 */
	@Column(name="TREE_RIGHT")
	public int getRight() {
		return right;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#setRight(int)
	 */
	public void setRight(int right) {
		this.right = right;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#getDepth()
	 */
	@Column(name="TREE_DEPTH")
	public int getDepth() {
		return depth;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#setDepth(int)
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}
	@Transient
	public String getIndentStr(){
		StringBuffer sb = new StringBuffer();
		int i = this.getDepth();
		while (i > 1) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			i--;
		}
		return sb.toString();
	}
    /* bean properties begin */
    public static final String LEFT_PRO_NAME="left";
    public static final String RIGHT_PRO_NAME="right";
    public static final String DEPTH_PRO_NAME="depth";
    /* bean properties end */

}
