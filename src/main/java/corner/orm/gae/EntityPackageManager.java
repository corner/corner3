/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: EntityPackageManager.java 5915 2009-09-22 04:56:54Z jcai $
 * created at:2009-09-22
 */

package corner.orm.gae;

import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.UsesConfiguration;

/**
 * Entity Package Manager
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 5915 $
 * @since 0.1
 */
@UsesConfiguration(String.class)
public interface EntityPackageManager {

	public Collection<String> getPackageNames();
}
