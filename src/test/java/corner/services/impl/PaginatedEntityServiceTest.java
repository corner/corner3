/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-28
 */
package corner.services.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PaginatedEntityServiceTest {
    @Test
    public void testAppendOrder(){
        PaginatedEntityService service = new PaginatedEntityService(null,null);
        String order=" order by email";
        StringBuffer sb = new StringBuffer();
        service.appendOrder(sb,order);
        Assert.assertEquals(sb.toString()," order by email");
    }
}
