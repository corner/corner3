package corner.cache.services;

import java.lang.reflect.Method;

import org.apache.tapestry5.ioc.Invocation;


/**
 * cache definition parser
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public interface CacheableDefinitionParser {

	public abstract String parseAsKey(Invocation invocation, Method method);

}