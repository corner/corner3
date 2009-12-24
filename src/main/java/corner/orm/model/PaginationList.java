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

/**
 * provide pagination function list based on ArrayList Object
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PaginationList <T>{
    private Object it;
    private PaginationOptions options;

    /**
     * construct pagination list by iterator
     * @param it collection object
     **/
    public PaginationList(Object it, PaginationOptions options){
        this.it = it;
        this.options = options;
    }

    public Object collectionObject() {
        return it;
    }
    public PaginationOptions options(){
        return this.options;
    }
    public void overrideCollectionObject(Object it){
    	this.it = it;
    }
}
