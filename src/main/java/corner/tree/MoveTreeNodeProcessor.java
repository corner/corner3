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

import corner.jpa.TreeAdapter;
import corner.orm.hibernate.HibernateEntityService;
import corner.orm.services.EntityService;

/**
 * 对树进行处理的字处理程序
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 718 $
 * @since 0.0.1 
 */
public interface MoveTreeNodeProcessor {

	/**
	 * @param node 需要移动的节点
	 * @param service corner基础服务类
	 * @param n 需要移动的距离,n>0 时候 向上移动，n<0的时候向下移动
	 * @param clazz 给定的查询类，如果给定的为空，则从node中获取对应的类.
	 */
	public void execute(TreeAdapter node,EntityService service,HibernateEntityService hibernateEntityService,int n, Class<? extends TreeAdapter> clazz);

}
