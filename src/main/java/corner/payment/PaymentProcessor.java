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
package corner.payment;


import java.util.Map;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.json.JSONObject;


/**
 * 进行支付操作的处理器
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 2268 $
 * @since 0.0.2
 */
public interface PaymentProcessor {

	
	/**
	 * 得到服务网关的URL
	 * @return
	 * @since 0.0.2
	 */
	public Object getServiceUrl();

	/**
	 * 得到视图的帮助类.
	 * @param writer Html Page Writer
	 * @param options 客户端信息
	 * @return 试图帮助类
	 * @since 0.0.2
	 */
	public ViewHelper getViewHelper(MarkupWriter writer,JSONObject options);
	
	/**
	 * 验证返回的参数是否正确
	 * @param params
	 * @return true,通过验证;false,未通过验证
	 * @since 0.0.2
	 */
	public boolean verifyReturn(Map<String, String> params);
	
	/**
	 *  校验通知的消息是否真实有效
	 * @param notifyId 通知的id号
	 * @return true,该通知id号是有效的;false,该通知id是无效的
	 * @since 0.0.2
	 */
	public boolean verifyNotify(String notifyId);
}
