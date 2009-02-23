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
package corner.services.converter;

/**
 * 用于进行版本转换的接口
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public interface ConverterVersion {
	/**
	 * 对value进行版本转换
	 * @param value
	 * @return 返回增加过版本号的新值
	 * @since 0.0.2
	 */
	public String convert(String value);
}
