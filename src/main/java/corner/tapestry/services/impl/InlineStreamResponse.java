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
        public void prepareResponse(Response response) {
                response.setHeader("Content-Disposition", "inline; filename=" + filename);
        }
}
