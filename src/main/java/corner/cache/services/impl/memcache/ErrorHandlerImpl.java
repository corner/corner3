/* 
 * Copyright 2009 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.cache.services.impl.memcache;

import java.util.Arrays;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;

import com.meetup.memcached.ErrorHandler;
import com.meetup.memcached.MemCachedClient;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class ErrorHandlerImpl implements ErrorHandler {

	/**
	 * @see com.meetup.memcached.ErrorHandler#handleErrorOnDelete(com.meetup.memcached.MemCachedClient, java.lang.Throwable, java.lang.String)
	 */
	@Override
	public void handleErrorOnDelete(MemCachedClient client, Throwable error,
			String cacheKey) {
		throw new RuntimeException("Delete error for ["+cacheKey+"]",error);
	}

	/**
	 * @see com.meetup.memcached.ErrorHandler#handleErrorOnFlush(com.meetup.memcached.MemCachedClient, java.lang.Throwable)
	 */
	@Override
	public void handleErrorOnFlush(MemCachedClient client, Throwable error) {
		throw new RuntimeException("Flush error",error);
	}

	/**
	 * @see com.meetup.memcached.ErrorHandler#handleErrorOnGet(com.meetup.memcached.MemCachedClient, java.lang.Throwable, java.lang.String)
	 */
	@Override
	public void handleErrorOnGet(MemCachedClient client, Throwable error,
			String cacheKey) {
		throw new RuntimeException("Get error for ["+cacheKey+"]",error);
	}

	/**
	 * @see com.meetup.memcached.ErrorHandler#handleErrorOnGet(com.meetup.memcached.MemCachedClient, java.lang.Throwable, java.lang.String[])
	 */
	@Override
	public void handleErrorOnGet(MemCachedClient client, Throwable error,
			String[] cacheKeys) {
		throw new RuntimeException("Get error for ["+InternalUtils.join(Arrays.asList(cacheKeys))+"]",error);
	}

	/**
	 * @see com.meetup.memcached.ErrorHandler#handleErrorOnInit(com.meetup.memcached.MemCachedClient, java.lang.Throwable)
	 */
	@Override
	public void handleErrorOnInit(MemCachedClient client, Throwable error) {
		throw new RuntimeException("Init error",error);
	}

	/**
	 * @see com.meetup.memcached.ErrorHandler#handleErrorOnSet(com.meetup.memcached.MemCachedClient, java.lang.Throwable, java.lang.String)
	 */
	@Override
	public void handleErrorOnSet(MemCachedClient client, Throwable error,
			String cacheKey) {
		throw new RuntimeException("Set error for ["+cacheKey+"]",error);
	}

	/**
	 * @see com.meetup.memcached.ErrorHandler#handleErrorOnStats(com.meetup.memcached.MemCachedClient, java.lang.Throwable)
	 */
	@Override
	public void handleErrorOnStats(MemCachedClient client, Throwable error) {
		throw new RuntimeException("Stats error",error);
	}

}
