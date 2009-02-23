/* 
 * Copyright 2008 The Lichen Team.
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
package corner.services.captcha.impl;

import java.util.Random;

import corner.services.EncryptService;
import corner.services.captcha.CaptchaCode;




/**
 * 用于生成ASCII码的验证码,生成内容为A-Z,0-9随机生成,为了便于识别,去掉了I和1,O和0
 * 
 * @author dong
 * @version $Revision: 2089 $
 * @since 0.2
 */
public class ASCIICaptchaCodeImpl implements CaptchaCode {

	private final static String[] ascii = { "A", "B", "C", "D", "E", "F", "G",
			"H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "2", "3", "4", "5", "6", "7", "8", "9" };

	private final EncryptService crypt;
	private final int codeLength;

	/**
	 *  
	 * @param crypt
	 * @param codeLength
	 */
	public ASCIICaptchaCodeImpl(final EncryptService crypt,final int codeLength) {
		if(codeLength<=0 && codeLength>10){
			throw new IllegalArgumentException("The code length must be between 1 and 10");
		}
		this.crypt = crypt;
		this.codeLength = codeLength;
	}

	public String getChallengeCode() {
		final int size = ascii.length - 1;
		final Random ran = new Random();
		final StringBuffer buf = new StringBuffer();
		int num = 0;
		for (int i = 0; i < codeLength; i++) {
			num = ran.nextInt(size);
			buf.append(ascii[num]);
		}
		buf.append(System.currentTimeMillis());
		return crypt.encrypt(buf.toString());
	}
	
	public String[] getTextChallengeCode(String encrypt){
		if(encrypt == null){
			throw new IllegalArgumentException("No encrypt");
		}
		String dencrypt = crypt.decrypt(encrypt);
		if(dencrypt == null || dencrypt.length()<this.codeLength){
			return null;
		}
		String text = dencrypt.substring(0,this.codeLength);
		String timestamp = dencrypt.substring(this.codeLength);
		String[] result = new String[2];
		result[0] = text;
		result[1] = timestamp;
		return result;
	}

	public boolean checkChallengerCode(final String code,
			final String encryptCode, final long expirse) {
		if (code == null || encryptCode == null) {
			return false;
		}
		final String recoveredCode = crypt.decrypt(encryptCode);
		if (recoveredCode == null|| recoveredCode.length() < this.codeLength ) {
			return false;
		}
		final String _code = recoveredCode.substring(0, this.codeLength);
		if (!code.equalsIgnoreCase(_code)) {
			return false;
		}
		if (expirse < 0) {
			return true;
		}
		final String lastTime = recoveredCode.substring(this.codeLength);
		long _lastTime = Long.parseLong(lastTime);
		return System.currentTimeMillis() - _lastTime <= expirse;
	}
}
