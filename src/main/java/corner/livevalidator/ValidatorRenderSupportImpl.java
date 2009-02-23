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
package corner.livevalidator;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

/**
 * 对校验进行的delegate类。
 * 
 * 用来加载JavaScript,以及CSS文件
 * 
 * @author Jun Tsai
 * @version $Revision: 3355 $
 * @since 0.0.1
 */
public class ValidatorRenderSupportImpl implements ValidatorRenderSupport {
	protected RenderSupport renderSupport;
	protected Asset validatorCss;
	private final String lv4t5JavaScript;

	public ValidatorRenderSupportImpl(RenderSupport renderSupport,
			@Path("/corner/validator/validator.css")
			Asset validatorCss,@Inject @Symbol(ValidationModule.LV4T5_JS_VERSION) String lv4t5JavaScript) {
		this.renderSupport = renderSupport;
		this.validatorCss = validatorCss;
		this.lv4t5JavaScript = lv4t5JavaScript;
	}

	public void renderAssetFiles() {
		renderSupport.addStylesheetLink(validatorCss, null);
		renderSupport
				.addClasspathScriptLink("${tapestry.scriptaculous}/prototype.js");
		renderSupport
				.addClasspathScriptLink("/corner/validator/livevalidation_prototype.compressed.js");
		renderSupport.addClasspathScriptLink("/corner/validator/"+lv4t5JavaScript);

	}

	public void addValidatorScript(String fieldId, String validator,
			JSONObject options) {

		JSONArray parameterList = new JSONArray();
		parameterList.put(fieldId);
		parameterList.put(validator);
		parameterList.put(options);
		renderSupport.addInit("liveValidation", parameterList);
	}
}
