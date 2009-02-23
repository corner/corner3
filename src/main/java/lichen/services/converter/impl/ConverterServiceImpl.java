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

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import lichen.services.converter.Converter;
import lichen.services.converter.ConverterSource;

/**
 * ConverterSource的实现类
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class ConverterServiceImpl implements ConverterSource {

	private final Map<String, Converter> cache = CollectionFactory
			.newConcurrentMap();

	public ConverterServiceImpl(Map<String, Converter> configuration) {
		for (String _key : configuration.keySet()) {
			cache.put(_key, configuration.get(_key));
		}
	}

	/**
	 * @see lichen.services.converter.ConverterSource#getConvert(java.lang.String)
	 */
	@Override
	public Converter getConvert(String name) {
		return this.cache.get(name);
	}

}
