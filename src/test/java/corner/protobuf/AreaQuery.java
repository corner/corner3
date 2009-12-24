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

import java.io.Serializable;

import com.google.protobuf.InvalidProtocolBufferException;

import corner.protobuf.AreaQueryProtocol.AreaQueryBuffer;
import corner.protobuf.AreaQueryProtocol.AreaQueryBuffer.Builder;


/**
 * 用于地区查询的实体类
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.1
 */
public class AreaQuery implements Serializable, ProtocolBuffer {
	private static final long serialVersionUID = 1L;
	private final Builder builder = AreaQueryBuffer.newBuilder();

	public String getQname() {
		return builder.getQname();
	}

	public void setQname(String qname) {
		if (qname == null) {
			this.builder.clearField(AreaQueryBuffer.getDescriptor().findFieldByName("qname"));
		} else {
			this.builder.setQname(qname);
		}
	}

	public String getQcode() {
		return builder.getQcode();
	}

	public void setQcode(String qcode) {
		if (qcode == null) {
			this.builder.clearField(AreaQueryBuffer.getDescriptor().findFieldByName("qcode"));
		} else {
			this.builder.setQcode(qcode);
		}
	}

	/**
	 * @see .ProtocolBuffer#getData()
	 */
	public byte[] getData() {
		return this.builder.clone().build().toByteArray();
	}

	/**
	 * @see .ProtocolBuffer#mergeData(byte[])
	 */
	public void mergeData(byte[] byteData) {
		try {
			this.builder.mergeFrom(byteData);
		} catch (InvalidProtocolBufferException e) {
			throw new RuntimeException(e);
		}
	}

}
