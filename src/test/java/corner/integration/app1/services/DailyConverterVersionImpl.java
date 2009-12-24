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
package corner.integration.app1.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import corner.converter.services.Converter;


/**
 * 每天一个
 * 
 * @author dong
 * 
 */
public class DailyConverterVersionImpl implements Converter{

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
