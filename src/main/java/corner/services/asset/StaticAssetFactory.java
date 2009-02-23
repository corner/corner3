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
package corner.services.asset;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.Request;

/**
 * 提供static类型的Asset工厂实现
 * 
 * @author dong
 * @version $Revision: 3031 $
 * @since 0.0.1
 */
public class StaticAssetFactory implements AssetFactory {
	private final String context;
	private final StaticAsseUrlFactory urlFactory;

	/**
	 * 构建StaticAssetFactory实例
	 * 
	 * @param context
	 *            应用的上下文路径,必须的
	 *            可以通过org.apache.tapestry5.services.Request.getContext得到;
	 * @param urlFactory
	 *            静态资源的Url工厂实现,必须的
	 */
	public StaticAssetFactory(Request request, StaticAsseUrlFactory urlFactory) {
		if (request == null || urlFactory == null) {
			throw new IllegalArgumentException(
					"The request and urlFactory are both excepted.");
		}
		this.context = request.getContextPath();
		this.urlFactory = urlFactory;
	}

	/**
	 * @see org.apache.tapestry5.services.AssetFactory#createAsset(org.apache.tapestry5.ioc.Resource)
	 */
	public Asset createAsset(final Resource resource) {
		//getUrl的referPath参数为空,因为对于asset来说,它们的资源总是相对于应用的context路径,与请求的页面无关
		final String url = this.urlFactory.getUrl(context, resource.getPath(),"");
		return new Asset() {
			public Resource getResource() {
				return resource;
			}

			public String toClientURL() {
				return url;
			}
			@Override
			public String toString() {
				return toClientURL();
			}
		};
	}

	/**
	 * @see org.apache.tapestry5.services.AssetFactory#getRootResource()
	 */
	public Resource getRootResource() {
		return new StaticResource(this.context != null ? this.context : "");
	}
}
