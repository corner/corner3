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
package corner.tapestry.components;

import java.io.IOException;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.test.TapestryTestCase;
import org.testng.annotations.Test;

import corner.payment.services.PaymentProcessor;
import corner.payment.services.PaymentServiceSource;
import corner.payment.services.ViewHelper;
import corner.tapestry.components.Payment;

/**
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */

public class PaymentTest extends TapestryTestCase{

	@Test
	public void testOutput() throws IOException{
		
		MarkupWriter writer=this.createMarkupWriter();
		Payment payment = new Payment();
		
		PaymentServiceSource paymentServiceSource = this.newMock(PaymentServiceSource.class);
		PaymentProcessor paymentProcessor=this.newMock(PaymentProcessor.class);
		expect(paymentServiceSource.getPaymentProcessor(null)).andReturn(paymentProcessor);
		expect(paymentProcessor.getServiceUrl()).andReturn("https://www.alipay.com/cooperate/gateway.do?");
		
		Messages messages = this.newMock(Messages.class);
		payment.setMessagesObject(messages);
		expect(messages.get("pay")).andReturn("支付").times(1);
		
		ViewHelper viewHelper = new ViewHelper(){

			@Override
			public JSONObject getMapping() {
				return null;
			}


			@Override
			public void sign() {
			}

			@Override
			public String getFieldNameMapped(String srcKey) {
				return null;
			}

			@Override
			public void outputFieldInformation() {
			}
		};
		expect(paymentProcessor.getViewHelper(writer,null)).andReturn(viewHelper );
		replay();
		payment.setPaySource(paymentServiceSource);
		payment.renderPaymentForm(writer);
		payment.endRenderPaymentForm(writer);
		verify();
		assertEquals("<form method=\"post\" action=\"https://www.alipay.com/cooperate/gateway.do?\"><input value=\"支付\" type=\"submit\"></input></form>",writer.toString());
	}
}
