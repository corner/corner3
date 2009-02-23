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
package lichen.bindings;

import java.text.Format;
import java.util.Map;

/**
 * 针对格式化源的实现
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class FormatterSourceImpl implements FormatterSource {

	/**
	 * 所有的格式化配置
	 */
	private Map<String, Format> configuration;

	public FormatterSourceImpl(Map<String,Format> configuration) {
		this.configuration = configuration;
	}

	/**
	 * @see lichen.bindings.FormatterSource#getFormatter(java.lang.String)
	 */
	@Override
	public Format getFormatter(String formatName) {
		return configuration.get(formatName);
	}

}
