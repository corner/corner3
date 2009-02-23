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
package lichen.mixins;

import lichen.services.FieldRenderTrackerSupport;
import lichen.services.impl.FieldRenderTrackerSupportImpl;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.FormSupport;

/**
 * 跟踪Field的Render过程,记录在FieldRenderTrackerSupport中
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class FieldRenderTracker {

	@InjectContainer
	private Field field;
	@Inject
	private Environment enviroment;

	void afterRender() {
		FieldRenderTrackerSupport trackerSupport = enviroment
				.peek(FieldRenderTrackerSupport.class);
		FormSupport formSupport = enviroment.peek(FormSupport.class);
		if (formSupport == null) {
			// form 已经结束,或者还未开始,清空FieldRenderTrackerSupport后返回
			if (trackerSupport != null) {
				enviroment.pop(FieldRenderTrackerSupport.class);
			}
			return;
		}
		if (trackerSupport != null) {
			// 新的form开始了,清空FieldRenderTrackerSupport后返回
			if (!trackerSupport.getFormId().equals(formSupport.getClientId())) {
				enviroment.pop(FieldRenderTrackerSupport.class);
				trackerSupport = null;
			}
		}

		if (trackerSupport == null) {
			trackerSupport = new FieldRenderTrackerSupportImpl(formSupport
					.getClientId());
			enviroment.push(FieldRenderTrackerSupport.class, trackerSupport);
		}
		String id = field.getClientId();
		String name = field.getControlName();
		trackerSupport.trackFiledNameId(name, id);
	}
}
