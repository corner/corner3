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
package lichen.services.converter.impl;

import java.util.Map;

import lichen.services.converter.ConverterVersion;
import lichen.services.converter.ConverterVersionSource;
import lichen.utils.StringUtils;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

/**
 * ConverterVersionSource的实现类
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class ConverterVersionServiceImpl implements ConverterVersionSource {

	private final Map<String, ConverterVersion> cache = CollectionFactory
			.newConcurrentMap();

	public ConverterVersionServiceImpl(
			Map<String, ConverterVersion> configuration) {
		for (String _key : configuration.keySet()) {
			cache.put(_key, configuration.get(_key));
		}
	}

	/**
	 * 
	 * @see lichen.services.converter.ConverterVersionSource#getConvertVersion(java.lang.String)
	 */
	@Override
	public ConverterVersion getConvertVersion(String value) {
		String _prefix = StringUtils.getPart(value, "/", 0);
		if (_prefix != null) {
			return this.cache.get(_prefix);
		} else {
			return null;
		}
	}
}
