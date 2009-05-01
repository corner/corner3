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
package corner.bindings;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.test.TapestryTestCase;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 3018 $
 * @since 0.0.2
 */

public class FormatBindingFactoryTest extends TapestryTestCase {
	@Test
	public void testFactory(){
		ComponentResources resource = this.mockComponentResources();
		EasyMock.expect(resource.getCompleteId()).andReturn("id");
		this.replay();
		Map<String, Format> configuration=new HashMap<String,Format>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		configuration.put("date",formatter);
		FormatterSource formatterSource = new FormatterSourceImpl(configuration);
		FormatBindingFactory factory = new FormatBindingFactory(formatterSource);
		Binding binding = factory.newBinding("format",resource, resource, "date", null);
		Assert.assertNotNull(binding);
		Assert.assertEquals(binding.toString(),"ForamtBinding[format id(date)]");
		Assert.assertEquals(binding.get(),formatter);
		this.verify();
	}
}
