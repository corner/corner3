package corner.tapestry.services.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

/**
 * 
 * inline stream response
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class InlineStreamResponse implements StreamResponse {
        private InputStream is = null;

        protected String contentType = "text/plain";// this is the default


        protected String filename = "default";

        public InlineStreamResponse(InputStream is, String fileName,String contentType) {
                this.is = is;
                if(fileName != null){
                	filename = fileName;
                }
                if(contentType != null){
                	this.contentType = contentType;
                }
        }

        public InlineStreamResponse(byte[] fileData, String fileName,String  contentType) {
        	this(new ByteArrayInputStream(fileData),fileName,contentType);
		}

		public String getContentType() {
                return contentType;
        }

        public InputStream getStream() throws IOException {
                return is;
        }

        public void prepareResponse(Response response) {
                response.setHeader("Content-Disposition", "inline; filename=" + filename);
        }
}
