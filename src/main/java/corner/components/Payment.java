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
package corner.components;


import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;

import corner.services.payment.PaymentProcessor;
import corner.services.payment.PaymentServiceSource;
import corner.services.payment.ViewHelper;

/**
 * 支付系统帐号
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 3408 $
 * @since 0.0.2
 */

public class Payment {

	/**
	 * 支付的类型
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String service;
	/**
	 * 支付时候的一些其他参数
	 */
	@Parameter
	private JSONObject options;
	
	@Inject
	private PaymentServiceSource paymentServiceSource;
	
	@Inject
	private Messages messages;
	
	@BeginRender
	void renderPaymentForm(MarkupWriter writer){
		PaymentProcessor processor = paymentServiceSource.getPaymentProcessor(service);
		writer.element("form","action",processor.getServiceUrl(),"method","post");
		//得到前端映射
		ViewHelper helper = processor.getViewHelper(writer,options);
		helper.outputFieldInformation();
		writer.element("input","type","submit","value",messages.get("pay"));
	}
	@AfterRender
	void endRenderPaymentForm(MarkupWriter writer){
		writer.end();
		writer.end();
	}

	   // For testing:

    void setPaySource(PaymentServiceSource paymentServiceSource)
    {
    	this.paymentServiceSource = paymentServiceSource;
    }
    void setMessagesObject(Messages messages){
    	this.messages = messages;
    }
}
