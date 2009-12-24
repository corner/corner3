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
package corner.transaction.services;

/**
 * 事物相关的Decorator,用来采用aop方式自动对事物进行处理
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface TransactionDecorator {
	/**
	 * 构建
	 * @param <T>
	 * @param serviceInterface
	 * @param delegate
	 * @param serviceId
	 * @return
	 * @since 3.1
	 */
	public abstract <T> T build(Class<T> serviceInterface, T delegate,
			String serviceId);
}
