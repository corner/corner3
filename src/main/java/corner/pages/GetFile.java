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
package corner.pages;


import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import corner.services.hadoop.DistributedResource;
import corner.services.hadoop.GetFileService;

/**
 * 实现分布式文件读取
 * <p>
 * 需要注意的是在Nginx/Apache中可以配置重写规则 
 * <code>
 * proxy_store  on;
 * proxy_temp_path /opt/temp;
 * rewrite ^/hadoop/([^$]*)$ /corner/hadoop.getfile/$1 last;
 * </code>
 * </p>
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class GetFile {

	@Inject
	private ApplicationGlobals globals;
	
	@Inject
	private GetFileService getFileService;
	
	@Inject
	private Request request;

	// 从hadoop中获取数据
	DistributedResource onActivate() {
		String path = request.getPath();
		final String filePath = this.getFileService.preprocess(path.replaceAll("[\\w\\/]*getfile/", ""));

		return new DistributedResource() {

			@Override
			public String getContentType() {
				// 通过servlet的context中定义的mimetype来获取对应的ContextType
				String _type = globals.getServletContext().getMimeType(filePath);
				return _type != null?_type:"text/plain";
			}

			@Override
			public String getFilePath() {
				return filePath;
			}

			@Override
			public void prepareResponse(Response response) {
			}
		};
	}

}
