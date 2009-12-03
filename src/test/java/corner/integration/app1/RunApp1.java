package corner.integration.app1;

import java.io.File;

import org.apache.tapestry5.test.JettyRunner;

public class RunApp1 {

	/**
	 * @param args
	 * @since 0.0.2
	 */
	public static void main(String[] args) {
		JettyRunner runner = new JettyRunner(new File("."), "/", 8888, "src/test/resources/app1", "localhost");
		runner.getClass();
	}

}
