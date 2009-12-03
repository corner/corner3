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
package corner.tapestry.components;

import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * 重整AjaxLoader的展示方式
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
@IncludeJavaScriptLibrary({"classpath:/js/BlackCover.js","AjaxLoader.js"})
@IncludeStylesheet("AjaxLoader.css")
public class AjaxLoader {
	@Environmental
	private RenderSupport renderSupport;

	@Inject
	private Messages messages;

	@BeginRender
	void registerAjaxResponders() {
		renderSupport.addScript("new AjaxLoader('%s');", messages
				.get("loading"));
	}
}
