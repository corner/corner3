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
package corner.protobuf;

public final class AreaQueryProtocol {
  private AreaQueryProtocol() {}
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static final com.google.protobuf.Descriptors.FileDescriptor
      descriptor = buildDescriptor();
  private static
      com.google.protobuf.Descriptors.FileDescriptor
      buildDescriptor() {
    java.lang.String descriptorData =
      "\n\020orderquery.proto\022\030com.ouriba.eweb.prot" +
      "obuf\"/\n\017AreaQueryBuffer\022\r\n\005qname\030\001 \001(\t\022\r" +
      "\n\005qcode\030\002 \001(\tB-\n\030com.ouriba.eweb.protobu" +
      "fB\021AreaQueryProtocol";
    try {
      return com.google.protobuf.Descriptors.FileDescriptor
        .internalBuildGeneratedFileFrom(descriptorData,
          new com.google.protobuf.Descriptors.FileDescriptor[] {
          });
    } catch (Exception e) {
      throw new RuntimeException(
        "Failed to parse protocol buffer descriptor for " +
        "\"orderquery.proto\".", e);
    }
  }
  
  public static final class AreaQueryBuffer extends
      com.google.protobuf.GeneratedMessage {
    // Use AreaQueryBuffer.newBuilder() to construct.
    private AreaQueryBuffer() {}
    
    private static final AreaQueryBuffer defaultInstance = new AreaQueryBuffer();
    public static AreaQueryBuffer getDefaultInstance() {
      return defaultInstance;
    }
    
    public AreaQueryBuffer getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return AreaQueryProtocol.internal_static_com_ouriba_eweb_protobuf_AreaQueryBuffer_descriptor;
    }
    
    @Override
	protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return AreaQueryProtocol.internal_static_com_ouriba_eweb_protobuf_AreaQueryBuffer_fieldAccessorTable;
    }
    
    // optional string qname = 1;
    private boolean hasQname;
    private java.lang.String qname_ = "";
    public boolean hasQname() { return hasQname; }
    public java.lang.String getQname() { return qname_; }
    
    // optional string qcode = 2;
    private boolean hasQcode;
    private java.lang.String qcode_ = "";
    public boolean hasQcode() { return hasQcode; }
    public java.lang.String getQcode() { return qcode_; }
    
    public static AreaQueryProtocol.AreaQueryBuffer parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static AreaQueryProtocol.AreaQueryBuffer parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static AreaQueryProtocol.AreaQueryBuffer parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static AreaQueryProtocol.AreaQueryBuffer parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static AreaQueryProtocol.AreaQueryBuffer parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static AreaQueryProtocol.AreaQueryBuffer parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static AreaQueryProtocol.AreaQueryBuffer parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static AreaQueryProtocol.AreaQueryBuffer parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return new Builder(); }
    public Builder newBuilderForType() { return new Builder(); }
    public static Builder newBuilder(AreaQueryProtocol.AreaQueryBuffer prototype) {
      return new Builder().mergeFrom(prototype);
    }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      // Construct using com.ouriba.eweb.protobuf.AreaQueryProtocol.AreaQueryBuffer.newBuilder()
      private Builder() {}
      
      AreaQueryProtocol.AreaQueryBuffer result = new AreaQueryProtocol.AreaQueryBuffer();
      
      @Override
	protected AreaQueryProtocol.AreaQueryBuffer internalGetResult() {
        return result;
      }
      
      @Override
	public Builder clear() {
        result = new AreaQueryProtocol.AreaQueryBuffer();
        return this;
      }
      
      @Override
	public Builder clone() {
        return new Builder().mergeFrom(result);
      }
      
      @Override
	public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return AreaQueryProtocol.AreaQueryBuffer.getDescriptor();
      }
      
      public AreaQueryProtocol.AreaQueryBuffer getDefaultInstanceForType() {
        return AreaQueryProtocol.AreaQueryBuffer.getDefaultInstance();
      }
      
      public AreaQueryProtocol.AreaQueryBuffer build() {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result);
        }
        return buildPartial();
      }
      
      private AreaQueryProtocol.AreaQueryBuffer buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public AreaQueryProtocol.AreaQueryBuffer buildPartial() {
        AreaQueryProtocol.AreaQueryBuffer returnMe = result;
        result = null;
        return returnMe;
      }
      
      
      // optional string qname = 1;
      public boolean hasQname() {
        return result.hasQname();
      }
      public java.lang.String getQname() {
        return result.getQname();
      }
      public Builder setQname(java.lang.String value) {
        result.hasQname = true;
        result.qname_ = value;
        return this;
      }
      public Builder clearQname() {
        result.hasQname = false;
        result.qname_ = "";
        return this;
      }
      
      // optional string qcode = 2;
      public boolean hasQcode() {
        return result.hasQcode();
      }
      public java.lang.String getQcode() {
        return result.getQcode();
      }
      public Builder setQcode(java.lang.String value) {
        result.hasQcode = true;
        result.qcode_ = value;
        return this;
      }
      public Builder clearQcode() {
        result.hasQcode = false;
        result.qcode_ = "";
        return this;
      }
    }
  }
  
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_ouriba_eweb_protobuf_AreaQueryBuffer_descriptor =
      getDescriptor().getMessageTypes().get(0);
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_ouriba_eweb_protobuf_AreaQueryBuffer_fieldAccessorTable = new
        com.google.protobuf.GeneratedMessage.FieldAccessorTable(
          internal_static_com_ouriba_eweb_protobuf_AreaQueryBuffer_descriptor,
          new java.lang.String[] { "Qname", "Qcode", },
          AreaQueryProtocol.AreaQueryBuffer.class,
          AreaQueryProtocol.AreaQueryBuffer.Builder.class);
}
