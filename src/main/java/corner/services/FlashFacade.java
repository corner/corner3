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
package corner.services;

/**
 * 用来操作flash的前端展示类
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public interface FlashFacade {

	/** 常用的几个消息 **/
	public static final String NOTICE_KEY="notice"; 
	public static final String WARN_KEY="warn"; 
	public static final String ERROR_KEY="error"; 
	/**
	 * push一条消息
	 * @param key key的键值
	 * @param value 对应的消息值
	 */
	public void push(String key,String value);
	/**
	 * 得到对应的值
	 * @param key 键值
	 * @return 消息
	 */
	public String get(String key);
}
