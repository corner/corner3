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
package lichen.services.converter;

import lichen.services.converter.impl.ConverterServiceImpl;
import lichen.services.converter.impl.ConverterVersionServiceImpl;

import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * Conver模块的配置
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class ConverterModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(ConverterSource.class, ConverterServiceImpl.class);
		binder.bind(ConverterVersionSource.class,
				ConverterVersionServiceImpl.class);
	}

}
