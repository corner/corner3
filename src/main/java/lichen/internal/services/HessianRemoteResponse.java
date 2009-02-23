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
package lichen.internal.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lichen.services.RemoteResponse;

import org.apache.tapestry5.services.Response;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;
import com.caucho.hessian.server.HessianSkeleton;

/**
 * 远程调用的响应类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 2954 $
 * @since 0.0.1
 */
public abstract class HessianRemoteResponse implements RemoteResponse {
	private static final String CONTENT_TYPE="application/x-hessian";
	
	/**
	 * @see org.apache.tapestry5.StreamResponse#getContentType()
	 */
	public String getContentType() {
		return CONTENT_TYPE;
	}



	/**
	 * @see org.apache.tapestry5.StreamResponse#prepareResponse(org.apache.tapestry5.services.Response)
	 */
	public void prepareResponse(Response response) {
		
	}
	public abstract HessianSkeleton getSkeleton();
	
	public void invoke(HttpServletRequest request,HttpServletResponse response) throws IOException {
		InputStream isToUse = request.getInputStream();
		OutputStream osToUse = response.getOutputStream();
		SerializerFactory serializerFactory = new SerializerFactory();
		
		Hessian2Input in = new Hessian2Input(isToUse);
		in.setSerializerFactory(serializerFactory);

		int code = in.read();
		if (code != 'c') {
			throw new IOException("expected 'c' in hessian input at " + code);
		}

		AbstractHessianOutput out = null;
		int major = in.read();
		in.read();
		if (major >= 2) {
			out = new Hessian2Output(osToUse);
		}
		else {
			out = new HessianOutput(osToUse);
		}
		out.setSerializerFactory(serializerFactory);

		try {
			this.getSkeleton().invoke(in, out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		 // TAPESTRY-2415: WebLogic needs this flush() call.            
		osToUse.flush();

		osToUse.close();
		osToUse = null;

		isToUse.close();
		isToUse = null;
        
	}

}
