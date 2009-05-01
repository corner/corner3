/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-28
 */
package corner.model;

import org.apache.tapestry5.json.JSONObject;
import org.hibernate.Query;

/**
 * by hql query callback
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public interface HQLQueryCallback {
    /**
     * append some query conditions using {@link JSONObject} parameters
     * pls: NOT include 'where'
     * eg: email=? or sex=?
     * @param parameters parameters
     * @return  conditions
     */
    public String appendConditions(JSONObject parameters);

    /**
     * append some hql conditions prepare statement
     * @param query hibernate {@link Query} instance
     */
    public void appendConditionsStatement(Query query);

    /**
     * append some order(asc,desc) by parameters
     * pls: NOT include 'order by'
     * eg: email asc,sex desc
     * @param parameters parameters
     * @return order 
     */
    public String appendOrder(JSONObject parameters);
}
