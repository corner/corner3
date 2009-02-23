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
package corner.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.services.Response;

import com.caucho.hessian.server.HessianSkeleton;

/**
 * 远程调用响应接口类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public interface RemoteResponse {

	/**
	 * 准备response之前的操作。
	 * @param response web response
	 */
	void prepareResponse(Response response);

	/**
	 * content type
	 * @return content type
	 */
	String getContentType();

	/**
	 * 响应对应的
	 * @param response web request 
	 * @param request web response
	 * @throws IOException 
	 */
	void invoke(HttpServletRequest request,HttpServletResponse response) throws IOException;
	
	/**
	 * 得到Hessian的Skeleton实体对象.
	 * @return Hessian对象.
	 */
	HessianSkeleton getSkeleton();

}
