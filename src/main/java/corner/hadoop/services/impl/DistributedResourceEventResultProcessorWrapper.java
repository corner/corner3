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
package corner.hadoop.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.slf4j.Logger;

import corner.hadoop.services.DistributedResource;
import corner.utils.StringUtils;

/**
 * ComponentEventResultProcessor的适配器
 * 根据请求文件的前缀选择对应的ComponentEventResultProcess处理请求.
 * 如果根据前缀无法找到对应的Processor,则尝试查找DEFAULT_PREFIX所定义的Processor,所以至少要定义一个DEFAULT_PREFIX的Processor.
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class DistributedResourceEventResultProcessorWrapper implements
		ComponentEventResultProcessor<DistributedResource> {
	public static final String DEFAULT_PREFIX = "component.eventresult.processor.adaptor.default";

	private final Map<String, ComponentEventResultProcessor> prefixMap = new HashMap<String, ComponentEventResultProcessor>();

	private final Logger logger;

	public DistributedResourceEventResultProcessorWrapper(
			Map<String, ComponentEventResultProcessor> config, Logger logger) {
		prefixMap.putAll(config);
		this.logger = logger;
	}

	/**
	 * @see org.apache.tapestry5.services.ComponentEventResultProcessor#processResultValue(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void processResultValue(DistributedResource value)
			throws IOException {
		final String _path = value.getFilePath();
		ComponentEventResultProcessor<DistributedResource> _processor = null;
		String _prefix = StringUtils.getPart(_path, "/", 0);
		if (_prefix != null) {
			_processor = this.prefixMap.get(_prefix);
		}
		if (_processor == null) {
			_processor = this.prefixMap.get(DEFAULT_PREFIX);
		}
		if (_processor == null) {
			if (logger.isWarnEnabled()) {
				logger.warn(String.format("No processor for path [%s]", _path));
			}
			return;
		}
		_processor.processResultValue(value);
	}
}
