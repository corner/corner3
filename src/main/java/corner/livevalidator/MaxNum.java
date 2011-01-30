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
import org.apache.tapestry5.validator.AbstractValidator;

/**
 * 针对最大值的校验
 * 
 * @author msliu
 * @version $Revision$
 * @since 0.0.1
 */
public class MaxNum extends AbstractValidator<Long, Number> {
	private ValidatorRenderSupport delegate;

	public MaxNum(ValidatorRenderSupport delegate) {
		super(Long.class, Number.class, "maxnum");
		this.delegate = delegate;
	}

	/**
	 * @see org.apache.tapestry5.Validator#validate(org.apache.tapestry5.Field, java.lang.Object, org.apache.tapestry5.ioc.MessageFormatter, java.lang.Object)
	 */
	@Override
	public void validate(Field field, Long constraintValue,
			MessageFormatter formatter, Number value)
			throws ValidationException {
		if (value.longValue() > constraintValue)
			throw new ValidationException(buildMessage(formatter, field,
					constraintValue));
	}

	private String buildMessage(MessageFormatter formatter, Field field,
			Long constraintValue) {
		return formatter.format(constraintValue, field.getLabel());
	}

	/**
	 * @see org.apache.tapestry5.Validator#render(org.apache.tapestry5.Field, java.lang.Object, org.apache.tapestry5.ioc.MessageFormatter, org.apache.tapestry5.MarkupWriter, org.apache.tapestry5.services.FormSupport)
	 */
	@Override
	public void render(Field field, Long constraintValue,
			MessageFormatter formatter, MarkupWriter writer,
			FormSupport formSupport) {
		
		delegate.renderAssetFiles();

		JSONObject options = new JSONObject();
		options.put("tooHighMessage", buildMessage(formatter, field,constraintValue));
		options.put("maximum", constraintValue);
		delegate.addValidatorScript(field.getClientId(), "Numericality", options);

	}

}
