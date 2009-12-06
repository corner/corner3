/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ganshane.net
 * file: $Id: ConfigurationSource.java 2424 2008-10-31 17:13:57Z d0ng $
 * created at:2008-10-08
 */

package corner.config;

/**
 * 得到服务配置的工厂类
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 2424 $
 * @since 0.0.1
 */
public interface ConfigurationSource {

	/**
	 * 通过给定的服务配置类来得到对应的服务配置类.
	 * @param <T> 服务配置.
	 * @param clazz 配置类的class
	 * @return 带有数据的配置实例.
	 */
	public <T> T getServiceConfig(Class<T> clazz);
}
