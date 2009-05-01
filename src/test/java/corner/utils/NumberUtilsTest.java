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
package corner.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author user
 * @version $Revision$
 * @since 0.0.2
 */
public class NumberUtilsTest {

	@Test
	public void test_plus(){
		double m1=0.1;
		double m2=0.6;
		Assert.assertEquals(NumberUtils.plus(m1,m2),0.7);
	}
	@Test
	public void test_div(){
		double m1=0.1;
		double m2=0.6;
		Assert.assertEquals(NumberUtils.div(m1,m2,2),0.17);
	}
	
}
