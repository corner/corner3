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
package corner.payment.services.impl.view;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.json.JSONObject;


/**
 * 用来针对支付宝的前端输出帮助类
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class AlipayViewHelper extends AbstractViewHelper {
	
	private static final JSONObject DEFAULT_OPTIONS=new JSONObject();
	static{
		DEFAULT_OPTIONS.put("account","finance@1king1.com");
	}

	private String key;

	
	public AlipayViewHelper(MarkupWriter writer,JSONObject options,String partner ,String key) {
		super(writer,options);
		
		this.getMapping().put("charset","_input_charset");
		this.getMapping().put("description","body");
		this.getMapping().put("account","partner");
		this.getMapping().put("amount","total_fee");
		this.getMapping().put("order","out_trade_no");
		this.getMapping().put("seller", "seller_email");
		this.getMapping().put("notify_url","notify_url");
		this.getMapping().put("return_url","return_url");
		this.getMapping().put("show_url","show_url");
		this.getMapping().put("description","body");
		this.getMapping().put("charset","_input_charset");
		this.getMapping().put("service","service");
		this.getMapping().put("payment_type","payment_type");
		this.getMapping().put("subject","subject");
		this.key = key;
		
		options.put("account",partner);
	}
	

	/**
	 * @see corner.payment.services.ViewHelper#sign()
	 */
	@Override
	public void sign() {
		String [] signParameter=new String[]{"charset","description","defaultbank","notify_url","order","account",
				"payment_type","paymethod","return_url","seller","service","show_url","subject","total_fee"};
		StringBuffer sb = new StringBuffer();
		
		for(String p : signParameter){
			String v = this.getOptionValue(p);
			if(v==null){
				continue;
			}
			sb.append(this.getFieldNameMapped(p));
			sb.append("=");
			sb.append(v);
			sb.append("&");
		}
		sb.setLength(sb.length()-1);
		sb.append(this.key);
		System.out.println("from corner:");
		System.out.println(sb.toString());
		this.addField("sign",DigestUtils.md5Hex(sb.toString()));
		this.addField("sign_type","MD5");
	}

	/**
	 * @see corner.payment.services.impl.view.AbstractViewHelper#outputFieldInformation(org.apache.tapestry5.json.JSONObject)
	 */
	@Override
	public void outputFieldInformation() {
		super.outputFieldInformation();
		this.sign();
	}
	
}
