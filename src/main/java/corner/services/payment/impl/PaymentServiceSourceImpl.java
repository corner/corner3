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
package corner.services.payment.impl;

import java.util.Map;

import corner.services.payment.PaymentProcessor;
import corner.services.payment.PaymentServiceSource;


/**
 * 实现支付服务类
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 2138 $
 * @since 0.0.2
 */
public class PaymentServiceSourceImpl implements PaymentServiceSource {
	 
	private Map<String, PaymentProcessor> processors;

	public PaymentServiceSourceImpl( Map<String, PaymentProcessor> processors ) {
		this.processors = processors;
	}

	/**
	 * @see corner.services.payment.PaymentServiceSource#getPaymentProcessor(java.lang.String)
	 */
	@Override
	public PaymentProcessor getPaymentProcessor(String serviceType) {
		return this.processors.get(serviceType);
	}

}
