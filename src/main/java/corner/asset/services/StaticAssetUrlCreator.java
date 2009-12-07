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
package corner.asset.services;

/**
 * 创建静态资源的URL创建者
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public interface StaticAssetUrlCreator {

	/**
	 * 创建URL
	 * @param context http context 
	 * @param protocol 协议，通常为缩写，譬如： hdfs:/xxx
	 * @param path 请求的路径
	 * @param referPath 来自的页面
	 * @return 转换后的值
	 * @since 0.0.2
	 */
	public String createUrl(String context,String protocol,String path,String referPath);
}
