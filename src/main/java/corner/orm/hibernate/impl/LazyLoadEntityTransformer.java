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
package corner.orm.hibernate.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

/**
 * @author <a href="mailto:xiafei114@gmail.com">xiafei</a>
 * @version $Revision$
 * @since 3.1
 */
public class LazyLoadEntityTransformer implements ResultTransformer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6259516575635282447L;
	private Session session;
	private Class persistClass;

	public LazyLoadEntityTransformer(Session session, Class persistClass) {
		this.session = session;
		this.persistClass = persistClass;
	}

	@Override
	public List transformList(List collection) {
		return collection;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		return session.get(persistClass, (Serializable) tuple[0]);
	}
}
