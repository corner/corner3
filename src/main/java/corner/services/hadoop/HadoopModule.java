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
package corner.services.hadoop;

import java.util.Map;


import org.apache.tapestry5.annotations.Service;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import corner.services.hadoop.impl.DistributedResourceComponentEventResultProcessor;
import corner.services.hadoop.impl.DistributedResourceEventResultProcessorWrapper;
import corner.services.hadoop.impl.GetFileServiceRegexClearImpl;
import corner.services.hadoop.impl.HadoopAccessorImpl;

/**
 * 针对Hadoop支持的module.
 * <p>
 * see http://hadoop.apache.org
 * </p>
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class HadoopModule {
	public final static String HADOOP_DFS_NAME_VAR = "corner.hadoop.dfs";
	public final static String HADOOP_WEB_URL = "corner.hadoop.web.url";
	/** Hadoop客户端建立Socket的连接超时* */
	public static final String HADOOP_CLIENT_SOCKETTIMEOUT = "corner.hadoop.sockettimeout";
	/** Hadoop客户端建立Socket的连接超时后的重次次数* */
	public static final String HADOOP_DFS_CLIENT_SOCKETTIMEOUT_MAXRETRIES = "corner.hadloop.sockettimeout.maxretries";
	/** Hadoop向Web返回响应时是否Cache* */
	public final static String HADOOP_WEB_CACHE = "corner.hadoop.web.cache";

	public static void bind(ServiceBinder binder) {
		binder
				.bind(DistributedResourceAccessor.class,
						HadoopAccessorImpl.class);
		binder.bind(GetFileService.class, GetFileServiceRegexClearImpl.class);

	}

	public static void contributeFactoryDefaults(
			MappedConfiguration<String, String> configuration) {
		configuration.add(HADOOP_DFS_NAME_VAR, "file:///");
		configuration.add(HADOOP_WEB_URL, "/corner/getfile");
		configuration.add(HADOOP_DFS_CLIENT_SOCKETTIMEOUT_MAXRETRIES, "0");
		configuration.add(HADOOP_CLIENT_SOCKETTIMEOUT, "1000");
		configuration.add(HADOOP_WEB_CACHE, "true");
		configuration.add(
				GetFileServiceRegexClearImpl.GETFILE_SERVICE_CLEARREGEX, "");
		// 采用HDFS作为文件存储
		// configuration.add(HADOOP_DFS_NAME_VAR,"hdfs://192.168.1.119:9000/");
		// 采用KFS作为后端的文件存储
		// configuration.add(HADOOP_DFS_NAME_VAR,"kfs://192.168.1.119:9000/");
	}

	/**
	 * 构建默认的Processor
	 * 
	 * @param response
	 * @param dfsAccessor
	 * @param request
	 * @param useCache
	 * @return
	 * @since 0.0.2
	 */
	public static ComponentEventResultProcessor<DistributedResource> buildDefaultDistributedResourceProcessor(
			@Inject
			Response response, @Inject
			DistributedResourceAccessor dfsAccessor, @Inject
			Request request, @Inject
			@Symbol(HADOOP_WEB_CACHE)
			boolean useCache) {
		return new DistributedResourceComponentEventResultProcessor(
				dfsAccessor, response, request, useCache);
	}

	/**
	 * 构建ComponentEventResultProcessor的适配器,以便于根据策略使用不同的HDFS服务
	 * 
	 * @param config
	 * @param logger
	 * @return
	 * @since 0.0.2
	 */
	public static ComponentEventResultProcessor<DistributedResource> buildDistributedResourceProcessorWrapper(
			Map<String, ComponentEventResultProcessor> config, Logger logger) {
		return new DistributedResourceEventResultProcessorWrapper(config, logger);
	}

	/**
	 * 向Adaptor中配置默认的Processor
	 * 
	 * @param defaultProcessor
	 * @param configuration
	 * @since 0.0.2
	 */
	public static void contributeDistributedResourceProcessorWrapper(
			@Inject
			@Service("DefaultDistributedResourceProcessor")
			ComponentEventResultProcessor<DistributedResource> defaultProcessor,
			MappedConfiguration<String, ComponentEventResultProcessor> configuration) {
		configuration.add(DistributedResourceEventResultProcessorWrapper.DEFAULT_PREFIX,
				defaultProcessor);
	}

	/**
	 * 注册针对Hadoop文件的读取Processor
	 */
	public static void contributeComponentEventResultProcessor(
			@Inject
			@Service("DistributedResourceProcessorWrapper")
			ComponentEventResultProcessor<DistributedResource> adaptor,
			MappedConfiguration<Class, ComponentEventResultProcessor> configuration) {
		configuration.add(DistributedResource.class, adaptor);
	}
}
