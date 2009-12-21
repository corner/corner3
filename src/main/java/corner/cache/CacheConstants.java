package corner.cache;

/**
 * 常量定义
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheConstants {
	
	/**
	 * 得到namespace的定义
	 * common list namespace
	 */
	public static final String COMMON_LIST_KEY_NAMESPACE_FORMATE = "%s_c_l_ns";
	public static final String COMMON_LIST_STRATEGY = "c_l";
	public static final String ENTITY_CACHE_KEY_FORMAT= "%s{%s}";
	/**
	 * 实体cache的定义key
	 */
	public static String ENTITY_CACHE_NAME="entity";
	/**
	 * 实体namespace缓存区名称
	 */
	public static String ENTITY_NS_CACHE_NAME="ns";
}
