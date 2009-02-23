package corner.services.hadoop.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.Test;

import corner.services.hadoop.impl.LocalFileAccessorProxy;
import static org.testng.Assert.*;

public class LocalFileAccessorTest {
	@Test
	public void test_local() throws FileNotFoundException, IOException {
		LocalFileAccessorProxy accessor = new LocalFileAccessorProxy("target");
		File file = new File("pom.xml");
		accessor.putFile("a/b/c/test.xml", new FileInputStream(file));
		assertTrue(accessor.deleteFile("a/b/c/test.xml"));
	}
}
