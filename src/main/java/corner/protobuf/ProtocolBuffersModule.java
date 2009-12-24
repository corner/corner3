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

import java.util.Collection;

import org.apache.commons.codec.binary.Base64;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.services.ClassNameLocator;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.services.ValueEncoderFactory;

import corner.orm.model.PaginationOptions;

/**
 * 配置<a href="http://code.google.com/p/protobuf/">Google protocol buffer</a>的Value Encoder.
 * 提供了通过Object<->String两者之间的转换,方便在查询的时候记录查询条件.
 * 
 * @author dong
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class ProtocolBuffersModule {

	public static void contributeValueEncoderSource(MappedConfiguration<Class, ValueEncoderFactory> configuration, final ClassNameLocator classNameLocator, @Inject
	@Symbol(InternalConstants.TAPESTRY_APP_PACKAGE_PARAM)
	String appRootPackage) {
		final String protoPackage = appRootPackage+".protobuf";
		final Collection<String> protoClasses = classNameLocator.locateClassNames(protoPackage);
		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		for (String className : protoClasses) {
            addProtobufferCoercer(configuration,contextClassLoader,className);
		}
        //add PaginationOptions class type coercer
        addProtobufferCoercer(configuration,contextClassLoader, PaginationOptions.class.getName());
	}
    private static void addProtobufferCoercer(MappedConfiguration<Class, ValueEncoderFactory> configuration,
                                       ClassLoader contextClassLoader,String className){

        try {
            Class protoClass = contextClassLoader.loadClass(className);

            //假如class是ProtocolBuffer的子类，则进行处理
            if (ProtocolBuffer.class.isAssignableFrom(protoClass)) {
                final
                ValueEncoderFactory factory = new ValueEncoderFactory() {
                    @SuppressWarnings("unchecked")
					public ValueEncoder create(Class type) {
                        return new ProtoValueEncoder(type);
                    }
                };
                configuration.add(protoClass,factory);
            }
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
	/**
	 * 针对Protobuf类型的对象进行Encoder
	 * @author dong
	 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
	 * @version $Revision$
	 * @since 0.0.1
	 */
	public static class ProtoValueEncoder<T extends ProtocolBuffer> implements ValueEncoder<T> {
		final Coercion<T, String> proto2string = new Object2ProtoStringCoercion<T>();
		final Coercion<String, T> string2proto;

	
		public ProtoValueEncoder(Class<T> type) {
			string2proto = new ProtoString2ObjectCoercion<T>(type);
		}

		/**
		 * @see org.apache.tapestry5.ValueEncoder#toClient(java.lang.Object)
		 */
		@Override
		public String toClient(T value) {
			return proto2string.coerce(value);
		}

		/**
		 * @see org.apache.tapestry5.ValueEncoder#toValue(java.lang.String)
		 */
		@Override
		public T toValue(String clientValue) {
			return this.string2proto.coerce(clientValue);
		}
	}
	/**
	 * 从String 到Protobuffer
	 * 
	 * @author dong
	 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
	 * @version $Revision$
	 * @since 0.0.1
	 */
	static class ProtoString2ObjectCoercion<T extends ProtocolBuffer> implements Coercion<String, T> {
		private final Class<T> type;

		public ProtoString2ObjectCoercion(Class<T> type) {
			this.type = type;
		}

		/**
		 * @see org.apache.tapestry5.ioc.services.Coercion#coerce(java.lang.Object)
		 */
		@Override
		public T coerce(String input) {
			if (input == null) {
				return null;
			}
			
			T obj = null;
			try {
				obj = this.type.newInstance();
				if("X".equals(input)){
					return obj;
				}
				//先通过base64进行解码
				byte[] _b64 = Base64.decodeBase64(input.getBytes());
				//通过给定的二进制码，然后来反序列化对象
				obj.mergeData(_b64);
				return obj;
			} catch (Throwable te) {
				throw new RuntimeException(te);
			}
		}

	}
	/**
	 * 从对象到Protobuf的string的转换
	 * @author dong
	 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
	 * @version $Revision$
	 * @since 0.0.1
	 */
	static class Object2ProtoStringCoercion<T extends ProtocolBuffer> implements Coercion<T, String> {
		/**
		 * @see org.apache.tapestry5.ioc.services.Coercion#coerce(java.lang.Object)
		 */
		@Override
		public String coerce(T input) {
			if (input == null) {
				return null;
			}
			String str= new String(Base64.encodeBase64(input.getData()));
			if(InternalUtils.isBlank(str)){
				return "X";
			}
			return str;
		}
	}
}
