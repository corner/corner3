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
package corner.payment.services;


/**
 * 用来支付的服务器的集合
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public interface PaymentServiceSource {

	/**
	 * 得到支付使用的处理类
	 * @param serviceType 服务类型
	 * @return 对应的服务处理类
	 * @since 0.0.2
	 */
	public PaymentProcessor getPaymentProcessor(String serviceType) ;

	
}
