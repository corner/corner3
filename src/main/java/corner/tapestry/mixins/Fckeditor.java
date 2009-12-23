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
package corner.tapestry.mixins;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

import corner.tapestry.fckeditor.FckeditorSymbols;

/**
 * 实现fckeditor一个Mixin.
 * 
 * 使用方法：
 * <code>
 * 	&gt;t:textarea value="testValue" t:mixins="corner/fckeditor"/&lt;
 * </code>
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
@IncludeJavaScriptLibrary( { "${fckeditor.path}/fckeditor.js","FckEditorWrapper.js" })
@SupportsInformalParameters
public class Fckeditor {
	/** 编辑器的宽度 **/
	@Parameter
	private String height;
	
	/** 编辑器的高度 **/
	@Parameter
	private String width;
	
	/** 编辑器的工具条名称**/
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String toolbar;

	@InjectContainer
	private Field field;

	@Inject
	private RenderSupport renderSupport;

	@Inject
	@Path("${"+FckeditorSymbols.FCKEDITOR_JS_PATH+"}")
	private Asset fckeditorPath;
	
	@Inject
	private ComponentResources resources;

	void afterRender() {
		String _height = this.resources.isBound("height")?this.height:"400";
		String _width= this.resources.isBound("width")?this.width:"100%";
		String _toolbar = this.resources.isBound("toolbar")?this.toolbar:"corner";
		renderSupport.addInit("createEditor",
				fckeditorPath.toClientURL() + "/", field.getClientId(),_width,_height,_toolbar);
	}
}
