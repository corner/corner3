/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-9-26
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

	public String getContentType() {
		return contentType;
	}

	public InputStream getStream() throws IOException {
		return is;
	}

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
