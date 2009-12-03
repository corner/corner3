/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ganshane.net
 * file: $Id$
 * created at:2008-11-3
 */
package corner.asset;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;


/**
 * 根据地址规则类型选择对应的StaticAssetUrlFactory进行处理
 * @author dong
 * @version $Revision$
 * @since 0.0.1
 */
public class StaticAssetUrlFactoryAdapter implements StaticAsseUrlFactory {
	private final Map<String, StaticAsseUrlFactory> typeConfig = new HashMap<String, StaticAsseUrlFactory>();
	private final Logger logger;

	public StaticAssetUrlFactoryAdapter(
			Map<String, StaticAsseUrlFactory> configuration,Logger logger) {
		for (String _key : configuration.keySet()) {
			StaticAsseUrlFactory _factory = configuration.get(_key);
			if (_factory == this) {
				throw new IllegalArgumentException("Can't adapter this");
			}
			this.typeConfig.put(_key, _factory);
		}
		this.logger = logger;
	}

	/**
	 * @see corner.asset.StaticAsseUrlFactory#getUrl(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public String getUrl(String context, String path, String referPath) {
		String _type = null;
		String _path = null;
		StaticAsseUrlFactory _factory = null;
		{
			int i = path.indexOf(":");
			if (i > 0) {
				_type = path.substring(0, i);
				_path = path.substring(i + 1);
			} else {
				_path = path;
			}
			if (_type != null) {
				_factory = this.typeConfig.get(_type);
			} else {
				_factory = this.typeConfig.get("default");
			}
		}
		if (_factory == null) {
			if(logger.isWarnEnabled()){
				logger.warn("Can't find a factory for path [" + path+"]");
			}
		}
		return _factory.getUrl(context, _path, referPath);
	}
}
