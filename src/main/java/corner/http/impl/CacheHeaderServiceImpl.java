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
package corner.http.impl;


import org.apache.tapestry5.services.Response;

import corner.http.CacheHeaderService;
import corner.http.CacheHeaderType;

/**
 * 实现CacheHeaderService的接口
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class CacheHeaderServiceImpl implements CacheHeaderService {

	@Override
	public void setCacheHeader(Response response, CacheHeaderType type,
			String value) {
		if (type == null) {
			throw new IllegalArgumentException(
					"The cache header type is excepted");
		}
		if (type == CacheHeaderType.NOCACHE) {
			// 不缓存
			response.setDateHeader("Date", System.currentTimeMillis());
			response.setHeader("Cache-Control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");
		} else if (type == CacheHeaderType.EXPIRES_AFTER_NOW) {
			//当前时间之后的一段时间过期
			if (value == null || value.trim().length() == 0) {
				throw new IllegalArgumentException("The value is excpted for type "+CacheHeaderType.EXPIRES_AFTER_NOW);
			}
			long _value = Long.parseLong(value);
			long _date = System.currentTimeMillis();
			long _expires = _date+(_value*1000);
			response.setDateHeader("Date",_date);
			response.setDateHeader("Expires",_expires);
			response.setHeader("Cache-Control","public, max-age="+_value);
		}else if(type==CacheHeaderType.EXPIRES_AT){
			//在指定的时间后过期
			long _date = System.currentTimeMillis();
			long _value = Long.parseLong(value)*1000;
			response.setDateHeader("Date",_date);
			response.setDateHeader("Expires",_value);
		}
	}
}
