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
package corner.cache.services.impl;

public final class CacheableDefine {
  private CacheableDefine() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class Definition extends
      com.google.protobuf.GeneratedMessage {
    // Use Definition.newBuilder() to construct.
    private Definition() {}
    
    private static final Definition defaultInstance = new Definition();
    public static Definition getDefaultInstance() {
      return defaultInstance;
    }
    
    public Definition getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return corner.cache.services.impl.CacheableDefine.internal_static_lichen_common_services_impl_Definition_descriptor;
    }
    
    @Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return corner.cache.services.impl.CacheableDefine.internal_static_lichen_common_services_impl_Definition_fieldAccessorTable;
    }
    
    // repeated int32 parameter_index = 2;
    public static final int PARAMETER_INDEX_FIELD_NUMBER = 2;
    private java.util.List<java.lang.Integer> parameterIndex_ =
      java.util.Collections.emptyList();
    public java.util.List<java.lang.Integer> getParameterIndexList() {
      return parameterIndex_;
    }
    public int getParameterIndexCount() { return parameterIndex_.size(); }
    public int getParameterIndex(int index) {
      return parameterIndex_.get(index);
    }
    
    @Override
    public final boolean isInitialized() {
      return true;
    }
    
    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      for (int element : getParameterIndexList()) {
        output.writeInt32(2, element);
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    @Override
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      {
        int dataSize = 0;
        for (int element : getParameterIndexList()) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(element);
        }
        size += dataSize;
        size += 1 * getParameterIndexList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static corner.cache.services.impl.CacheableDefine.Definition parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static corner.cache.services.impl.CacheableDefine.Definition parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static corner.cache.services.impl.CacheableDefine.Definition parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static corner.cache.services.impl.CacheableDefine.Definition parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static corner.cache.services.impl.CacheableDefine.Definition parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static corner.cache.services.impl.CacheableDefine.Definition parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static corner.cache.services.impl.CacheableDefine.Definition parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static corner.cache.services.impl.CacheableDefine.Definition parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static corner.cache.services.impl.CacheableDefine.Definition parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static corner.cache.services.impl.CacheableDefine.Definition parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return new Builder(); }
    public Builder newBuilderForType() { return new Builder(); }
    public static Builder newBuilder(corner.cache.services.impl.CacheableDefine.Definition prototype) {
      return new Builder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      // Construct using corner.cache.services.impl.CacheableDefine.Definition.newBuilder()
      private Builder() {}
      
      corner.cache.services.impl.CacheableDefine.Definition result = new corner.cache.services.impl.CacheableDefine.Definition();
      
      @Override
      protected corner.cache.services.impl.CacheableDefine.Definition internalGetResult() {
        return result;
      }
      
      @Override
      public Builder clear() {
        result = new corner.cache.services.impl.CacheableDefine.Definition();
        return this;
      }
      
      @Override
      public Builder clone() {
        return new Builder().mergeFrom(result);
      }
      
      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return corner.cache.services.impl.CacheableDefine.Definition.getDescriptor();
      }
      
      public corner.cache.services.impl.CacheableDefine.Definition getDefaultInstanceForType() {
        return corner.cache.services.impl.CacheableDefine.Definition.getDefaultInstance();
      }
      
      public corner.cache.services.impl.CacheableDefine.Definition build() {
        if (result != null && !isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result);
        }
        return buildPartial();
      }
      
      private corner.cache.services.impl.CacheableDefine.Definition buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public corner.cache.services.impl.CacheableDefine.Definition buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");  }
        if (result.parameterIndex_ != java.util.Collections.EMPTY_LIST) {
          result.parameterIndex_ =
            java.util.Collections.unmodifiableList(result.parameterIndex_);
        }
        corner.cache.services.impl.CacheableDefine.Definition returnMe = result;
        result = null;
        return returnMe;
      }
      
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof corner.cache.services.impl.CacheableDefine.Definition) {
          return mergeFrom((corner.cache.services.impl.CacheableDefine.Definition)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(corner.cache.services.impl.CacheableDefine.Definition other) {
        if (other == corner.cache.services.impl.CacheableDefine.Definition.getDefaultInstance()) return this;
        if (!other.parameterIndex_.isEmpty()) {
          if (result.parameterIndex_.isEmpty()) {
            result.parameterIndex_ = new java.util.ArrayList<java.lang.Integer>();
          }
          result.parameterIndex_.addAll(other.parameterIndex_);
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
            case 16: {
              addParameterIndex(input.readInt32());
              break;
            }
          }
        }
      }
      
      
      // repeated int32 parameter_index = 2;
      public java.util.List<java.lang.Integer> getParameterIndexList() {
        return java.util.Collections.unmodifiableList(result.parameterIndex_);
      }
      public int getParameterIndexCount() {
        return result.getParameterIndexCount();
      }
      public int getParameterIndex(int index) {
        return result.getParameterIndex(index);
      }
      public Builder setParameterIndex(int index, int value) {
        result.parameterIndex_.set(index, value);
        return this;
      }
      public Builder addParameterIndex(int value) {
        if (result.parameterIndex_.isEmpty()) {
          result.parameterIndex_ = new java.util.ArrayList<java.lang.Integer>();
        }
        result.parameterIndex_.add(value);
        return this;
      }
      public Builder addAllParameterIndex(
          java.lang.Iterable<? extends java.lang.Integer> values) {
        if (result.parameterIndex_.isEmpty()) {
          result.parameterIndex_ = new java.util.ArrayList<java.lang.Integer>();
        }
        super.addAll(values, result.parameterIndex_);
        return this;
      }
      public Builder clearParameterIndex() {
        result.parameterIndex_ = java.util.Collections.emptyList();
        return this;
      }
    }
    
    static {
      corner.cache.services.impl.CacheableDefine.getDescriptor();
    }
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_lichen_common_services_impl_Definition_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_lichen_common_services_impl_Definition_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String descriptorData =
      "\n>src/main/java/corner/cache/services/im" +
      "pl/CacheableDefine.proto\022\033lichen.common." +
      "services.impl\"%\n\nDefinition\022\027\n\017parameter" +
      "_index\030\002 \003(\005B/\n\032corner.cache.services.im" +
      "plB\017CacheableDefineH\001";
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_lichen_common_services_impl_Definition_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_lichen_common_services_impl_Definition_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_lichen_common_services_impl_Definition_descriptor,
              new java.lang.String[] { "ParameterIndex", },
              corner.cache.services.impl.CacheableDefine.Definition.class,
              corner.cache.services.impl.CacheableDefine.Definition.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
}
