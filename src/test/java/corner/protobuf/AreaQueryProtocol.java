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

public final class AreaQueryProtocol {
  private AreaQueryProtocol() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
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
      return corner.protobuf.AreaQueryProtocol.internal_static_corner_model_AreaQueryBuffer_descriptor;
    }
    
    @Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return corner.protobuf.AreaQueryProtocol.internal_static_corner_model_AreaQueryBuffer_fieldAccessorTable;
    }
    
    // optional string qname = 1;
    public static final int QNAME_FIELD_NUMBER = 1;
    private boolean hasQname;
    private java.lang.String qname_ = "";
    public boolean hasQname() { return hasQname; }
    public java.lang.String getQname() { return qname_; }
    
    // optional string qcode = 2;
    public static final int QCODE_FIELD_NUMBER = 2;
    private boolean hasQcode;
    private java.lang.String qcode_ = "";
    public boolean hasQcode() { return hasQcode; }
    public java.lang.String getQcode() { return qcode_; }
    
    @Override
    public final boolean isInitialized() {
      return true;
    }
    
    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (hasQname()) {
        output.writeString(1, getQname());
      }
      if (hasQcode()) {
        output.writeString(2, getQcode());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    @Override
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasQname()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getQname());
      }
      if (hasQcode()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getQcode());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static corner.protobuf.AreaQueryProtocol.AreaQueryBuffer parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return new Builder(); }
    public Builder newBuilderForType() { return new Builder(); }
    public static Builder newBuilder(corner.protobuf.AreaQueryProtocol.AreaQueryBuffer prototype) {
      return new Builder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      // Construct using corner.protobuf.AreaQueryProtocol.AreaQueryBuffer.newBuilder()
      private Builder() {}
      
      corner.protobuf.AreaQueryProtocol.AreaQueryBuffer result = new corner.protobuf.AreaQueryProtocol.AreaQueryBuffer();
      
      @Override
      protected corner.protobuf.AreaQueryProtocol.AreaQueryBuffer internalGetResult() {
        return result;
      }
      
      @Override
      public Builder clear() {
        result = new corner.protobuf.AreaQueryProtocol.AreaQueryBuffer();
        return this;
      }
      
      @Override
      public Builder clone() {
        return new Builder().mergeFrom(result);
      }
      
      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return corner.protobuf.AreaQueryProtocol.AreaQueryBuffer.getDescriptor();
      }
      
      public corner.protobuf.AreaQueryProtocol.AreaQueryBuffer getDefaultInstanceForType() {
        return corner.protobuf.AreaQueryProtocol.AreaQueryBuffer.getDefaultInstance();
      }
      
      public corner.protobuf.AreaQueryProtocol.AreaQueryBuffer build() {
        if (result != null && !isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result);
        }
        return buildPartial();
      }
      
      private corner.protobuf.AreaQueryProtocol.AreaQueryBuffer buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public corner.protobuf.AreaQueryProtocol.AreaQueryBuffer buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");  }
        corner.protobuf.AreaQueryProtocol.AreaQueryBuffer returnMe = result;
        result = null;
        return returnMe;
      }
      
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof corner.protobuf.AreaQueryProtocol.AreaQueryBuffer) {
          return mergeFrom((corner.protobuf.AreaQueryProtocol.AreaQueryBuffer)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(corner.protobuf.AreaQueryProtocol.AreaQueryBuffer other) {
        if (other == corner.protobuf.AreaQueryProtocol.AreaQueryBuffer.getDefaultInstance()) return this;
        if (other.hasQname()) {
          setQname(other.getQname());
        }
        if (other.hasQcode()) {
          setQcode(other.getQcode());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
        return mergeFrom(input,
          com.google.protobuf.ExtensionRegistry.getEmptyRegistry());
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistry extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              setQname(input.readString());
              break;
            }
            case 18: {
              setQcode(input.readString());
              break;
            }
          }
        }
      }
      
      
      // optional string qname = 1;
      public boolean hasQname() {
        return result.hasQname();
      }
      public java.lang.String getQname() {
        return result.getQname();
      }
      public Builder setQname(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
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
        if (value == null) {
    throw new NullPointerException();
  }
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
    
    static {
      corner.protobuf.AreaQueryProtocol.getDescriptor();
    }
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_corner_model_AreaQueryBuffer_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_corner_model_AreaQueryBuffer_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String descriptorData =
      "\n-src/test/java/corner/protobuf/AreaQuer" +
      "y.proto\022\014corner.model\"/\n\017AreaQueryBuffer" +
      "\022\r\n\005qname\030\001 \001(\t\022\r\n\005qcode\030\002 \001(\tB$\n\017corner" +
      ".protobufB\021AreaQueryProtocol";
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_corner_model_AreaQueryBuffer_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_corner_model_AreaQueryBuffer_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_corner_model_AreaQueryBuffer_descriptor,
              new java.lang.String[] { "Qname", "Qcode", },
              corner.protobuf.AreaQueryProtocol.AreaQueryBuffer.class,
              corner.protobuf.AreaQueryProtocol.AreaQueryBuffer.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
}
