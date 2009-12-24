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
package corner.http.services;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于控制浏览器对页面的缓存策略
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheHeader {
	/**
	 * 缓存的类型,默认为NOCACHE
	 * @return
	 * @since 0.0.2
	 */
	public CacheHeaderType type() default CacheHeaderType.NOCACHE;
	
	/**
	 * 所选择的CacheHeaderType对应的参数,一般是一个表示时间的整数
	 * <ul>
	 * <li>CacheHeaderType.NOCACHE 不需要参数</li>
	 * <li>CacheHeaderType.EXPIRES_AFTER_NOW 参数值是以秒为单位的整数,表示在n秒之后过期,如600代表在当前时间之后的600秒后过期</li>
	 * <li>CacheHeaderType.EXPIRES_AT 参数值是以秒位单位的整数,这个值应该是某个时间以秒为单位的表示</li>
	 * </ul>
	 * @return
	 * @since 0.0.2
	 */
	public String value() default "";

}
