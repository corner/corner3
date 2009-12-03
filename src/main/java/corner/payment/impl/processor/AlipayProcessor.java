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
package corner.payment.impl.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;

import corner.payment.PaymentProcessor;
import corner.payment.ViewHelper;
import corner.payment.impl.view.AlipayViewHelper;

/**
 * 针对支付宝的处理类
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 4056 $
 * @since 0.0.2
 */
public class AlipayProcessor implements PaymentProcessor {
	/** 支付宝的支付网关 * */
	private static final String GATEWAY_URL = "https://www.alipay.com/cooperate/gateway.do?_input_charset=utf-8";
	/** 支付宝的查询验证接口* */
	private static final String QUERY_URL = "http://notify.alipay.com/trade/notify_query.do";
	private String key;
	private String partner;

	public AlipayProcessor(@Inject
	@Symbol("alipay.partner")
	String partner, @Inject
	@Symbol("alipay.key")
	String key) {
		this.partner = partner;
		this.key = key;

	}

	/**
	 * @see corner.payment.PaymentProcessor#getServiceUrl()
	 */
	@Override
	public Object getServiceUrl() {
		return GATEWAY_URL;
	}

	/**
	 * @see corner.payment.PaymentProcessor#getViewHelper(org.apache.tapestry5.MarkupWriter)
	 */
	@Override
	public ViewHelper getViewHelper(MarkupWriter writer, JSONObject options) {
		// 增加一些常用的配置
		return new AlipayViewHelper(writer, options, partner, key);
	}

	/**
	 * 
	 * @see corner.payment.PaymentProcessor#verifyReturn(java.util.Map)
	 */
	public boolean verifyReturn(Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			throw new IllegalArgumentException("No params.");
		}
		final Map<String, String> _data = new HashMap<String, String>();
		String _sign = null;
		String _sign_type = null;
		// 从参数map中提取出sing和sign_type,并将它们从校验的参数中去掉
		for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String value = params.get(name);
			if (name.equalsIgnoreCase("sign")) {
				_sign = value;
				continue;
			} else if (name.equalsIgnoreCase("sign_type")) {
				_sign_type = value;
				continue;
			}
			_data.put(name, value);
		}

		if (_sign == null || _sign_type == null) {
			// 没有签名参数和签名的类型,验证失败
			return false;
		}
		if (!"MD5".equalsIgnoreCase(_sign_type)) {
			throw new UnsupportedOperationException("Not support digest type["
					+ _sign_type + "]");
		}
		// 按照支付宝的要求,对参数按字母升序排列
		final List<String> keys = new ArrayList<String>(_data.keySet());
		Collections.sort(keys);

		final StringBuilder content = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = _data.get(key);
			content.append((i == 0 ? "" : "&") + key + "=" + value);
		}
		content.append(this.key);

		String _tocheck = "";
		if ("MD5".equalsIgnoreCase(_sign_type)) {
			try {
				_tocheck = DigestUtils.md5Hex(content.toString().getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return _tocheck.equalsIgnoreCase(_sign);
	}

	@Override
	public boolean verifyNotify(String notifyId) {
		if(notifyId == null){
			throw new IllegalArgumentException("The notifyId is null");
		}
		String alipayNotifyURL = String.format("%s?partner=%s&notify_id=%s",
				QUERY_URL, partner, notifyId);
		java.io.Closeable input = null;
		try {
			URL url = new URL(alipayNotifyURL);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setDoOutput(false);
			urlConnection.setConnectTimeout(30 * 1000);
			urlConnection.setReadTimeout(30 * 1000);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			input = in;
			String inputLine = in.readLine().toString();
			urlConnection.disconnect();
			if ("true".equalsIgnoreCase(inputLine)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
		return false;
	}
}
