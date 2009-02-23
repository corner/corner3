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
package corner.services.captcha;

/**
 * 验证码的接口
 * 
 * @author dong
 * @version $Revision: 2089 $
 * @since 0.2
 */
public interface CaptchaCode {
	/**
	 * 取得一个验证码
	 * 
	 * @return
	 */
	public String getChallengeCode();

	/**
	 * 解密验证码
	 * 
	 * @param encrypt
	 * @return 一个长度为2的数组,result[0] 经解密的明文;result[1] 有关密码其它信息,如时间
	 */
	public String[] getTextChallengeCode(String encrypt);

	/**
	 * 校验
	 * @param code
	 * @param encryptCode
	 * @param expirse
	 * @return
	 */
	public boolean checkChallengerCode(final String code,
			final String encryptCode, final long expirse);
}
