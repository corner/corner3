/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: Cacheable.java 5915 2009-09-22 04:56:54Z jcai $
 * created at:2009-09-22
 */

package corner.cache.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import corner.cache.CacheConstants;

/**
 * 缓存的注释类
 * 譬如：缓存某一用户的博客，那么对应的声明如下：
 * <code>
 * @Cacheable(clazz=Blog.class,keyMatcher=xxx.KeyMatcher.class,keyFormats={"uid=%1$s","limit=%2$s,%3$s"})
 * public List<Blog> findBlogs(@CacheKeyParameter User user,@CacheKeyParameter int start,@CacheKeyParameter int offset)
 * </code>
 * 1) clazz 			要缓存的实体类
 * 2) keyMatcher 	针对key是否匹配的策略类，此key是要和实体类有关系，通常是一种类型的缓存一个实例即可
 * 3) keyFormats	缓存的key的定义，可以定义成多个数组，也就是说能够使用散列方式
 * 
 * a) 查找缓存
 *  先读取class=Blog.class,然后读取uid=xxx的缓存，然后再读取limit=xx,xx的缓存。
 * b) 更新缓存
 * 	譬如：增加Blog，那么先通过Blog.class,得到所有和Blog相关的KeyMatcher实例,遍历处理
 * 上述列子中keymatcher，直接通过uid=blog.userId构造一个key，然后删除所有的uid=blog.userId的缓存
 * 
 * 
 *  能够提高缓存的命中率，同时加快了查找速度,更重要的方便了程序的可读性
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 5915 $
 * @since 0.1
 */
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
	/**
	 *  需要缓存的实体类
	 * @return 实体类
	 * @since 3.1
	 */
	public Class<?> clazz() default Object.class;
	
	/**
	 * 针对key是否匹配的策略类,针对一种类型的缓存通常一个实例即可
	 * @return
	 * @since 3.1
	 */
	public String strategy() default CacheConstants.COMMON_LIST_STRATEGY;
	/**
	 * 缓存使用的key.
	 * 这是一个数组key，意味着可以多个key组成，
	 * @return
	 * @since 3.1
	 */
	public String[]  keyFormats() default {};
	
	/**
	 * ttl 时间，单位为秒数
	 * @return
	 * @since 3.1
	 */
	public long ttl() default -1;
}
