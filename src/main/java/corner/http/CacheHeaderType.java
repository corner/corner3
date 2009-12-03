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
package corner.http;

/**
 * HTTP Cache Header 类型的定义
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public enum CacheHeaderType {
	/** 在浏览器中不缓存 * */
	NOCACHE,
	/** 浏览器的缓存在当前时间之后的一个指定时间后过期 * */
	EXPIRES_AFTER_NOW,
	/** 浏览器的缓存在指定的时间之后过期* */
	EXPIRES_AT;
}
