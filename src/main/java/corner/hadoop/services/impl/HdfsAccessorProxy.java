/* 
 * Copyright 2009 The Corner Team.
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
package corner.hadoop.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


/**
 * 针对Hdfs文件访问的代理类
 * 
 * @author jcai
 * @version $Revision$
 * @since 0.0.2
 */
public class HdfsAccessorProxy implements AccessorProxy {
	private Configuration conf;

	public HdfsAccessorProxy(Configuration conf) {
		this.conf = conf;
	}

	/**
	 * @see corner.hadoop.services.DistributedResourceAccessor#getFile(java.lang.String,
	 *      java.io.OutputStream)
	 */
	@Override
	public void getFile(String filePath, OutputStream out) throws IOException {
		Path srcPath = new Path(filePath);
		FileSystem srcFS = srcPath.getFileSystem(getConf());
		InputStream in = srcFS.open(srcPath);
		IOUtils.copyBytes(in, out, getConf());
	}

	Configuration getConf() {
		return this.conf;
	}

	/**
	 * @see corner.hadoop.services.impl.AccessorProxy#getFileMTTime(java.lang.String)
	 */
	@Override
	public long getFileMTTime(String filePath) throws IOException {
		Path srcPath = new Path(filePath);
		FileSystem srcFS = srcPath.getFileSystem(getConf());
		if (srcFS != null) {
			return srcFS.getFileStatus(srcPath).getModificationTime();
		} else {
			return -1;
		}
	}

	/**
	 * @see corner.hadoop.services.DistributedResourceAccessor#putFile(java.lang.String,
	 *      java.io.InputStream)
	 */
	@Override
	public void putFile(String filePath, InputStream is) throws IOException {
		Path dstPath = new Path(filePath);
		FileSystem dstFs = dstPath.getFileSystem(getConf());
		FSDataOutputStream out = dstFs.create(dstPath);
		try {
			IOUtils.copyBytes(is, out, getConf(), false);
		} finally {
			out.close();
		}
	}

	/**
	 * 
	 * @see corner.hadoop.services.impl.AccessorProxy#deleteFile(java.lang.String)
	 */
	@Override
	public boolean deleteFile(String filePath) throws IOException {
		Path dstPath = new Path(filePath);
		FileSystem dstFs = dstPath.getFileSystem(getConf());
		return dstFs.delete(dstPath, false);
	}

	/**
	 * @see corner.hadoop.services.impl.AccessorProxy#list(java.lang.String)
	 */
	@Override
	public List<FileDesc> list(final String path) throws IOException {
		String _path = path;
		if (path.endsWith("/")) {
			_path = path.substring(0, path.length() - 1);
		}
		Path dstPath = new Path(_path);
		FileSystem dstFs = dstPath.getFileSystem(getConf());
		FileStatus _dstStatus = dstFs.getFileStatus(dstPath);
		if (_dstStatus == null) {
			throw new IllegalArgumentException("The path [" + path
					+ "] dose not exist.");
		}
		if (!_dstStatus.isDir()) {
			throw new IllegalArgumentException("The path [" + path
					+ "] is not dir.");
		}
		FileStatus[] fileStatus = dstFs.listStatus(dstPath);
		if (fileStatus != null && fileStatus.length > 0) {
			List<FileDesc> ret = new LinkedList<FileDesc>();
			for (FileStatus status : fileStatus) {
				ret.add(new FileDesc(_path + "/" + status.getPath().getName(),
						status.isDir(), new Timestamp(status
								.getModificationTime()), status.getLen()));
			}
			return ret;
		}
		return null;
	}

	@Override
	public boolean mkdirs(String dirPath) throws IOException {
		Path dstPath = new Path(dirPath);
		FileSystem dstFs = dstPath.getFileSystem(getConf());
		return dstFs.mkdirs(dstPath);
	}

	@Override
	public boolean isFileExist(String filePath) throws IOException {
		Path dstPath = new Path(filePath);
		FileSystem dstFs = dstPath.getFileSystem(getConf());
		return dstFs.exists(dstPath);
	}

	@Override
	public FileDesc getFileDesc(String filePath) throws IOException {
		Path srcPath = new Path(filePath);
		FileSystem srcFS = srcPath.getFileSystem(getConf());
		FileStatus _status = srcFS.getFileStatus(srcPath);
		if (_status != null) {
			return new FileDesc(filePath, _status.isDir(), new Timestamp(
					_status.getModificationTime()), _status.getLen());
		} else {
			return null;
		}
	}
}
