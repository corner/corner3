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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import corner.services.hadoop.FileDesc;


/**
 * 用来对各种访问形式进行代理
 * 
 * @author jcai
 * @version $Revision$
 * @since 0.0.2
 */
public interface AccessorProxy {

	/**
	 * @see corner.services.hadoop.DistributedResourceAccessor#getFile(java.lang.String,
	 *      java.io.OutputStream)
	 */
	public void getFile(String filePath, OutputStream out) throws IOException;

	public long getFileMTTime(String filePath) throws IOException;

	/**
	 * @see corner.services.hadoop.DistributedResourceAccessor#putFile(java.lang.String,
	 *      java.io.InputStream)
	 */
	public void putFile(String filePath, InputStream is) throws IOException;

	/**
	 * @see corner.services.hadoop.DistributedResourceAccessor#deleteFile(java.lang.String)
	 */
	public abstract boolean deleteFile(String filePath) throws IOException;

	/**
	 * 列出指定路径path下的文件列表
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @since 0.0.2
	 */
	public List<FileDesc> list(String path) throws IOException;

	/**
	 * 
	 * @param dirPath
	 * @return
	 * @since 0.0.2
	 */
	public boolean mkdirs(String dirPath) throws IOException;

	/**
	 * 检查文件是否存在
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @since 0.0.2
	 */
	public boolean isFileExist(String filePath) throws IOException;
	
	/**
	 * 取得文件的描述信息
	 * @param filePath 
	 * @return
	 * @throws IOException
	 * @since 0.0.2
	 */
	public FileDesc getFileDesc(String filePath) throws IOException;
}
