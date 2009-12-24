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

import org.apache.tapestry5.json.JSONObject;

/**
 * 用来展示对视图进行展示的帮助类.
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public  interface ViewHelper {

	/**
	 * 得到前端页面进行mapping的集合
	 * @return 页面中mapping的集合
	 * @since 0.0.2
	 */
	public JSONObject getMapping();
	/**
	 * 得到映射后的字段名称
	 * @param srcKey 原始的字段名称
	 * @return 映射后的字段名称
	 * @since 0.0.2
	 */
	public String getFieldNameMapped(String srcKey);
	/**
	 * 进行数字签名信息
	 * @since 0.0.2
	 */
	public void sign();
	/**
	 * 在视图中进行的处理.
	 * @param writer 前端的视图输出
	 * @param options 支付时候的输出的其他参数
	 * @since 0.0.2
	 */
	public void outputFieldInformation();
}
