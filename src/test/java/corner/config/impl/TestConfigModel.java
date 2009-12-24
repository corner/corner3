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
package corner.config.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import corner.config.services.ConfigInitable;

@XmlRootElement(name="config")
public class TestConfigModel implements ConfigInitable{
	private String testElement1;
	private String initValue;
	@XmlElement(name="e1")
	public String getTestElement1() {
		return testElement1;
	}
	public void setTestElement1(String testElement1) {
		this.testElement1 = testElement1;
	}
	@XmlElement(name="e2")
	public int getTestElement2() {
		return testElement2;
	}
	public void setTestElement2(int testElement2) {
		this.testElement2 = testElement2;
	}
	private int testElement2;
	@Override
	public void init() {
		this.initValue = "initValue";
	}
	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}
	public String getInitValue() {
		return initValue;
	} 
}
