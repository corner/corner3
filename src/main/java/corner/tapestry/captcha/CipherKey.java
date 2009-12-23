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
package corner.tapestry.captcha;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.springframework.util.FileCopyUtils;

/**
 * 密钥工具类
 * 
 * @author dong
 * @version $Revision$
 * @since 0.2
 */
public class CipherKey implements Serializable {
	public static final String CIPHER_CHARSET = "ISO8859-1";

	private static final long serialVersionUID = 1L;

	private volatile byte[] cipher;

	private final int cipherLen;

	private final String persisFile;

	/**
	 * 从密钥文件中生成CiperKey
	 * 
	 * @param persisFile
	 *            存放密钥的文件路径
	 * @param cipherLen
	 *            密钥的字节长度
	 */
	public CipherKey(final String persisFile, final int cipherLen) {
		if (persisFile == null) {
			throw new IllegalStateException("persisFile is null");
		}
		if (cipherLen <= 0) {
			throw new IllegalStateException("cipherLen must be greater than zero");
		}

		this.persisFile = persisFile;
		this.cipherLen = cipherLen;
		recoverCipher();
		if (cipher == null || cipher.length < this.cipherLen) {
			createRandomCipher();
		}
	}

	public byte[] getCipher() {
		return cipher.clone();
	}

	public void recoverCipher() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(persisFile);
			ByteArrayOutputStream bou = new ByteArrayOutputStream();
			FileCopyUtils.copy(fis, bou);
			bou.close();
			if (bou.size() > 0) {
				byte[] persistBytes = bou.toByteArray();
				cipher = Base64.decodeBase64(persistBytes);
			}
		} catch (Throwable e) {
			System.err.println("Exception during deserialization:" + e);
		} finally {
			InternalUtils.close(fis);
		}
	}

	public void persistCipher() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(persisFile);
			fos.write(Base64.encodeBase64(this.cipher));
			fos.flush();
		} catch (Throwable e) {
			System.err.println("Exception during serialization:" + e);
		} finally {
			InternalUtils.close(fos);
		}
	}

	public void createRandomCipher() {
		SecureRandom secureRandom = new SecureRandom();
		Random random = new Random(secureRandom.nextLong());
		cipher = new byte[cipherLen];
		random.nextBytes(cipher);
		persistCipher();
	}
}
