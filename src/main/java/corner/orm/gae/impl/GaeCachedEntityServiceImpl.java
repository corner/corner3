/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-12-18
 */
package corner.orm.gae.impl;

import java.util.Iterator;

import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaTemplate;

import corner.cache.annotations.Memcache;
import corner.cache.services.Cache;
import corner.cache.services.CacheManager;
import corner.orm.EntityConstants;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;
import corner.orm.services.EntityService;

/**
 * 实现EntityService，加入缓存策略
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class GaeCachedEntityServiceImpl extends JpaEntityServiceImpl implements
		EntityService {

	private Cache<String, Object> cache;
	private String ENTITY_CACHE_KEY_FORMAT= "%s{%s}";
	private PropertyAccess propertyAccess;
	private Logger logger = LoggerFactory.getLogger(GaeCachedEntityServiceImpl.class);
	private TypeCoercer typeCoercer;

	public GaeCachedEntityServiceImpl(JpaTemplate jpaTemplate,
			TypeCoercer typeCoercer, PropertyAccess propertyAccess,@Memcache CacheManager cacheManager) {
		super(jpaTemplate, typeCoercer, propertyAccess);
		this.cache = cacheManager.getCache("entity");
		this.propertyAccess = propertyAccess;
		this.typeCoercer = typeCoercer;
	}

	
	/**
	 * @see corner.orm.gae.impl.JpaEntityServiceImpl#delete(java.lang.Object)
	 */
	@Override
	public <T> void delete(T entity) throws DataAccessException {
		super.delete(entity);
		//删除缓存
		String cacheKey = makeEntityCacheKey(entity);
		if(cacheKey!=null){	
			if(logger.isDebugEnabled()){
				logger.debug("delete cache object ,key:{}",cacheKey);
			}
			cache.remove(cacheKey);
		}
//		CacheEvent event = new CacheEvent(getEntityClass(entity),entity,Operation.DELETE);
		
	}

	/**
	 * @see corner.orm.gae.impl.JpaEntityServiceImpl#get(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T get(Class<T> entityClass, Object id)
			throws DataAccessException {
		if(id == null){
			return null;
		}
		T obj =null;
		//先从缓存中读取
		String cacheKey = makeEntityCacheKey(entityClass,id);
		obj = (T) cache.get(cacheKey);
		if(logger.isDebugEnabled()){
			logger.debug("read cache object{} ,key:{}",String.valueOf(obj),cacheKey);
		}
		if(obj == null){ // 缓存中没有
			//从数据库查询出来的值
			obj = super.get(entityClass, id);
			if(obj !=null){
				cache.put(cacheKey, obj); //放入缓存中
			}
		}
		return obj;
	}

	/**
	 * @see corner.orm.gae.impl.JpaEntityServiceImpl#save(java.lang.Object)
	 */
	@Override
	public <T>void save(T entity) throws DataAccessException {
		super.save(entity);
	}


	/**
	 * @see corner.orm.gae.impl.JpaEntityServiceImpl#update(java.lang.Object)
	 */
	@Override
	public <T>void update(T entity) throws DataAccessException {
		super.update(entity);
		updateEntityCache(entity);
	}
	//根据entity实例对象，更新缓存对象
	private void updateEntityCache(Object entity){
		String cacheKey = makeEntityCacheKey(entity);
		if(cacheKey != null&&entity!=null){
			//放入缓存
    		if(logger.isDebugEnabled()){
    			logger.debug("put cache object{} ,key:{}",String.valueOf(entity),cacheKey);
    		}
			cache.put(cacheKey, entity);
		}
	}
	private String makeEntityCacheKey(Object entity){
		Object id = propertyAccess.get(entity, EntityConstants.ID_PROPERTY_NAME);
		if(id!=null){
			return makeEntityCacheKey(getEntityClass(entity),id);
		}
		logger.warn("fail to make cache key for entity {}",entity);
		return null;
	}
	private String makeEntityCacheKey(Class<?> entityClass,Object id){
		if(id!=null){
			return String.format(ENTITY_CACHE_KEY_FORMAT, entityClass.getName(),String.valueOf(id));
		}
		logger.warn("fail to make cache key ,beacause id is null!");
		return null;
	}

	/**
	 * @see corner.orm.gae.impl.JpaEntityServiceImpl#find(java.lang.Class, java.lang.Object, java.lang.String, int, int)
	 */
	@Override
	public<T> Iterator<T> find(final Class<T> persistClass, Object conditions,
			String order, int start, int offset) {
		//父类返回的结果为ID的集合
		final Iterator ids =  super.find(persistClass, conditions, order,start,offset);
		return new Iterator(){
			@Override
			public boolean hasNext() {
				return ids.hasNext();
			}
			@Override
			public Object next() {
				Object id = ids.next();
				return get(persistClass,id);
			}
			@Override
			public void remove() {
				ids.remove();
			}};
	}


	/**
	 *
	 * @see corner.orm.gae.impl.JpaEntityServiceImpl#paginate(java.lang.Class, java.lang.Object, java.lang.String, corner.orm.model.PaginationOptions)
	 */
	@Override
	public <T>PaginationList<T> paginate(final Class<T> persistClass, Object conditions,
			String order, PaginationOptions options) {
		PaginationList paginationList = super.paginate(persistClass, conditions, order, options);
		Object collectionObject = paginationList.collectionObject();
		final Iterator it = typeCoercer.coerce(collectionObject, Iterator.class);
		paginationList.overrideCollectionObject(new Iterator(){
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}
			@Override
			public Object next() {
				Object id = it.next();
				return get(persistClass,id);
			}

			@Override
			public void remove() {
				it.remove();
			}});
		return paginationList;
	}
}
