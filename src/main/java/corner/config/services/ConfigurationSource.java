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
package corner.config.services;

/**
 * 得到服务配置的工厂类
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public interface ConfigurationSource {

	/**
	 * 通过给定的服务配置类来得到对应的服务配置类.
	 * @param <T> 服务配置.
	 * @param clazz 配置类的class
	 * @return 带有数据的配置实例.
	 */
	public <T> T getServiceConfig(Class<T> clazz);
}
