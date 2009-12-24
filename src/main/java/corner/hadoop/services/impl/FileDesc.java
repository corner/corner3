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

import java.sql.Timestamp;

/**
 * 文件的描述
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class FileDesc implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 文件的路径 * */
	private final String path;
	/** 是否是目录 * */
	private final boolean isDir;
	/** 修改时间 * */
	private final Timestamp modifyTime;
	/** 长度* */
	private final long length;

	public FileDesc(String path, boolean isDir, Timestamp modifyTime,
			long length) {
		this.path = path;
		this.isDir = isDir;
		this.modifyTime = modifyTime;
		this.length = length;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the isDir
	 */
	public boolean isDir() {
		return isDir;
	}

	/**
	 * @return the modifyTime
	 */
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

}
