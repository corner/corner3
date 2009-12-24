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
package corner.bindings;

import java.text.Format;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.tapestry.bindings.BindingModule;

/**
 * 
 * @author Jun Tsai
 * @version $Revision$
 * @since 0.0.2
 */
public class FormatterModuleTest {

	@Test
	public void test_number_format(){
		Format format = BindingModule.buildCurrencyFormat();
		Assert.assertEquals(format.format(0.1),"0.10");
		Assert.assertEquals(format.format(110.1),"110.10");
		Assert.assertEquals(format.format(11210.1),"11210.10");
		Assert.assertEquals(format.format(11210.123),"11210.12");
	}
}
