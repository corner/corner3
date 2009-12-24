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
package corner.encrypt.services;

/**
 *  加密的接口
 * @author dong
 * @version $Revision$
 * @since 0.2
 */
public interface EncryptService {
	/**
	 *  用密钥key加密指定的内容src
	 * @param src 将被加密的内容
	 * @param key 用于加密的密钥
	 * @return 经过加密的数据
	 */
	public byte[] encrypt(byte[] src, byte[] key);

	/**
	 *  用密钥key解密指定的内容src
	 * @param src 加密的数据
	 * @param key 用于解密的密钥
	 * @return 经过解决的数据
	 */
	public byte[] decrypt(byte[] src, byte[] key);

	/**
	 * 
	 * @param src
	 * @return
	 */
	public String encrypt(String src);

	/**
	 * 
	 * @param cryptograph
	 * @return
	 */
	public String decrypt(String cryptograph);
}
