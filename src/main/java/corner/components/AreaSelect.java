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

import java.util.LinkedList;
import java.util.List;


import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.FieldValidationSupport;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.Mixins;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.FieldValidatorDefaultSource;
import org.apache.tapestry5.services.Heartbeat;

import corner.area.AreaModel;
import corner.area.AreaModelCallback;
import corner.area.AreaModelImpl;
import corner.area.AreaModelItem;
import corner.area.AreaSelectField;


/**
 * 地区选择的组件
 * 目前可用的validate属性为areavalidate,使用其它的validate操作会出现js错误
 * 
 * @author dong
 * @version $Revision: 3998 $
 * @since 0.0.1
 */
@IncludeJavaScriptLibrary(value = { "${ouriba.asset.type}:js/AreaSelect.js" })
@SupportsInformalParameters
public class AreaSelect extends AbstractField implements AreaSelectField{
	/* 组件参数定义 开始 */
	@SuppressWarnings("unused")
	@Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String _clientId;

	@Parameter()
	@Property
	@SuppressWarnings("unused")
	private String _class;

	@SuppressWarnings("unused")
	@Parameter(required = false)
	@Property
	private AreaModel _value;

	@Parameter(required = true)
	private AreaModelCallback callback;
	
	@Parameter
	private boolean provinceRequired;
	@Parameter
	private boolean cityRequired;
	@Parameter
	private boolean townRequired;
	/* 组件参数定义 结束 */

	/* 内部组件定义 开始 */
	@SuppressWarnings("unused")
	@Component(parameters = { "value=province", "model=provinceModel", "event=change", "onCompleteCallback=onAreaChanged", "blankLabel=请选择" })
	@Mixins("corner/OnAjaxEvent")
	private Select provinceField;
	@SuppressWarnings("unused")
	@Component(parameters = { "value=city", "model=cityModel", "event=change", "onCompleteCallback=onAreaChanged", "blankLabel=请选择" })
	@Mixins("corner/OnAjaxEvent")
	private Select cityField;
	@SuppressWarnings("unused")
	@Component(parameters = { "value=town", "model=townModel", "blankLabel=请选择" })
	private Select townField;
	/* 内部组件定义 结束 */

	@Inject
	private RenderSupport _pageRenderSupport;
	@Inject
	private FieldValidationSupport fieldValidationSupport;
	@Inject
	private ComponentResources resources;
	@Environmental
	private Heartbeat heartbeat;
	@Environmental
	private ValidationTracker tracker;

	@Parameter(defaultPrefix = BindingConstants.VALIDATE)
	@SuppressWarnings("unchecked")
	private FieldValidator<Object> validate;

	@Property
	private String province;
	@Property
	private String city;
	@Property
	private String town;

    @Inject
    private ComponentDefaultProvider defaultProvider;
    /**
     * Computes a default value for the "validate" parameter using {@link FieldValidatorDefaultSource}.
     */
    Binding defaultValidate()
    {
        return defaultProvider.defaultValidatorBinding("value", resources);
    }

	@SetupRender
	@SuppressWarnings("unused")
	void setupRedner() {
		if (this._value != null) {
			this.province = this._value.getProvince() != null ? _value.getProvince().getCode() : null;
			this.city = this._value.getCity() != null ? _value.getCity().getCode() : null;
			this.town = this._value.getTown() != null ? _value.getTown().getCode() : null;
		}
	}

	@Override
	public boolean isRequired() {
		return validate.isRequired();
	}

	@Override
	protected void processSubmission(String elementName) {
		Runnable command = new Runnable() {
			public void run() {
				try {
					AreaModelItem _province = null;
					AreaModelItem _city = null;
					AreaModelItem _town = null;
					if (province != null && province.length() > 0) {
						_province = callback.getAreaModelItemByCode(province);
					}
					if (city != null && city.length() > 0) {
						_city = callback.getAreaModelItemByCode(city);
					}
					if (town != null && town.length() > 0) {
						_town = callback.getAreaModelItemByCode(town);
					}
					AreaModel areaModel = null;
					if (_province != null || _city != null || _town != null) {
						areaModel = new AreaModelImpl(_province, _city, _town);
					}
					fieldValidationSupport.validate(areaModel, resources, validate);
					_value = areaModel;
				} catch (ValidationException ex) {
					tracker.recordError(AreaSelect.this, ex.getMessage());
				}
			}
		};
		heartbeat.defer(command);
	}


	public void afterRender(MarkupWriter writer) {
		if (this.validate != null) {
			validate.render(writer);
		}
		_pageRenderSupport.addScript("new AreaSelect('%s', '%s', '%s');", this.provinceField.getClientId(), this.cityField.getClientId(), this.townField.getClientId());
	}

	public SelectModel getProvinceModel() {
		List<AreaModelItem> options = callback.getProvince();
		return genSelectModel(options);
	}

	public SelectModel getCityModel() {
		if (this.province != null) {
			List<AreaModelItem> options = callback.getCities(this.province);
			return genSelectModel(options);
		} else {
			return new SelectModelImpl(null, null);
		}
	}

	public SelectModel getTownModel() {
		if (this.city != null) {
			List<AreaModelItem> options = callback.getTowns(this.city);
			return genSelectModel(options);
		}
		return new SelectModelImpl(null, null);
	}
	
	/**
	 * @see corner.area.AreaSelectField#getProvinceField()
	 */
	public Select getProvinceField() {
		return provinceField;
	}

	/**
	 * @see corner.area.AreaSelectField#getCityField()
	 */
	public Select getCityField() {
		return cityField;
	}

	/**
	 * @see corner.area.AreaSelectField#getTownField()
	 */
	public Select getTownField() {
		return townField;
	}
	
	@Override
	public boolean getCityRequired() {
		return this.cityRequired;
	}

	@Override
	public boolean getProvinceRequired() {
		return this.provinceRequired;
	}

	@Override
	public boolean getTownRequired() {
		return this.townRequired;
	}

	
	@OnEvent(component = "provinceField", value = "change")
	JSONObject provinceElementChanged(Object[] params) {
		String value = null;
		if(params != null && params.length==1){
			value = params[0].toString();
		}
		List<AreaModelItem> options = callback.getCities(value);
		return genModel(options);
	}

	@OnEvent(component = "cityField", value = "change")
	JSONObject cityElementChanged(Object[] params) {
		String value = null;
		if(params != null && params.length==1){
			value = params[0].toString();
		}
		List<AreaModelItem> options = callback.getTowns(value);
		return genModel(options);
	}

	private JSONObject genModel(List<AreaModelItem> options) {
		JSONObject json = new JSONObject();
		JSONArray jsarray = new JSONArray();
		for (AreaModelItem area : options) {
			JSONArray array = new JSONArray();
			array.put(area.getName());
			array.put(area.getCode());
			jsarray.put(array);
		}
		json.put("result", jsarray);
		return json;
	}

	private SelectModel genSelectModel(List<AreaModelItem> areas) {
		List<OptionModel> options = new LinkedList<OptionModel>();
		if (areas != null) {
			for (AreaModelItem item : areas) {
				OptionModel model = new OptionModelImpl(item.getName(), item.getCode());
				options.add(model);
			}
		}
		return new SelectModelImpl(null, options);
	}

}
