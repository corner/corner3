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
package corner.orm.model;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.tapestry5.json.JSONObject;

import com.google.protobuf.InvalidProtocolBufferException;

import corner.protobuf.ProtocolBuffer;

/**
 * wrapper for pagination proto buffer
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PaginationOptions implements Serializable, ProtocolBuffer {
    private static final long serialVersionUID = 1L;
    private JSONObject parameters;
    private final PaginationProtoBuffer.Pagination.Builder builder = PaginationProtoBuffer.Pagination.newBuilder();

    public int getPage() {
        return builder.getPage();
    }

    public int getPerPage() {
        return builder.getPerPage();
    }

    public long getTotalRecord() {
        return builder.getTotalRecord();
    }

    public void setPage(int value) {
        builder.setPage(value);
    }


    public void setPerPage(int value) {
        builder.setPerPage(value);
    }

    public void setTotalRecord(long value) {
        builder.setTotalRecord(value);
    }

    /**
     * add parameter,delegate to {@link JSONObject#put(String, Object)}
     * @param key parameter name
     * @param value parameter value
     * @see JSONObject#put(String, Object)
     */
    public void addParameter(String key,Object value){
        if(this.parameters==null){
            parameters = new JSONObject();
        }
        parameters.put(key,value);
    }

    /**
     * get parameters
     * @return parameter ,return null if no parameters set
     */
    public JSONObject getParameters(){
        return this.parameters;
    }

    /**
     * @see .ProtocolBuffer#getData()
     */
    public byte[] getData() {
        //clear parameters
        this.builder.clearParameters();
        if(this.parameters!=null){
            Iterator<String> it = parameters.keys().iterator();
            while(it.hasNext()){
                String key = it.next();
                String value = parameters.getString(key);
                PaginationProtoBuffer.Parameter.Builder pBuilder = PaginationProtoBuffer.Parameter.newBuilder();
                pBuilder.setKey(key);
                pBuilder.setValue(value);
                builder.addParameters(pBuilder);
            }
        }
        return this.builder.clone().build().toByteArray();
    }

    /**
     * @see .ProtocolBuffer#mergeData(byte[])
     */
    public void mergeData(byte[] byteData) {
        try {
            this.builder.mergeFrom(byteData);
            Iterator<PaginationProtoBuffer.Parameter> it = this.builder.getParametersList().iterator();
            while(it.hasNext()){
                PaginationProtoBuffer.Parameter parameter = it.next();
                this.addParameter(parameter.getKey(),parameter.getValue());
            }
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }
}
