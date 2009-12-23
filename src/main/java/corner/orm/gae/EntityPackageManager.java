/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-09-22
 */

package corner.orm.gae;

import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.UsesConfiguration;

/**
 * Entity Package Manager
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
@UsesConfiguration(String.class)
public interface EntityPackageManager {

	public Collection<String> getPackageNames();
}
