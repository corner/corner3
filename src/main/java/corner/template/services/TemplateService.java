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
package corner.template.services;

import java.io.InputStream;
import java.util.Map;

import org.apache.tapestry5.ioc.Resource;

/**
 * 模板服务，提供一些模板产生，譬如在发送email的时候，可以录入一些模板.
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public interface TemplateService {
	/**
	 * 给定的template和参数，输出对应的结果
	 * @param template 模板对象
	 * @param parameters 参数map
	 * @return 渲染后的结果
	 * @since 0.0.2
	 */
	public String output(String template,Map<String,Object> parameters);
	/**
	 * 给定的template和参数，输出对应的结果
	 * @param template 模板对象
	 * @param parameters 参数map
	 * @return 渲染后的结果
	 * @since 0.0.2
	 */
	public String output(InputStream template,Map<String,Object> parameters);
	/**
	 * 给定的resource和参数，输出结果
	 * @param resource 资源路径
	 * @param parameters 参数
	 * @return
	 * @since 0.0.2
	 */
	public String output(Resource resource,
			Map<String, Object> parameters);

}
