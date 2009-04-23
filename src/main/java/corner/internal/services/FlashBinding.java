/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-23
 */
package corner.internal.services;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.Location;
import corner.services.FlashFacade;

import java.lang.annotation.Annotation;

/**
 * flash binding
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class FlashBinding extends AbstractBinding {
    private String description;
    private String expression;
    private FlashFacade facade;

    public FlashBinding(Location location, String description, String expression, FlashFacade facade) {
        super(location);
        this.description = description;
        this.expression = expression;
        this.facade = facade;
    }
    public Object get() {
        return facade.get(expression);
    }
    public boolean isInvariant() {
        return false;
    }
}
