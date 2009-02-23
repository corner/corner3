/**
 * 
 */
package corner.integration.app1.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import corner.services.converter.ConverterVersion;


/**
 * 每天一个
 * 
 * @author dong
 * 
 */
public class DailyConverterVersionImpl implements ConverterVersion {

	public DailyConverterVersionImpl() {
	}

	@Override
	public String convert(String value) {
		if (value == null) {
			return null;
		}
		int _index = value.lastIndexOf(".");
		if (_index > 0) {
			SimpleDateFormat _format = new SimpleDateFormat("yyyyMMdd");
			return value.substring(0, _index) + "_V"
					+ _format.format(new Date()) + value.substring(_index);
		}
		return value;
	}

}
