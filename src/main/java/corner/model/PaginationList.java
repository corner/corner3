/*
 * Copyright 2008 The Fepss Pty Ltd.
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-26
 */
package corner.model;

import org.apache.tapestry5.json.JSONObject;

import java.util.Iterator;

/**
 * provide pagination function list based on ArrayList Object
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PaginationList {
    private Object it;
    private JSONObject options;

    /**
     * construct pagination list by iterator
     * @param it collection object
     **/
    public PaginationList(Object it, JSONObject options){
        this.it = it;
        this.options = options;
    }

    public Object collectionObject() {
        return it;
    }
    public JSONObject options(){
        return this.options;
    }
}
