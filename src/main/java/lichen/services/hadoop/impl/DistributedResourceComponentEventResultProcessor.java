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
package lichen.services.hadoop.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import lichen.services.hadoop.DistributedResource;
import lichen.services.hadoop.DistributedResourceAccessor;
import lichen.services.hadoop.FileDesc;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

/**
 * 针对Hadoop资源文件的引用处理方式
 * 
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class DistributedResourceComponentEventResultProcessor implements
		ComponentEventResultProcessor<DistributedResource> {
	private Response response;
	private Request request;
	private DistributedResourceAccessor accessor;
	private boolean userCache;
	private final long expiresTime = 60 * 60 * 12;

	public DistributedResourceComponentEventResultProcessor(
			DistributedResourceAccessor accessor, Response response,
			Request request, boolean useCache) {
		this.accessor = accessor;
		this.response = response;
		this.request = request;
		this.userCache = useCache;
	}

	/**
	 * @see org.apache.tapestry5.services.ComponentEventResultProcessor#processResultValue(java.lang.Object)
	 */
	@Override
	public void processResultValue(DistributedResource value)
			throws IOException {
		OutputStream os = null;
		value.prepareResponse(response);
		FileDesc _fileDesc = null;
		try {
			_fileDesc = this.accessor.getFileDesc(value.getFilePath());
		} catch (FileNotFoundException ne) {
			//捕获FileNotFoundExcepiton,统一作为404处理
		}
		if (_fileDesc == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"Can't find your request file [" + value.getFilePath()
							+ "]");
			return;
		}

		/*
		 * 设置http头中的Content-Length头,这里可能有一个问题,我们强制把long型的长度转化成int,虽然不大可能返回超过int可表示长度的文件,
		 * 但这是可能是一个潜在的问题,BTW,不知道为啥T5的接口使用int类型
		 */
		response.setContentLength((int) _fileDesc.getLength());

		try {
			os = response.getOutputStream(value.getContentType());
			if (this.userCache) {
				// 使用缓存
				final long _mtTime = _fileDesc.getModifyTime().getTime();
				if (_mtTime > 0) {
					response.setDateHeader("Last-Modified", _mtTime);
					long _lastModifiedSince = request
							.getDateHeader("If-Modified-Since");
					if (_mtTime / 1000 <= _lastModifiedSince / 1000) {
						response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					} else {
						long _date = System.currentTimeMillis();
						long _expires = _date + (expiresTime * 1000);
						response.setDateHeader("Date", _date);
						response.setDateHeader("Expires", _expires);
						response.setHeader("Cache-Control", "public, max-age="
								+ expiresTime);
						accessor.getFile(value.getFilePath(), os);
					}
				}
			} else {
				// 不使用缓存
				response.setDateHeader("Date", System.currentTimeMillis());
				response.setHeader("Cache-Control", "no-cache, no-store");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Expires", "-1");
				accessor.getFile(value.getFilePath(), os);
			}
			os.flush();

		} finally {
			InternalUtils.close(os);
		}

	}

}
