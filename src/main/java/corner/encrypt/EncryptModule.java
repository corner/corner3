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
package corner.encrypt;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import corner.encrypt.annotation.Des;
import corner.encrypt.services.EncryptService;
import corner.encrypt.services.impl.DESedeEncryptServiceImpl;
import corner.encrypt.services.impl.MD5EncryptServiceImpl;
import corner.tapestry.captcha.CipherKey;

/**
 * 用于加密的模块
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class EncryptModule {

	public static void bind(ServiceBinder binder){
		binder.bind(EncryptService.class,MD5EncryptServiceImpl.class);
		binder.bind(EncryptService.class,DESedeEncryptServiceImpl.class);
	}


	@Des
	public EncryptService buildDESedeEncryptService(
			@Inject @Symbol(EncryptSymbols.CIPHER_FILE) String cipherKeyPath) {
		return new DESedeEncryptServiceImpl(new CipherKey(cipherKeyPath, 24));
	}


}
