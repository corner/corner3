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
package corner.hadoop.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.hadoop.services.DistributedResourceAccessor;
import corner.hadoop.services.impl.FileDesc;
import corner.hadoop.services.impl.HadoopAccessorImpl;

public class HadoopAccessorTest {

	@Test
	public void test_put() throws IOException {
		try {
			DistributedResourceAccessor accessor = new HadoopAccessorImpl(null,
					0, 0);
			String content = "content";

			OutputStream out = new ByteArrayOutputStream();
			try {
				accessor.getFile("target/my.pom", out);
				Assert.assertTrue(false);
			} catch (java.io.FileNotFoundException e) {

			}

			InputStream is = new ByteArrayInputStream(content.getBytes());
			accessor.putFile("target/my.pom", is);

			out = new ByteArrayOutputStream();
			accessor.getFile("target/my.pom", out);
			Assert.assertEquals(out.toString(), content);
			Assert.assertTrue(accessor.deleteFile("target/my.pom"));
		} catch (Throwable te) {
			te.printStackTrace();
		}
	}

	// @Test
	public void test_gethdfs() throws IOException {
		// 测试产生socket timeout时的问题,11.0.0.88:9000应该是一个不存在的主机地址
		try {
			DistributedResourceAccessor accessor = new HadoopAccessorImpl(
					"hdfs://11.0.0.88:9000/", 0, 1000);
			OutputStream out = new ByteArrayOutputStream();
			accessor.getFile("upload/1", out);
		} catch (Throwable te) {
			te.printStackTrace();
		}
	}

//	@Test
	public void test_hdfs_list() throws IOException {
		try {
			DistributedResourceAccessor accessor = new HadoopAccessorImpl(null,
					0, 0);
			List<FileDesc> ret = accessor.list("target");
			Assert.assertNotNull(ret);
			for (FileDesc fileDesc : ret) {
				System.out.println(String.format(
						"name:%s,isDir:%s,length:%s,last modify:%s", fileDesc
								.getPath(), fileDesc.isDir(), fileDesc
								.getLength(), fileDesc.getModifyTime()));

			}
		} catch (Throwable te) {
			te.printStackTrace();
		}

		try {
			DistributedResourceAccessor accessor = new HadoopAccessorImpl(null,
					0, 0);
			List<FileDesc> ret = accessor
					.list("target/corner-0.0.2-SNAPSHOT.jar");
			Assert.assertNotNull(ret);
			for (FileDesc fileDesc : ret) {
				System.out.println(String.format(
						"name:%s,isDir:%s,length:%s,last modify:%s", fileDesc
								.getPath(), fileDesc.isDir(), fileDesc
								.getLength(), fileDesc.getModifyTime()));

			}
		} catch (Throwable te) {
			te.printStackTrace();
		}

		try {
			DistributedResourceAccessor accessor = new HadoopAccessorImpl(
					"hdfs://192.168.0.7:9000", 1, 1);
			List<FileDesc> ret = accessor.list("/user/dong/");
			Assert.assertNotNull(ret);
			for (FileDesc fileDesc : ret) {
				System.out.println(String.format(
						"name:%s,isDir:%s,length:%s,last modify:%s", fileDesc
								.getPath(), fileDesc.isDir(), fileDesc
								.getLength(), fileDesc.getModifyTime()));

			}
		} catch (Throwable te) {
			te.printStackTrace();
		}

	}
}
