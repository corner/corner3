/* 
 * Copyright 2008 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.payment;


import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ServiceBinder;

import corner.payment.services.PaymentProcessor;
import corner.payment.services.PaymentServiceSource;
import corner.payment.services.impl.PaymentServiceSourceImpl;
import corner.payment.services.impl.processor.AlipayProcessor;

/**
 * 支付模块
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class PaymentModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(PaymentServiceSource.class,PaymentServiceSourceImpl.class);
	}
    public static void contributePaymentServiceSource(MappedConfiguration<String, PaymentProcessor> configuration,ObjectLocator locator){
    	configuration.add("alipay",locator.autobuild(AlipayProcessor.class));
    }
    public static void contributeFactoryDefaults(
			MappedConfiguration<String, String> configuration) {
		configuration.add("alipay.partner","2088101091710060");
		configuration.add("alipay.key","lmiqfj8c0xvehcaiuqk96i0jkbj907tu");
	}
}
