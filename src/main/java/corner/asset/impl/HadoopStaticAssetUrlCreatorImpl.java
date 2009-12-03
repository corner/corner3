/* 
 * Copyright 2008 The Corner Team.
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
package corner.asset.impl;


import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import corner.asset.AssetConstants;
import corner.asset.StaticAssetUrlCreator;
import corner.hadoop.HadoopModule;


/**
 * 构造位于HDFS中的静态资源的URL
 * 
 * @author dong
 * @version $Revision: $
 * @since 0.0.2
 */
public class HadoopStaticAssetUrlCreatorImpl implements StaticAssetUrlCreator {
	private final String hadoopPath;

	public HadoopStaticAssetUrlCreatorImpl(@Inject @Symbol(HadoopModule.HADOOP_WEB_URL)String hadoopPath) {
		this.hadoopPath = hadoopPath;
	}

	/**
	 * @see corner.asset.impl.StaticAsseUrlFactory#getUrl(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String createUrl(String context,String protocol,String path,String referPath) {
		//仅仅针对hdfs类型的资源
		if(!AssetConstants.HDFS_ASSET_TYPE.equals(protocol)){
			return null;
		}
		return String.format("%s/%s", this.hadoopPath, path);
	}
}
