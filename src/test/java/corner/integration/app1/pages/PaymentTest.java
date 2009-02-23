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
package corner.integration.app1.pages;

import org.apache.tapestry5.json.JSONObject;

public class PaymentTest {

	public JSONObject getPaymentParameter(){
		JSONObject options = new JSONObject();
		//订单信息
		options.put("order", "2008102211"); //订单号
		options.put("description","阿");//订单的描述
		options.put("subject", "啊啊");//订单的名称
		options.put("total_fee","0.01");//价格
		
		
		//TODO 考虑一下信息由配置产生
		options.put("service","create_direct_pay_by_user");//
		options.put("paymethod","bankPay");
		options.put("defaultbank","ICBCB2C");
		options.put("charset","utf-8");
		options.put("show_url","www.sina.com.cn");
		options.put("return_url","http://10.2.17.136:8081/jsp_direct_utf/alipay_return.jsp");
		options.put("notify_url","http://10.2.17.136:8081/jsp_direct_utf/alipay_notify.jsp");
		options.put("payment_type","1");
		options.put("seller", "finance@1king1.com");

		return options;
	}
}
