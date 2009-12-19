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

/**
 * 缓存的注释类
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
	 * 缓存使用的key.
	 * 这是一个数组key，意味着可以多个key组成，
	 * 譬如：缓存某一用户的博客，那么对应的声明如下：
	 * <code>
	 * @Cacheable(clazz=Blog.class,keyFormats={"uid=%1$s","limit=%2$s,%3$s"})
	 * public List<Blog> findBlogs(@CacheKeyParameter User user,@CacheKeyParameter int start,@CacheKeyParameter int offset)
	 * </code>
	 * 那么在缓存查找的时候，先读取class=Blog.class,然后读取uid=xxx的缓存，然后再读取limit=xx,xx的缓存。
	 * 能够提高缓存的命中率，同时加快了查找速度
	 * @return
	 * @since 3.1
	 */
	public String[]  keyFormats() default "";
}
