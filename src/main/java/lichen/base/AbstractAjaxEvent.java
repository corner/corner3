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
package lichen.base;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * 使用T5COnEvent.js进行的Ajax请求抽象类
 * @author dong
 * @version $Revision: 3683 $
 * @since 0.0.2
 */
@IncludeJavaScriptLibrary(value = { "${ouriba.asset.type}:js/T5COnEvents.js" })
public abstract class AbstractAjaxEvent {
	/** Ajax请求完成之后的触发的客户端调用函数名称 * */
	@Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
	private String onCompleteCallback;

	/** 客户端发起Ajax请求之前调用的函数(或者函数名) * */
	@Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
	private String onBeforeRequest;

	@Environmental
	private RenderSupport _pageRenderSupport;

	@Inject
	private ComponentResources _resources;

	/**
	 * 客户端组件的id号
	 * @return
	 * @since 0.0.2
	 */
	public abstract String getClientId();

	/**
	 * 事件的名称
	 * @return 事件的名称
	 * @since 0.0.2
	 */
	public abstract String getEventName();

	/**
	 * 上下文的参数
	 * @return 在响应事件时候的上下文
	 * @since 0.0.2
	 */
	protected abstract Object[] getContext();

	public String getOnCompleteCallback() {
		return onCompleteCallback;
	}

	public String getOnBeforeRequest() {
		return onBeforeRequest;
	}

	public RenderSupport getPageRenderSupport() {
		return _pageRenderSupport;
	}

	public ComponentResources getResources() {
		return _resources;
	}
	
	/**
	 * 增加js输出
	 * @param writer
	 * @since 0.0.2
	 */
	protected void renderJsLibary(MarkupWriter writer) {
		Link link = _resources.createEventLink(this.getEventName()
				.toLowerCase(), this.getContext());
		String id = getClientId();

		String jsString = "new T5COnEvent('%s', '%s','%s', '%s',%s);";
		String callBackString = _resources.isBound("onCompleteCallback") ? onCompleteCallback
				: "";
		String beforeRequest = _resources.isBound("onBeforeRequest") ? onBeforeRequest
				: "''";
		_pageRenderSupport.addScript(jsString, this.getEventName(), id, link
				.toAbsoluteURI(), callBackString, beforeRequest);
	}
}
