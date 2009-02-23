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
package corner.services.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标注用户访问某个资源时是否有权限 需要由ResourceSecurityWorker和ResourceSecurityObject提供支持.
 * 
 * @author dong
 * @version $Revision: $
 * @since 0.0.1
 */
@Target( { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ResourceSecurity {
	/**
	 * 用于校验的Service接口类,
	 * 
	 * @return
	 */
	public Class service();

	/**
	 * Service的ID
	 * 
	 * @return
	 */
	public String serviceId() default "";

	/**
	 * service中负责校验资源的方法名,方法的参数由ResourceSecurityObject标识的被验证方法中的参数声明取得,顺序与被验证方法的顺序一致。
	 * 如: 
	 * <code>
	 * @ResourceSecurity(service=ShoppingCartSevice.class)
	 * public void onDelete(@ResourceSecurityObject ShoppingCartItem item)
	 * </code>
	 * 此被验证的方法是onDelete,service中对应的方法声明是<code>boolean aclCheck(ShoppingCartItem item)</code>
	 * @return
	 */
	public String checkMethod() default "aclCheck";

	/**
	 * 校验失败后是否抛出异常
	 * 
	 * @return
	 */
	public boolean throwExcepiton() default true;
}
