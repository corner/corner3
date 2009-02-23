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
package corner.services.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标注登录时候的角色,以及与安全相关的属性定义
 * 需要由SecurityWorker提供支持,SecurityWorker的配置默认由SecurityModule提供
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @author dong
 * @version $Revision: 718 $
 * @since 0.0.1
 */
@Target( { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Security {
	/**
	 * Returns the list of security configuration attributes. (i.e. ROLE_USER,
	 * ROLE_ADMIN etc.)
	 * 
	 * @return String[] The secure method attributes
	 */
	public String[] value() default "";
	
	/**
	 * 返回安全校验失败后定向到的页面类
	 * @return
	 */
	public Class<?> fail() default Object.class;
}
