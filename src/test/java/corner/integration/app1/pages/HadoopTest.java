package corner.integration.app1.pages;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

import corner.services.hadoop.DistributedResource;
import corner.services.hadoop.DistributedResourceAccessor;

public class HadoopTest {
	private static final String REMOTE_FILE_PATH="target/a/b/c/logo.jpg";
	@Inject
	private DistributedResourceAccessor accessor;

	//向Hadoop中添加数据
	void onActionFromPutFile() throws IOException{
		InputStream is = HadoopTest.class.getResourceAsStream("/test-data/logo-Ubuntu.png");
		accessor.putFile(REMOTE_FILE_PATH,is);
		
	}
	//从hadoop中获取数据
	DistributedResource onActionFromGetFile(){
		
		return new DistributedResource(){

			@Override
			public String getContentType() {
				return "image/jpeg";
			}

			@Override
			public String getFilePath() {
				return REMOTE_FILE_PATH;
			}

			@Override
			public void prepareResponse(Response response) {
			}};
	}
}
