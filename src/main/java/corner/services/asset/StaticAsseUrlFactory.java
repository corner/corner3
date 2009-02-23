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
package corner.services.asset;
/**
 *  静态资源域名选择的工厂类接口
 *  
 * @author dong
 * @version $Revision: 1488 $
 * @since 0.0.1
 */
public interface StaticAsseUrlFactory {
	/**
	 * 根据context和path选择取得path所代表的资源的实际地址
	 * @param context 应用的根路径
	 * @param path 请求的资源路径
	 * @param referPath 请求来自的页面
	 * @return
	 * @since 0.0.2
	 */
	public String getUrl(String context,String path,String referPath);
}
