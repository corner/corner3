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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;


import org.testng.annotations.Test;

import corner.services.payment.impl.processor.AlipayProcessor;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class AlipayProcessorTest {

	@Test
	public void test_verifyreturn() throws Exception {
		String query = "body=%E9%87%91%E4%B8%80%E8%AE%A2%E5%8D%95&buyer_email=agile.java%40gmail.com&buyer_id=2088102047022345&exterface=trade_create_by_buyer&is_success=T&notify_id=RqPnCoPT3K9%252Fvwbh3I%252BOD%252BHewkE6vJKlzCu3gdRH7Vbw6e5UHC2O4phs3V71Le8%252BdlYE&notify_time=2008-10-27+11%3A09%3A48&notify_type=trade_status_sync&out_trade_no=44&payment_type=1&seller_email=finance%401king1.com&seller_id=2088101091710060&subject=%E8%AE%A2%E5%8D%9544&total_fee=0.01&trade_no=2008102712075594&trade_status=TRADE_FINISHED&sign=ecb4cf68c1fec2769903f8f68d6abce8&sign_type=MD5";
		String plainQuery = java.net.URLDecoder.decode(query, "UTF-8");
		Map<String, String> map = new HashMap<String, String>();
		String[] params = plainQuery.split("&");
		for (String param : params) {
			String[] _p = param.split("=");
			map.put(_p[0], _p[1]);
		}
		AlipayProcessor vp = new AlipayProcessor("2088101091710060",
				"lmiqfj8c0xvehcaiuqk96i0jkbj907tu");
		assertTrue(vp.verifyReturn(map));
		map.remove("body");
		assertFalse(vp.verifyReturn(map));
	}
	
	@Test
	public void test_verifynofity() throws Exception {
		AlipayProcessor vp = new AlipayProcessor("2088101091710060",
				"lmiqfj8c0xvehcaiuqk96i0jkbj907tu");
		assertFalse(vp.verifyNotify("aaa"));
	}
}
