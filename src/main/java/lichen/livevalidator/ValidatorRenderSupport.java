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
package lichen.livevalidator;

import org.apache.tapestry5.json.JSONObject;

/**
 * 对校验进行的delegate类。
 * 
 * 用来加载JavaScript,以及CSS文件
 * @author Jun Tsai
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public interface ValidatorRenderSupport {

	/**
	 * 填加一些资源文件
	 */
	public void renderAssetFiles();

	/**
	 * 增加校验使用的JavaScript脚本.
	 * @param fieldId 待校验的HtmlElement
	 * @param validator 校验器
	 * @param arguments 格式化使用的参数
	 */
	public void addValidatorScript(String fieldId,String validator, JSONObject options);
	

}