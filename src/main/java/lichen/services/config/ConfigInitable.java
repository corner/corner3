package lichen.services.config;

/**
 * 需要进行初始化的配置
 * 
 * @author dong
 * 
 */
public interface ConfigInitable {
	/**
	 * 
	 * 进行初始作的操作
	 * 
	 * @since 0.0.2
	 */
	public abstract void init();

}