/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: EntityManagerSource.java 5915 2009-09-22 04:56:54Z jcai $
 * created at:2009-09-22
 */

package corner.orm.gae;

import javax.persistence.EntityManager;

/**
 * entity manager source
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 5915 $
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
