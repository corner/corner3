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
package corner.internal.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.RequestGlobals;

import corner.services.RemoteResponse;

/**
 * 对远程调用进行的响应操作
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public class RemoteResponseResultProcessor implements
		ComponentEventResultProcessor<RemoteResponse> {
	private RequestGlobals requestGlobals;
	
	

	public RemoteResponseResultProcessor(RequestGlobals requestGlobals) {
		this.requestGlobals = requestGlobals;
	}

	/**
	 * 
	 * @see org.apache.tapestry5.services.ComponentEventResultProcessor#processResultValue(java.lang.Object)
	 */
	public void processResultValue(RemoteResponse remoteResponse)
			throws IOException {

		OutputStream os = null;
		InputStream is = null;

		remoteResponse.prepareResponse(requestGlobals.getResponse());

		try {
			this.requestGlobals.getHTTPServletResponse().setContentType(remoteResponse.getContentType());
			remoteResponse.invoke(this.requestGlobals.getHTTPServletRequest(),this.requestGlobals.getHTTPServletResponse());
		}catch(Exception e){
            throw new RuntimeException(e);
		}
		finally {
			InternalUtils.close(is);
			InternalUtils.close(os);
		}

	}
}
