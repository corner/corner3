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
package corner;

/**
 * 一些常用量
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3041 $
 * @since 0.0.1
 */
public class LichenConstants {
	public static final String REMOTE_SCOPE = "remote";
	public static final String REMOTE_SERVER_URL = "remote-service-url";
	/**
	 * 是否禁用远程调用
	 */
	public static final String ENABLE_REMOTE_CALL = "enable-remote-call";
	/**
	 * 默认的调用caller
	 */
	public static final String DEFAULT_CALLER = "default-remote-caller";
	/** 是否支持html模板 * */
	public static final String ENABLE_HTML_TEMPLATE = "enable-html-template";
	/** HTML模板文件的后缀 * */
	public static final String HTML_TEMPLATE_EXTENSION = "html";
	/** 是否允许访问HTML**/
	public static final String ENABLE_HTML_ACCESS= "enable-html-access";

	/** TableView组件中每页显示的记录条数 * */
	public static final String COMPOENT_TABLEVIEW_ROWS_PERPAGE = "corner.component.tableview.rowsPerPage";
	/** 默认的资源引用类型* */
	public static final String ASSET_TYPE = "ouriba.asset.type";
}
