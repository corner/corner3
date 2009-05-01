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
package corner.services.hadoop.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;

import corner.services.hadoop.DistributedResourceAccessor;
import corner.services.hadoop.FileDesc;
import corner.services.hadoop.HadoopModule;

/**
 * 针对Hadoop进行访问的服务类
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public class HadoopAccessorImpl implements DistributedResourceAccessor {
	private final String hadoopDfsName;
	private String protocol;
	private final Configuration configuration = new Configuration();
	private AccessorProxy proxy;

	/**
	 * 
	 * @param hadoopDfsName
	 * @param clientSocketTimeoutMaxRetries
	 *            Hadoop客户端建立Socket的连接超时后的重次次数
	 * @param clientSockettimeout
	 *            Hadoop客户端建立Socket的连接超时,单位毫秒
	 */
	public HadoopAccessorImpl(@Inject
	@Symbol(HadoopModule.HADOOP_DFS_NAME_VAR)
	String hadoopDfsName, @Inject
	@Symbol(HadoopModule.HADOOP_DFS_CLIENT_SOCKETTIMEOUT_MAXRETRIES)
	int clientSocketTimeoutMaxRetries, @Inject
	@Symbol(HadoopModule.HADOOP_CLIENT_SOCKETTIMEOUT)
	int clientSockettimeout) {
		this.hadoopDfsName = hadoopDfsName;
		String _protocol = null;
		if (InternalUtils.isNonBlank(this.hadoopDfsName)) {
			configuration.setInt("ipc.client.socket.timeout",
					clientSockettimeout);
			configuration.setInt("ipc.client.socket.timeout.max.retries",
					clientSocketTimeoutMaxRetries);
			configuration.setInt("ipc.client.connect.max.retries", 0);
			configuration.set("fs.default.name", hadoopDfsName);
			int i = this.hadoopDfsName.indexOf(":");
			if (i > 0) {
				_protocol = this.hadoopDfsName.substring(0, i);
			}
		}
		this.protocol = _protocol;
		if ("file".equalsIgnoreCase(_protocol)) {// 文件形式
			this.proxy = new LocalFileAccessorProxy(hadoopDfsName);
			// 保证在StaticLink中从文件中读取
			this.protocol = "hdfs";
		} else {
			this.proxy = new HdfsAccessorProxy(this.getConf());
		}
	}

	// 得到Hadoop的配置
	private Configuration getConf() {
		return configuration;
	}

	@Override
	public String getProtocol() {
		return this.protocol;
	}

	public void getFile(String filePath, OutputStream out) throws IOException {
		proxy.getFile(filePath, out);
	}

	public long getFileMTTime(String filePath) throws IOException {
		return proxy.getFileMTTime(filePath);
	}

	public void putFile(String filePath, InputStream is) throws IOException {
		proxy.putFile(filePath, is);
	}

	@Override
	public boolean deleteFile(String filePath) throws IOException {
		return proxy.deleteFile(filePath);
	}

	@Override
	public List<FileDesc> list(String path) throws IOException {
		return proxy.list(path);
	}

	@Override
	public boolean mkdirs(String dirPath) throws IOException {
		return proxy.mkdirs(dirPath);
	}

	@Override
	public boolean isFileExist(String filePath) throws IOException {
		return proxy.isFileExist(filePath);
	}

	@Override
	public FileDesc getFileDesc(String filePath) throws IOException {
		return proxy.getFileDesc(filePath);
	}
}
