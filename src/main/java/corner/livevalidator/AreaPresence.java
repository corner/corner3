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
package corner.livevalidator;


import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.validator.AbstractValidator;

import corner.area.AreaModel;
import corner.area.AreaSelectField;


/**
 *  用于地区选择的校验
 * @author dong
 * @version $Revision: 2089 $
 * @since 0.0.1
 */
public class AreaPresence extends AbstractValidator<Void, Object> {

	private ValidatorRenderSupport delegate;

	public AreaPresence(ValidatorRenderSupport delegate) {
		super(null, Object.class, "areapresence");
		this.delegate = delegate;
	}

	/**
	 * @see org.apache.tapestry5.Validator#render(org.apache.tapestry5.Field,
	 *      java.lang.Object, org.apache.tapestry5.ioc.MessageFormatter,
	 *      org.apache.tapestry5.MarkupWriter,
	 *      org.apache.tapestry5.services.FormSupport)
	 */
	public void render(Field field, Void constraintValue, MessageFormatter formatter, MarkupWriter writer, FormSupport formSupport) {
		delegate.renderAssetFiles();
		AreaSelectField areaField = (AreaSelectField) field;
		String pid = areaField.getProvinceField().getClientId();
		String cid = areaField.getCityField().getClientId();
		String tid = areaField.getTownField().getClientId();
		if (areaField.getProvinceRequired()) {
    		JSONObject options = new JSONObject();
    		options.put("failureMessage", formatter.format("省"));
			delegate.addValidatorScript(pid, "presence", options);
		}
		if (areaField.getCityRequired()) {
    		JSONObject options = new JSONObject();
    		options.put("failureMessage", formatter.format("市"));
			delegate.addValidatorScript(cid, "presence", options);
		}
		if (areaField.getTownRequired()) {
    		JSONObject options = new JSONObject();
    		options.put("failureMessage", formatter.format("县"));
			delegate.addValidatorScript(tid, "presence", options);
		}
	}

	public void validate(Field field, Void constraintValue, MessageFormatter formatter, Object value) throws ValidationException {
		AreaSelectField areaField = (AreaSelectField) field;
		boolean required = areaField.getProvinceRequired() || areaField.getCityRequired() || areaField.getTownRequired();
		if (!required) {
			return;
		}
		if (value == null)
			throw new ValidationException(formatter.format("地区"));
		AreaModel model = (AreaModel) value;
		// 分别检查AreaModel各项是否满足要求
		if (areaField.getProvinceRequired()) {
			if (model.getProvince() == null) {
				throw new ValidationException(formatter.format("省"));
			}
		}

		if (areaField.getCityRequired()) {
			if (model.getCity() == null) {
				throw new ValidationException(formatter.format("市"));
			}
		}

		if (areaField.getTownRequired()) {
			if (model.getTown() == null) {
				throw new ValidationException(formatter.format("县"));
			}
		}
	}
}
