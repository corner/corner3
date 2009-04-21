package corner.services.hadoop.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


import org.testng.Assert;
import org.testng.annotations.Test;

import corner.services.hadoop.DistributedResourceAccessor;
import corner.services.hadoop.FileDesc;
import corner.services.hadoop.impl.HadoopAccessorImpl;

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
