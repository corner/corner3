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
package corner.protobuf;

/**
 * 对Google Protocl buffers 的封装接口
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.1
 */
public interface ProtocolBuffer {

	/**
	 * 取得数据的byte数组
	 * @return 数据
	 */
	public abstract byte[] getData();

	/**
	 * 用data初始化对象的数据
	 * @param data 待初始化的数据
	 */
	public abstract void mergeData(byte[] data);

}