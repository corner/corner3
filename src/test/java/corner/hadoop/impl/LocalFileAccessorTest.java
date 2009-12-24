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

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.Test;

import corner.hadoop.services.impl.LocalFileAccessorProxy;

public class LocalFileAccessorTest {
	@Test
	public void test_local() throws FileNotFoundException, IOException {
		LocalFileAccessorProxy accessor = new LocalFileAccessorProxy("target");
		File file = new File("pom.xml");
		accessor.putFile("a/b/c/test.xml", new FileInputStream(file));
		assertTrue(accessor.deleteFile("a/b/c/test.xml"));
	}
}
