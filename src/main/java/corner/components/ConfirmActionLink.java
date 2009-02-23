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
package corner.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * 能展示提示信息的一个组件
 * @author jcai
 * @version $Revision: 854 $
 * @since 0.0.1
 */
@IncludeJavaScriptLibrary("ConfirmActionLink.js")
public class ConfirmActionLink extends ActionLink{

	 @Inject
	 private RenderSupport renderSupport;
	 
	 /** 确认时候的消息提示 **/
	 @Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	 private String msg;
	 
	 @BeginRender
	 void writeConfirmJavaScript(){
		 this.renderSupport.addScript("confirmActionLink('%s','%s');", this.getClientId(),this.msg);
	 }
}
