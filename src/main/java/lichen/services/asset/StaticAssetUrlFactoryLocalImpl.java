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
package lichen.services.asset;

import java.util.Stack;
import java.util.regex.Pattern;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.slf4j.Logger;

/**
 * 构造位于本地的静态资源的URL
 * 
 * @author dong
 * @version $Revision: 2088 $
 * @since 0.0.2
 */
public class StaticAssetUrlFactoryLocalImpl implements StaticAsseUrlFactory {
	private final Pattern SLASH_PATTERN = Pattern.compile("/+");
	private final Logger logger;

	public StaticAssetUrlFactoryLocalImpl(Logger logger) {
		this.logger = logger;
	}

	/**
	 * @see lichen.services.asset.StaticAsseUrlFactory#getUrl(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getUrl(String context, String path, String referPath) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("context:%s,path:%s,referPath:%s",
					context, path, referPath));
		}
		String absolutePath = "";
		// 当path中有..的时候,进行路径的解析
		if (path.indexOf("..") > -1 ) {
			String[] slashItems = SLASH_PATTERN.split(path);
			// 将referPath的目录项放到stack当中,最后一项认为是页面,不加入到路径堆栈中
			Stack<String> pathStack = new Stack<String>();
			if (!path.startsWith("/") && !InternalUtils.isBlank(referPath)) {
				//只有不以/开头的path,结合referPath进行解析
    			String[] referSlashItems = SLASH_PATTERN.split(referPath);
				for (int i = 0; i < referSlashItems.length - 1; i++) {
					pathStack.push(referSlashItems[i]);
				}
			}
			// 判断根据path的相对路径,对pathStack进行处理
			for (int i = 0; i < slashItems.length; i++) {
				String s = slashItems[i];
				if (s.equals("..")) {
					if (!pathStack.isEmpty()) {
						pathStack.pop();
					}
				} else if (s.equals(".")) {

				} else if (s.length() > 0) {
					pathStack.push(s);
				}
			}
			while (!pathStack.empty()) {
				absolutePath = pathStack.pop()
						+ (absolutePath.length() > 0 ? "/" : "") + absolutePath;
			}
		} else {
			absolutePath = path;
		}
		absolutePath = absolutePath.replaceFirst("^/+", "");
		String _ret = "/";
		if (InternalUtils.isNonBlank(context)) {
			if (context.startsWith("/")) {
				_ret = context + "/";
			} else {
				_ret += context + "/";
			}
		}
		_ret = _ret + absolutePath;
		return _ret;
	}
}
