/* 
 * Copyright 2009 The Corner Team.
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
package corner.tapestry.services.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

/**
 * attachement stream response
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class AttachmentStreamResponse implements StreamResponse {
	private InputStream is = null;

	/**
	 * This can be changed to something obscure, so that it is more likely to
	 * trigger a "Save As" dialog, although there is no guarantee.
	 * 
	 * ex: application/x-download
	 * 
	 * See http://www.onjava.com/pub/a/onjava/excerpt/jebp_3/index3.html
	 */
	protected String contentType = "application/x-download";


	protected String filename = "default";

	public AttachmentStreamResponse(InputStream is, String filenameIn) {
		this.is = is;
		if (filenameIn != null) {
			this.filename = filenameIn;
		}
	}
	public AttachmentStreamResponse(byte [] fileData, String fileName) {
		this(new ByteArrayInputStream(fileData),fileName);
	}

	public AttachmentStreamResponse(InputStream is) {
		this.is = is;
	}

	/**
	 * @see org.apache.tapestry5.StreamResponse#getContentType()
	 */
	@Override
	public String getContentType() {
		return contentType;
	}

	/**
	 * @see org.apache.tapestry5.StreamResponse#getStream()
	 */
	@Override
	public InputStream getStream() throws IOException {
		return is;
	}

	/**
	 * @see org.apache.tapestry5.StreamResponse#prepareResponse(org.apache.tapestry5.services.Response)
	 */
	@Override
	public void prepareResponse(Response arg0) {
		arg0.setHeader("Content-Disposition", "attachment; filename="
				+ filename);
		arg0.setHeader("Expires", "0");
		arg0.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		arg0.setHeader("Pragma", "public");
		// We can't set the length here because we only have an Input Stream at
		// this point. (Although we'd like to.)
		// We can only get size from an output stream.
		// arg0.setContentLength(.length);
	}
}
