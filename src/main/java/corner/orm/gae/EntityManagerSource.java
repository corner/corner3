/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-09-22
 */

package corner.orm.gae;

import javax.persistence.EntityManager;

/**
 * entity manager source
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public interface EntityManagerSource{

	/**
	 * get persistence entity manager
	 * @return entity manager
	 * @since 0.0.2
	 */
	public EntityManager  getEntityManager();
}
