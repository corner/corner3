package corner.config.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="config")
public class TestConfigModel {
	private String testElement1;
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
}
