/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-23
 */
package corner.tapestry.bindings.flash;

import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.Location;


/**
 * flash binding
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class FlashBinding extends AbstractBinding {
     private String expression;
    private FlashFacade facade;

    public FlashBinding(Location location, String description, String expression, FlashFacade facade) {
        super(location);
        this.expression = expression;
        this.facade = facade;
    }
    public Object get() {
        return facade.get(expression);
    }
    @Override
	public boolean isInvariant() {
        return false;
    }
}
