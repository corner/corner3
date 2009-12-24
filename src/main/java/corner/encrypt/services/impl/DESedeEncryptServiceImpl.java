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

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import corner.encrypt.services.EncryptService;
import corner.tapestry.captcha.CipherKey;

/**
 * 使用DEsede加密算法实现的EncryptService
 * 
 * @author dong
 * @version $Revision$
 * @since 0.2
 */
public class DESedeEncryptServiceImpl implements EncryptService {
	private static final String CHIPERH_CHARSET = "UTF-8";
	public final static String Algorithm = "DESede";

//	static {
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//	}

	private final CipherKey cipher;

	public DESedeEncryptServiceImpl(final CipherKey cipher) {
		if (cipher == null) {
			throw new IllegalArgumentException("The cipher must not be null.");
		}
		this.cipher = cipher;
	}

	public byte[] encrypt(byte[] src, byte[] key) {
		try {
			SecretKey deskey = new SecretKeySpec(key, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public byte[] decrypt(byte[] src, byte[] keybyte) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public String encrypt(final String src) {
		if (src == null)
			return null;
		try {
			// 加密
			byte[] encoded = encrypt(src.getBytes(CHIPERH_CHARSET), cipher
					.getCipher());

			// 编码
			return  new String(Hex.encodeHex(encoded));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(final String cryptograph) {
		if (cryptograph == null)
			return null;
		try {
			// 解码
			byte[] decoded = Hex.decodeHex(cryptograph.toCharArray());
			// cryptograph.getBytes(CHIPERH_CHARSET);

			// 解密
			byte[] srcBytes = decrypt(decoded, cipher.getCipher());

			if (srcBytes == null)
				return null;

			return new String(srcBytes, CHIPERH_CHARSET);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

}
