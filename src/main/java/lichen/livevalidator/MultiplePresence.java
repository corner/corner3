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

import lichen.services.FieldRenderTrackerSupport;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Heartbeat;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.validator.AbstractValidator;

/**
 * 校验多个字段的多项选择,要求至少选择一个
 * 
 * @author dong
 * @version $Revision: Id $
 * @since 0.0.1
 */
public class MultiplePresence extends AbstractValidator<String, String> {
	private final ValidatorRenderSupport delegate;
	private final Request request;
	private final Environment environment;

	public MultiplePresence(ValidatorRenderSupport delegate, Request request,
			Environment environment) {
		super(String.class, String.class, "multiplechoice");
		this.delegate = delegate;
		this.request = request;
		this.environment = environment;
	}

	public void render(final Field field, final String constraintValue,
			final MessageFormatter formatter, final MarkupWriter writer,
			final FormSupport formSupport) {
		Heartbeat heartbeat = this.environment.peekRequired(Heartbeat.class);
		Runnable command = new Runnable() {
			@Override
			public void run() {
				delegate.renderAssetFiles();
				JSONObject options = new JSONObject();
				String[] names = constraintValue.split(";");
				FieldRenderTrackerSupport fieldTracker = environment
						.peek(FieldRenderTrackerSupport.class);
				if (fieldTracker != null) {
					int i = 0;
					String[] _names = new String[names.length];
					for (String name : names) {
						String _clientId = fieldTracker.getFiledNameId(name);
						if (_clientId == null) {
							_clientId = name;
						}
						_names[i++] = _clientId;
					}
					options.put("args", new JSONArray(Object[].class.cast(_names)));
				} else {
					options.put("args", new JSONArray(Object[].class.cast(constraintValue
							.split(";"))));
				}
				options.put("failureMessage", buildMessage(formatter, field));
				delegate.addValidatorScript(field.getClientId(),
						"mutiplePresence", options);
			}
		};
		heartbeat.defer(command);

	}

	private String buildMessage(MessageFormatter formatter, Field field) {
		return formatter.format(field.getLabel());
	}

	public void validate(Field field, String constraintValue,
			MessageFormatter formatter, String value)
			throws ValidationException {
		String[] names = constraintValue.split(";");
		boolean passed = false;
		do {
			if (InternalUtils.isNonBlank(value)) {
				passed = true;
				break;
			}
			for (String name : names) {
				if (InternalUtils.isNonBlank(this.request.getParameter(name))) {
					passed = true;
					break;
				}
			}
		} while (false);
		if (!passed) {
			throw new ValidationException(buildMessage(formatter, field));
		}
	}

	@Override
	public boolean isRequired() {
		return true;
	}
}
