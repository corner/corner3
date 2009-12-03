/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ganshane.net
 * file: $Id: StaticLink.java 1489 2008-10-13 00:28:01Z d0ng $
 * created at:2008-09-30
 */

package corner.tapestry.components;


import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import corner.asset.StaticAsseUrlFactory;
import corner.converter.Converter;
import corner.converter.ConverterSource;
import corner.converter.ConverterVersion;
import corner.converter.ConverterVersionSource;

/**
 * 用于img,link,script静态资源的路径选择,具体的路径由locator决定;根据配置增加资源的版本号
 * 
 * 
 * @author dong
 * @version $Revision: 1489 $
 * @since 0.0.1
 */
@SupportsInformalParameters
public class StaticLink {
	@Parameter("componentResources.elementName")
	private String elementName;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private boolean version;

	@Inject
	private ComponentResources resources;

	@Inject
	private Request request;

	@Inject
	private StaticAsseUrlFactory locator;

	@Inject
	private ConverterSource converterSource;

	@Inject
	private ConverterVersionSource converterVersionSource;

	void beginRender(MarkupWriter writer) {
		if (elementName != null) {
			writer.element(elementName);
			String _attribute = null;
			if (this.elementName.equals("img")) {
				_attribute = "src";
			} else if (this.elementName.equals("link")) {
				_attribute = "href";
			} else if (this.elementName.equals("script")) {
				_attribute = "src";
			} else if (this.elementName.equals("a")) {
				_attribute = "href";
			}
			if (_attribute != null) {
				String _value = this.resources.getInformalParameter(_attribute,
						String.class);
				if (_value != null) {
					String _protocol = null;
					int _index = _value.indexOf("://");
					if (_index > 0) {
						_protocol = _value.substring(0, _index);
					}
					if (_protocol != null) {
						Converter _converter = this.converterSource
								.getConvert(_protocol);
						if (_converter != null) {
							String _convertValue = _converter.convert(_value);
							if (_convertValue != null) {
								_value = _convertValue;
							}
						}
					}

					_value = this.locator.getUrl(request.getContextPath(),
							_value, resources.getPageName());

					// 是否需要判断文件的版本号
					boolean _version = resources.isBound("version") ? this.version
							: false;
					if (_version) {
						ConverterVersion _converterVersion = this.converterVersionSource
								.getConvertVersion(_value);
						if (_converterVersion != null) {
							String _versionValue = _converterVersion
									.convert(_value);
							if (_versionValue != null) {
								_value = _versionValue;
							}
						}
					}
					writer.attributes(_attribute, _value);
				}
			}
			resources.renderInformalParameters(writer);
		}
	}

	void afterRender(MarkupWriter writer) {
		writer.end();
	}
}