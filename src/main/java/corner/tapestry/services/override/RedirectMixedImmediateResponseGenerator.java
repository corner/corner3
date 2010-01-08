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
package corner.tapestry.services.override;

import java.io.IOException;

import org.apache.tapestry5.internal.services.ActionRenderResponseGeneratorImpl;
import org.apache.tapestry5.internal.services.ImmediateActionRenderResponseGenerator;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.internal.structure.Page;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import corner.tapestry.ComponentConstants;

/**
 * 混合了immediate和ActionRenderResponseGeneratorImpl
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class RedirectMixedImmediateResponseGenerator extends
		ImmediateActionRenderResponseGenerator {

	private ActionRenderResponseGeneratorImpl redirectGenerator;
	private Request request;

	public RedirectMixedImmediateResponseGenerator(LinkSource linkSource,Request request, Response response) {
		super(request);
		this.request = request;
		redirectGenerator = new ActionRenderResponseGeneratorImpl(linkSource,response);
	}

	/**
	 * @see org.apache.tapestry5.internal.services.ImmediateActionRenderResponseGenerator#generateResponse(org.apache.tapestry5.internal.structure.Page)
	 */
	@Override
	public void generateResponse(Page page) throws IOException {
        if (request.getAttribute(ComponentConstants.REQUIRED_REDIRECT) != null){
        	redirectGenerator.generateResponse(page);
        }else{
        	super.generateResponse(page);
        }
	}
}
