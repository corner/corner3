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
package corner.integration.app1.pages;

import java.util.Date;


import org.apache.tapestry5.annotations.Meta;

import corner.services.http.CacheHeader;
import corner.services.http.CacheHeaderType;

@Meta({"tapestry.response-content-type=text/html"})
@CacheHeader(type=CacheHeaderType.NOCACHE)
public class TestFormat {
	void onActivate(){
	}
	public Date getCurrentDate(){
		return new java.util.Date();
	}
	public double getCurrency(){
		return 0.246;
	}
}
