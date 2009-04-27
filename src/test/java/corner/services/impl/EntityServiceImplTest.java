/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-26
 */
package corner.services.impl;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.tapestry5.test.TapestryTestCase;
import org.hibernate.Session;
import org.easymock.EasyMock;

/**
 * test entity service
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class EntityServiceImplTest extends TapestryTestCase {
    private EntityServiceImpl entityService;

    @Test
    public void testConstructCountHQL(){
        Session session = this.newMock(Session.class);
        EasyMock.expect(session.getSessionFactory()).andReturn(null);
        replay();
        
        entityService = new EntityServiceImpl(session);
        executeTest("from A","select count(*) from A");
        executeTest("select * from A","select count(*) from A");
        executeTest("select id from A where A.x=?","select count(*) from A where A.x=?");
        verify();
    }
    private void executeTest(String hql,String chql){
        Assert.assertEquals(entityService.constructCountHQL(hql),
                chql);
    }
}
