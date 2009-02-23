/* 
 * Copyright 2008 The Lichen Team.
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
package lichen.table;

import java.util.Iterator;

import org.apache.tapestry5.Link;

/**
 * query model
 * @author Jun Tsai
 * @version $Revision: 740 $
 * @since 0.0.2
 */
public interface QueryModel {

	public abstract Iterator<?> getCurrentPageRecord();

	public abstract int getRowCount();

	public abstract int getRowsPerPage();

	public abstract int getCurrentPage();

	public abstract Link createActionLink(int pageIndex);


}