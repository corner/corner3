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
package corner.services.hadoop;

import org.apache.tapestry5.services.Response;

/**
 * 分布式文件资源的引用
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public interface DistributedResource {
	
	/**
	 * 准备输出时候的操作
	 * @param response web 响应
	 * @since 0.0.2
	 */
	void prepareResponse(Response response);
	/**
	 * 得到向浏览发送contenttype的类型，譬如： text/html,application/ms-word 等
	 * @return
	 * @since 0.0.2
	 */
	String getContentType();
	/**
	 * 得到要输出的文件的路径
	 * @return 文件路径
	 * @since 0.0.2
	 */
	String getFilePath();

}
