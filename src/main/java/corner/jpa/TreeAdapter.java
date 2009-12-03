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
package corner.jpa;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * 抽象树的属性接口
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 2047 $
 * @since 0.0.1
 */
public interface TreeAdapter {

	/**
	 * @return the left
	 */
	@Column(name = "TREE_LEFT")
	public abstract int getLeft();

	/**
	 * @param left the left to set
	 */
	public abstract void setLeft(int left);

	/**
	 * @return the right
	 */
	@Column(name = "TREE_RIGHT")
	public abstract int getRight();

	/**
	 * @param right the right to set
	 */
	public abstract void setRight(int right);

	/**
	 * @return the depth
	 */
	@Column(name = "TREE_DEPTH")
	public abstract int getDepth();

	/**
	 * @param depth the depth to set
	 */
	public abstract void setDepth(int depth);
	
	@Transient
	public String getIndentStr();
	 /* bean properties begin */
    public static final String LEFT_PRO_NAME="left";
    public static final String RIGHT_PRO_NAME="right";
    public static final String DEPTH_PRO_NAME="depth";
    /* bean properties end */
}