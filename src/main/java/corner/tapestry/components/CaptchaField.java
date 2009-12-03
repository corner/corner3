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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Heartbeat;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import corner.tapestry.captcha.CaptchaCode;
import corner.tapestry.captcha.CaptchaImage;

/**
 * 提供用于Form中的校验码组件
 * 
 * @author dong
 * @version $Revision: 3029 $
 * @since 0.0.1
 */
public class CaptchaField extends AbstractField {

	/** 客户端的id * */
	private String _clientId;

	/** 校验码 * */
	private String _code;

	/** 输入框的样式名 * */
	@SuppressWarnings("unused")
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	@Property
	private String inputValueClass;

	/** 图片的显示宽度 * */
	@SuppressWarnings("unused")
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	@Property
	private String imgWidth;

	/** 图片的显示高度* */
	@SuppressWarnings("unused")
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	@Property
	private String imgHeight;

	/** 校验码的输入框 * */
	@Component(parameters = { "value=inputValue",
			"validate=presence,liveregexp" })
	private TextField checkCodeField;

	/** 输入的校验码 * */
	@Property
	private String inputValue;

	@Inject
	private Request request;

	@Inject
	private CaptchaCode ccode;

	@Inject
	private CaptchaImage image;

	@Inject
	@Property
	private Block codeBlock;

	@Inject
	private ComponentResources resources;

	@Environmental
	private ValidationTracker tracker;

	@Environmental
	private Heartbeat heartbeat;

	/**
	 * 生成验证码
	 * 
	 * @return 验证码
	 */
	public String getCode() {
		return genCCode();
	}

	/**
	 * 生成校验码的图片URL
	 * 
	 * @return 图片的URL
	 */
	public Link getImageLink() {
		return resources.createEventLink("renderImage", this.getCode());
	}

	/**
	 * 取得校验码的表单ID号,对应tml中的hidden元素
	 * 
	 * @return
	 */
	public String getBlockClientId() {
		if (_clientId != null) {
			return _clientId;
		} else {
			return super.getClientId();
		}
	}

	/**
	 * 处理刷新的请求
	 * 
	 * @param cid
	 *            从页面上传来的表单元素ID,用于在codeBlock中重新生成表单hidden项
	 * @return
	 */
	public Object onActionFromRefresh(String cid) {
		this._clientId = cid;
		return codeBlock;
	}

	/**
	 * 响应图片生成的请求
	 * 
	 * @param code
	 * @return
	 */
	public Object onRenderImage(String code) {
		String[] text = ccode.getTextChallengeCode(code);
		byte[] data = image.converString2Image(text[0]);
		return new ByteStreamResponse(image.getMimeType(), data);
	}

	/**
	 * 校验输入的明文校验和加密的校验码是否匹配,过期时间是60秒
	 * 
	 * @param code
	 *            用户输入的明文校验码
	 * @param encryptCode
	 *            加密的校验码
	 * @return true,code和encryptCode匹配,并且未过期;false,code和encryptCode不匹配,或者已经过期
	 */
	public boolean check(String code, String encryptCode) {
		return this.ccode.checkChallengerCode(code, encryptCode, 1000 * 60);
	}

	/**
	 * 
	 * @see org.apache.tapestry5.corelib.base.AbstractField#processSubmission(java.lang.String)
	 */
	@Override
	protected void processSubmission(final String elementName) {
		Runnable command = new Runnable() {
			public void run() {
				final String _encryptCode = request.getParameter(elementName);
				final String _inputValue = inputValue;
				inputValue = "";
				if (_encryptCode == null) {
					tracker.recordError(CaptchaField.this.checkCodeField, resources
							.getMessages().get("challengecode-noencrpty"));
					return;
				}
				if (_inputValue == null) {
					tracker.recordError(CaptchaField.this.checkCodeField, resources
							.getMessages().get("challengecode-noinput"));
					return;
				}
				if (!check(_inputValue, _encryptCode)) {
					tracker.recordError(CaptchaField.this.checkCodeField, resources
							.getMessages().get("challengecode-nomatch"));
				}
				// 验证完毕之后,清空checkCodeField
				tracker.recordInput(checkCodeField, "");
			}
		};
		heartbeat.defer(command);
	}

	/**
	 * 生成本次请求的校验码,在每次请求的过程中,只在第一次生成校验码供以后使用
	 * 
	 * @return
	 */
	private String genCCode() {
		if (this._code == null) {
			this._code = ccode.getChallengeCode();
		}
		return this._code;
	}

	/**
	 * 简单的对StreamResponse的实现,用于返回流格式的数据
	 * 
	 * @author dong
	 * @version $Revision: 3029 $
	 * @since 0.0.1
	 */
	private final class ByteStreamResponse implements StreamResponse {
		private final String contentType;
		private final byte[] data;

		public ByteStreamResponse(String contentType, byte[] data) {
			this.contentType = contentType;
			this.data = data;
		}

		public String getContentType() {
			return this.contentType;
		}

		public InputStream getStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		public void prepareResponse(Response arg0) {
		}
	}
}
