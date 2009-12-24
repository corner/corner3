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
package corner.encrypt.services.impl;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.ioc.annotations.Marker;

import corner.encrypt.annotations.Md5;
import corner.encrypt.services.EncryptService;

/**
 * md5的加密方式
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
@Marker(Md5.class)
public class MD5EncryptServiceImpl implements EncryptService {

	/**
	 * @see corner.encrypt.EncryptService#decrypt(byte[], byte[])
	 */
	@Override
	public byte[] decrypt(byte[] src, byte[] key) {
		throw new UnsupportedOperationException("Can't use md5 to decrypt");
	}

	/**
	 * @see corner.encrypt.EncryptService#decrypt(java.lang.String)
	 */
	@Override
	public String decrypt(String cryptograph) {
		throw new UnsupportedOperationException("Can't use md5 to decrypt");
	}

	/**
	 *@param src 要被加密的字节 
	 *@param key 密钥,在MD5中不使用
	 * @see corner.encrypt.EncryptService#encrypt(byte[], byte[])
	 */
	@Override
	public byte[] encrypt(byte[] src, byte[] key) {
		return DigestUtils.md5(src);
	}

	/**
	 * @see corner.encrypt.EncryptService#encrypt(java.lang.String)
	 */
	@Override
	public String encrypt(String src) {
		return DigestUtils.md5Hex(src);
	}

}
