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
package corner.services.migration.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件操作的utils类.
 * 
 * @version $Revision: 2250 $
 * @since 0.9.0
 */
public class FileUtils {

	/** 匹配路径的正则表达式 * */
	public final static String GROUP_BASE_DIR_REG_PATTERN = "([^$]+)[\\\\|/]([^\\\\|/]+)$";

	/**
	 * 对给定的路径进行得到文件集合
	 * 
	 * @param filePath
	 *            还有正则表达式的路径.
	 * @return 文件的集合.
	 */
	public static FileCollection dir(String filePath) {

		// 通过正则表达式来得到文件的基础路径以及文件名.
		Pattern pattern = Pattern.compile(GROUP_BASE_DIR_REG_PATTERN);
		Matcher matcher = pattern.matcher(filePath);

		String baseDir = null;
		String filePattern = null;
		if (matcher.find()) {
			baseDir = matcher.group(1);
			filePattern = matcher.group(2);
		} else {
			baseDir = ".";
			filePattern = filePath;
		}

		File dir = new File(baseDir);
		File[] files = dir.listFiles(new RegexpFileFilter(filePattern));
		return new FileCollection(files, new DefaultSortCallback(filePattern));
	}

	/**
	 * 对文件通过正则表达式进行过滤
	 * 
	 * @author <a href="mailto:jun.tsai@ouriba.com">Jun Tsai</a>
	 * @version $Revision: 2250 $
	 * @since 0.9.0
	 */
	static class RegexpFileFilter implements FileFilter {
		private String fileNamePattern;

		RegexpFileFilter(String fileNamePattern) {
			this.fileNamePattern = fileNamePattern;
		}

		/**
		 * 
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		public boolean accept(File pathname) {
			return pathname.getName().matches(fileNamePattern);
		}
	}

	/**
	 * 文件集合
	 * 
	 * @author <a href="mailto:jun.tsai@ouriba.com">Jun Tsai</a>
	 * @version $Revision: 2250 $
	 * @since 0.9.0
	 */
	public static class FileCollection {

		private File[] files;
		private ISortCallback defaultCallback;

		FileCollection(File[] files, ISortCallback defaultCallback) {
			this.files = files;
			this.defaultCallback = defaultCallback;
		}

		/**
		 * 通过给定的排序的callback，来进行排序
		 * 
		 * @param callback
		 * @return
		 */
		public FileCollection sort(final ISortCallback callback) {
			return this.sort(callback, true);
		}

		private FileCollection sort(final ISortCallback callback,
				final boolean flag) {
			Comparator<File> c = new Comparator<File>() {
				public int compare(File o1, File o2) {
					int obj1 = callback.getSortData(o1);
					int obj2 = callback.getSortData(o2);
					return flag ? obj1 - obj2 : obj2 - obj1;
				}
			};
			//fix 避免空指针异常
			if (files != null) {
				Arrays.sort(files, c);
			}
			return this;
		}

		/**
		 * 正序排列
		 * 
		 * @return 文件集合
		 */
		public FileCollection sort() {
			return sort(defaultCallback, true);
		}

		/**
		 * 倒序排列
		 * 
		 * @return 文件集合
		 */
		public FileCollection reverse() {
			return sort(defaultCallback, false);
		}

		public FileCollection reverse(ISortCallback callback) {
			return sort(defaultCallback, false);
		}

		public File[] getFiles() {
			return this.files;
		}

	}

	/**
	 * 默认对文件进行排序的类
	 * 
	 * @author <a href="mailto:jun.tsai@ouriba.com">Jun Tsai</a>
	 * @version $Revision: 2250 $
	 * @since 0.9.0
	 */
	static class DefaultSortCallback implements ISortCallback {

		private String filePattern;

		public DefaultSortCallback(String filePattern) {
			this.filePattern = filePattern;
		}

		/**
		 * @see com.ouriba.piano.utils.FileUtils.ISortCallback#getSortData(java.io.File)
		 */
		public int getSortData(File file) {
			Matcher matcher = Pattern.compile(filePattern).matcher(
					file.getName());
			if (matcher.find()) {
				return Integer.parseInt(matcher.group(1));
			}
			return -1;
		}

	}

	/**
	 * 对排序进行回掉的类.
	 * 
	 * @author <a href="mailto:jun.tsai@ouriba.com">Jun Tsai</a>
	 * @version $Revision: 2250 $
	 * @since 0.9.0
	 */
	public interface ISortCallback {
		public int getSortData(File file);
	}
}
