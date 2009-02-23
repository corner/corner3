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
package corner.services.hadoop.impl;

import java.util.regex.Pattern;


import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import corner.services.hadoop.GetFileService;

/**
 * 使用正则表达式删除路径中的内容
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class GetFileServiceRegexClearImpl implements GetFileService {
	/** 配置用于删除路径中内容的正则表这式,符合正则表达式的内容将被删除 * */
	public static final String GETFILE_SERVICE_CLEARREGEX = "getfile.service.clearregex";
	private final String regex;
	private final Pattern pattern;

	public GetFileServiceRegexClearImpl(@Inject
	@Symbol(GETFILE_SERVICE_CLEARREGEX)
	String regex) {
		this.regex = regex;
		Pattern _pattern = null;
		if (this.regex != null && this.regex.length() > 0) {
			_pattern = Pattern.compile(this.regex);
		}
		this.pattern = _pattern;
	}

	/**
	 * @see corner.services.hadoop.GetFileService#preprocess(java.lang.String)
	 */
	@Override
	public String preprocess(String path) {
		if (this.pattern == null) {
			return path;
		} else {
			return this.pattern.matcher(path).replaceAll("");
		}
	}

}
