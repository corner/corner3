/* 
 * Copyright 2009 The Corner Team.
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

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.validator.AbstractValidator;

/**
 * 对两个字段是否相同进行校验
 * 
 * @author Jun Tsai
 * @version $Revision$
 * @since 0.0.1
 */
public class Confirmation extends AbstractValidator<String, String> {

	private ValidatorRenderSupport delegate;

	private final Request request;

	public Confirmation(ValidatorRenderSupport delegate, Request request) {
		super(String.class, String.class, ValidatorConstants.CONFIRMATION);
		this.delegate = delegate;
		this.request = request;
	}

	public void render(Field field, String constraintValue,
			MessageFormatter formatter, MarkupWriter writer,
			FormSupport formSupport) {

		delegate.renderAssetFiles();

		JSONObject options = new JSONObject();
		options.put("match", constraintValue);
		options.put(ValidatorConstants.FAIL_MESSAGE, buildMessage(formatter, field));

		delegate.addValidatorScript(field.getClientId(), ValidatorConstants.CONFIRMATION,	options);

	}

	private String buildMessage(MessageFormatter formatter, Field field) {
		return formatter.format(field.getLabel());
	}

	public void validate(Field field, String constraintValue,
			MessageFormatter formatter, String value)
			throws ValidationException {
		String _confirmValue = this.request.getParameter(constraintValue);
		String _from = null;
		String _to = null;
		if (value != null) {
			_from = value;
			_to = _confirmValue;
		} else if (_confirmValue != null) {
			_from = _confirmValue;
		} else {
			return;
		}
		if (!_from.equals(_to)) {
			throw new ValidationException(buildMessage(formatter, field));
		}
	}
}
