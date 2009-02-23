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
package corner.services.hadoop;

/**
 * 由pages.GetFile使用的Service
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public interface GetFileService {
	/**
	 * 对path进行预处理
	 * 
	 * @param path
	 *            需要被处理路径
	 * @return 返回经过预处理的结果
	 * @since 0.0.2
	 */
	public String preprocess(String path);

}
