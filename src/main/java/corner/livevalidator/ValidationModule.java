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

import org.apache.tapestry5.Validator;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * 定义了所有校验的module
 * 
 * @author Jun Tsai
 * @version $Revision$
 * @since 0.0.1
 */
public class ValidationModule {
	/** lv4t5 javascript的版本 * */
	public static final String LV4T5_JS_VERSION = "lv4t5.js.version";

	public static void bind(ServiceBinder binder) {
		binder.bind(ValidatorRenderSupport.class,
				ValidatorRenderSupportImpl.class);
	}

	/**
	 * 实现对field的校验的扩展.
	 * 
	 * @param configuration
	 *            配置.
	 * 
	 */
	public static void contributeFieldValidatorSource(
			MappedConfiguration<String, Validator> configuration,
			ObjectLocator locator) {
		// 针对必填字段的校验
		configuration.add("presence", locator.autobuild(Presence.class));
		configuration
				.add("confirmation", locator.autobuild(Confirmation.class));
		configuration.add("mail", locator.autobuild(Email.class));
		configuration.add("mostLength", locator.autobuild(MostLength.class));
		configuration.add("lessLength", locator.autobuild(LessLength.class));
		configuration.add("pegLength", locator.autobuild(PegLength.class));
		configuration.add("maxnum", locator.autobuild(MaxNum.class));
		configuration.add("minnum", locator.autobuild(MinNum.class));
		configuration.add("pegnum", locator.autobuild(PegNum.class));
		configuration.add("mustnum", locator.autobuild(MustNum.class));
		configuration.add("liveregexp", locator.autobuild(Regexp.class));
		configuration.add("liveuploadregexp", locator
				.autobuild(UploadRegexp.class));
		configuration.add("fckeditor", locator.autobuild(Fckeditor.class));
	}

	public void contributeValidationMessagesSource(
			OrderedConfiguration<String> configuration) {
		configuration.add("corner","corner/validator/ValidationMessages");
	}
	
	public static void contributeFactoryDefaults(
			MappedConfiguration<String, String> configuration) {
		configuration.add(LV4T5_JS_VERSION, "lv4t5.js");
	}

}
