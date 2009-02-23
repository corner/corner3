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
package corner.services.impl;

import java.util.HashMap;
import java.util.Map;

import corner.services.FieldRenderTrackerSupport;


/**
 * FieldRenderTrackerSupport的实现
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class FieldRenderTrackerSupportImpl implements FieldRenderTrackerSupport {
	private final String formId;
	private final Map<String, Object> trackerMap = new HashMap<String, Object>(
			0);

	public FieldRenderTrackerSupportImpl(String formId) {
		this.formId = formId;
	}

	/**
	 * @see corner.services.FieldRenderTrackerSupport#getFiledNameId(java.lang.String)
	 */
	@Override
	public String getFiledNameId(String name) {
		return (String)this.trackerMap.get(name);
	}

	/**
	 * @see corner.services.FieldRenderTrackerSupport#getFormSupport()
	 */
	@Override
	public String getFormId() {
		return this.formId;
	}

	/**
	 * @see corner.services.FieldRenderTrackerSupport#trackFiledNameId(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void trackFiledNameId(String name, String id) {
		this.trackerMap.put(name, id);
	}
}
