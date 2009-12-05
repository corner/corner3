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
package corner.captcha;

import java.io.FileOutputStream;

import org.junit.Test;

import corner.tapestry.captcha.CaptchaImage;
import corner.tapestry.captcha.CaptchaJhlabsImageImpl;


public class CaptchaJhlabsImageImplTest {

	@Test
	public void testConverString2Image() throws Exception {
		CaptchaImage ca = new CaptchaJhlabsImageImpl(100,50,30,0,35);
		for (int i = 0; i < 10; i++) {
			byte[] data = ca.converString2Image("abcd");
			new FileOutputStream("capta"+i+".jpg").write(data);
		}

	}

}
