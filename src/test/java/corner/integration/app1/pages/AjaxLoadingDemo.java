package corner.integration.app1.pages;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ioc.annotations.Inject;


public class AjaxLoadingDemo {

	@Inject
	private Block blockContent;
	public Object onActionFromTestAjax(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return blockContent;
	}
}
