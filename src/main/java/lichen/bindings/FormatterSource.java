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

/**
 * 所有的格式化源
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public interface FormatterSource {

	/**
	 * 通过给定的format的名称来的到格式化工具类.
	 * @param formatName 格式化名称
	 * @return 格式化工具类
	 * @since 0.0.2
	 */
	public Format getFormatter(String formatName);
}
