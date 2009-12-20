/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: CacheableAdvice.java 6733 2009-12-07 05:06:15Z jcai $
 * created at:2009-09-22
 */

package corner.cache.services.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.ioc.MethodAdvice;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.PropertyAdapter;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corner.cache.services.Cache;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheableDefinitionParser;
import corner.orm.EntityConstants;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;

/**
 * 针对Cacheable的Advice
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 6733 $
 * @since 0.1
 */
public class CacheableAdvice implements MethodAdvice {

	private Method method;
	private final static Logger logger = LoggerFactory
			.getLogger(CacheableAdvice.class);
	private TypeCoercer coercer;
	private ValueEncoderSource valueEncoderSource;
	private Cache cache;
	private EntityService  entityService;
	private PropertyAccess propertyAccess;
	private CacheableDefinitionParser parser;

	public CacheableAdvice(
			 Method m, TypeCoercer coercer,
			 EntityService entityService, 
			ValueEncoderSource valueEncoderSource,
			PropertyAccess propertyAccess,
			CacheManager cacheManager,
			CacheableDefinitionParser parser) {
		this.method = m;
		this.coercer = coercer;
		this.entityService = entityService;
		this.valueEncoderSource = valueEncoderSource;
        cache = cacheManager.getCache("entity");
//        cache.clear();
        this.propertyAccess = propertyAccess;
        this.parser = parser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void advise(Invocation invocation) {
		try {
			// 先得到缓存的KEY
			String cacheKey = this.parser.parseAsKey(invocation,method);

			// 从缓存中读取
			Object object = cache.get(cacheKey);

			// 如果没有，则产生
			if (object == null) {
				logger.debug("cache object is null,so execute origion method");
				executeAndCache(invocation, cacheKey, cache);
			
			} else {
				restoreObjectFromCache(invocation, object);
			}
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	/**
	 * 从缓存对象中读取对应的实体
	 * 
	 * @param invocation
	 * @param object
	 * @since 0.0.2
	 */
	@SuppressWarnings("unchecked")
	private void restoreObjectFromCache(Invocation invocation, Object object) {
		if (logger.isDebugEnabled()) {
			logger.debug("get cache object [" + object + "]");
		}
		List list = (List) object;
		Class<?> type = invocation.getResultType();
		if (type.isAnnotationPresent(Entity.class)){
			//fetch id property class
			Class<? extends PropertyAdapter> idClass = propertyAccess.getAdapter(type).
				getPropertyAdapter(EntityConstants.ID_PROPERTY_NAME).getClass();
			//convert id value
			Object obj = coercer.coerce(list, idClass);
			//find object 
			obj = entityService.get(type,obj);
			invocation.overrideResult(obj);
		} else if (PaginationList.class.isAssignableFrom(type)) {
			/**
			 * 通过范性类型来得到对应的参数
			 */
			final Class clazz = getEntityClass(method);
			if (logger.isDebugEnabled()) {
				logger.debug("get return type:[" + clazz + "]");
			}
			final Iterator it = list.iterator();

			ValueEncoder encoder = valueEncoderSource
					.getValueEncoder(PaginationOptions.class);
			PaginationOptions options = (PaginationOptions) encoder
					.toValue((String) it.next());

			Iterator wrapperIt = new Iterator() {

				@Override
				public boolean hasNext() {
					return it.hasNext();
				}

				@Override
				public Object next() {
					return entityService.get(clazz, it.next());
				}

				@Override
				public void remove() {
				}
			};
			invocation.overrideResult(new PaginationList(wrapperIt, options));
		}else{
			invocation.overrideResult(coercer.coerce(object,invocation.getResultType()));
		}
	}

	/**
	 * 执行方法本身，然后缓存结果
	 * 
	 * @param invocation
	 *            invocation object
	 * @param cacheKey
	 *            cacheKey
	 * @param cache
	 *            缓存对象
	 * @since 0.0.2
	 */
	@SuppressWarnings("unchecked")
	private void executeAndCache(Invocation invocation, String cacheKey,
			Cache cache) {
		Object object;
		invocation.proceed();
		object = invocation.getResult();
		if(object == null){
			return;
		}
		// 针对结果全部作为list处理
		Iterable iterable = coercer.coerce(object, Iterable.class);
		Iterator it = iterable.iterator();
		List<Object> list = new ArrayList<Object>();
		while (it.hasNext()) {
			Object obj = it.next();
			//is persistence object
			if (obj.getClass().isAnnotationPresent(Entity.class)){
				list.add(propertyAccess.get(obj, EntityConstants.ID_PROPERTY_NAME));
			}else{
				list.add(obj);
			}
		}
		if (object instanceof PaginationList) { // 分页使用对象
			PaginationList pList = (PaginationList) object;
			ValueEncoder<PaginationOptions> encoder = valueEncoderSource
					.getValueEncoder(PaginationOptions.class);
			String options = encoder.toClient(pList.options());
			list.add(0, options);
		}
		//重新读取对象
		restoreObjectFromCache(invocation, list);
		// 放入缓存
		cache.put(cacheKey, list);
	}

	

	//通过分析方法的返回值来得到范性的值,
	//譬如： PaginationList<Member> 得到的结果是Member
	private Class<?> getEntityClass(Method method) {
		Class<?> defaultType = method.getReturnType();
		Type genericType = method.getGenericReturnType();
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			return (Class<?>) pt.getActualTypeArguments()[0];

		}
		return defaultType;
	}


}