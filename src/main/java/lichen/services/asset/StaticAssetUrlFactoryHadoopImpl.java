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

import lichen.services.hadoop.HadoopModule;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;


/**
 * 构造位于HDFS中的静态资源的URL
 * 
 * @author dong
 * @version $Revision: $
 * @since 0.0.2
 */
public class StaticAssetUrlFactoryHadoopImpl implements StaticAsseUrlFactory {
	private final String hadoopPath;

	public StaticAssetUrlFactoryHadoopImpl(@Inject @Symbol(HadoopModule.HADOOP_WEB_URL)String hadoopPath) {
		this.hadoopPath = hadoopPath;
	}

	/**
	 * @see lichen.services.asset.StaticAsseUrlFactory#getUrl(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getUrl(String context, String path, String referPath) {
		return String.format("%s/%s", this.hadoopPath, path);
	}
}
