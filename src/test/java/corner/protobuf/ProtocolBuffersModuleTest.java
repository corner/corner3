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

import junit.framework.TestCase;

import org.apache.tapestry5.ioc.services.Coercion;
import org.junit.Test;

import corner.protobuf.ProtocolBuffersModule.Object2ProtoStringCoercion;
import corner.protobuf.ProtocolBuffersModule.ProtoString2ObjectCoercion;


public class ProtocolBuffersModuleTest extends TestCase{
	@Test
	public void test_obj2str(){
		final Coercion<ProtocolBuffer, String> proto2string = new Object2ProtoStringCoercion<ProtocolBuffer>();
		AreaQuery query=new AreaQuery();
		query.setQname("qname");
		query.setQcode("1");
		String r = proto2string.coerce(query);
		assertNotNull(r);
		assertEquals(r,"CgVxbmFtZRIBMQ==");
		
	}
	@Test
	public void test_str2obj(){
		String str = "CgVxbmFtZRIBMQ==";
		ProtoString2ObjectCoercion<AreaQuery> string2proto = new ProtoString2ObjectCoercion<AreaQuery>(AreaQuery.class);
		AreaQuery query =string2proto.coerce(str);
		assertEquals("qname",query.getQname());
	}
	@Test
	public void test_obj2str_null(){
		final Coercion<ProtocolBuffer, String> proto2string = new Object2ProtoStringCoercion<ProtocolBuffer>();
		AreaQuery query=new AreaQuery();
		String r = proto2string.coerce(query);
		assertNotNull(r);
		assertEquals(r,"X");
	}
}
