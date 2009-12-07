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
package corner.hadoop.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.FileCopyUtils;


/**
 * 用来本地文件的处理
 * 
 * @author jcai
 * @version $Revision$
 * @since 0.0.2
 */
public class LocalFileAccessorProxy implements AccessorProxy {

	private String path;

	public LocalFileAccessorProxy(String basePath) {
		URI uri;
		try {
			uri = new URI(basePath);
			this.path = uri.getPath();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @see corner.hadoop.services.DistributedResourceAccessor#getFile(java.lang.String,
	 *      java.io.OutputStream)
	 */
	@Override
	public void getFile(String filePath, OutputStream out) throws IOException {
		InputStream in = new FileInputStream(new File(addRootPath(filePath)));
		FileCopyUtils.copy(in, out);
	}

	/**
	 * @see corner.hadoop.services.DistributedResourceAccessor#getFileMTTime(java.lang.String)
	 */
	@Override
	public long getFileMTTime(String filePath) throws IOException {
		File file = new File(addRootPath(filePath));
		return file.lastModified();
	}

	/**
	 * @see corner.hadoop.services.DistributedResourceAccessor#putFile(java.lang.String,
	 *      java.io.InputStream)
	 */
	@Override
	public void putFile(String filePath, InputStream is) throws IOException {
		File file = new File(addRootPath(filePath));
		file.getParentFile().mkdirs();
		OutputStream out = new FileOutputStream(file);
		FileCopyUtils.copy(is, out);
	}

	@Override
	public boolean deleteFile(String filePath) throws IOException {
		File file = new File(addRootPath(filePath));
		return file.delete();
	}

	@Override
	public List<FileDesc> list(String dirPath) throws IOException {
		String _path = addRootPath(dirPath);
		if (dirPath.endsWith(File.separator)) {
			_path = dirPath.substring(0, dirPath.length() - 1);
		}
		File dir = new File(_path);
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("The path [" + dirPath
					+ "] is not dir.");
		}
		File[] files = dir.listFiles();
		if (files != null && files.length > 0) {
			List<FileDesc> ret = new LinkedList<FileDesc>();
			for (File file : files) {
				ret.add(new FileDesc(_path + File.separator + file.getName(),
						file.isDirectory(), new Timestamp(file.lastModified()),
						file.length()));
			}
			return ret;
		}
		return null;
	}

	@Override
	public boolean mkdirs(String dirPath) throws IOException {
		File dir = new File(addRootPath(dirPath));
		return dir.mkdirs();
	}

	@Override
	public boolean isFileExist(String filePath) throws IOException {
		File file = new File(addRootPath(filePath));
		return file.exists();
	}

	@Override
	public FileDesc getFileDesc(String filePath) throws IOException {
		File file = new File(addRootPath(filePath));
		if (file.exists()) {
			return new FileDesc(file.getAbsolutePath(), file.isDirectory(),
					new Timestamp(file.lastModified()), file.length());
		} else {
			return null;
		}
	}

	/**
	 * 增加根目录
	 * @param filePath
	 * @return
	 * @since 0.0.2
	 */
	private String addRootPath(String filePath) {
		if (path != null && path.length() > 0) {
			return path + File.separator + filePath;
		} else {
			return filePath;
		}
	}
}
