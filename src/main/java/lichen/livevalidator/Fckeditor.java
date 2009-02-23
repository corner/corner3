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

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.validator.AbstractValidator;

/**
 * 定义对Fckeditor的校验,由于FckEditor的工作机制,在T5的校验执行前,对应Form表单元素的值还未设置,
 * 从而会第一次校验的逻辑错误,因为对FckEditor的校验需要单独进行,并且尽量对FckEditor的校验尽量不要与其它的校验器一起使用
 * 
 * 目前提供的校验方式是不能为空,与lv的Presence类似
 * 
 * @author dong
 * @version $Revision: $
 * @since 0.0.1
 */
public class Fckeditor extends AbstractValidator<Void, Object> {

	private ValidatorRenderSupport delegate;

	public Fckeditor(ValidatorRenderSupport delegate) {
		super(null, Object.class, "fckeditor");
		this.delegate = delegate;
	}

	/**
	 * @see org.apache.tapestry5.Validator#render(org.apache.tapestry5.Field,
	 *      java.lang.Object, org.apache.tapestry5.ioc.MessageFormatter,
	 *      org.apache.tapestry5.MarkupWriter,
	 *      org.apache.tapestry5.services.FormSupport)
	 */
	public void render(Field field, Void constraintValue,
			MessageFormatter formatter, MarkupWriter writer,
			FormSupport formSupport) {
		delegate.renderAssetFiles();

		JSONObject options = new JSONObject();
		options.put("failureMessage", buildMessage(formatter, field));
		delegate.addValidatorScript(field.getClientId(), "fckeditor", options);
	}

	private String buildMessage(MessageFormatter formatter, Field field) {
		return formatter.format(field.getLabel());
	}

	public void validate(Field field, Void constraintValue,
			MessageFormatter formatter, Object value)
			throws ValidationException {
		if (value == null || InternalUtils.isBlank(value.toString()))
			throw new ValidationException(buildMessage(formatter, field));

	}

	@Override
	public boolean isRequired() {
		return true;
	}
}
