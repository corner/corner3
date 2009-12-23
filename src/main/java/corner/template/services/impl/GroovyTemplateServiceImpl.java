/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-09-22
 */

package corner.template.services.impl;
import groovy.text.SimpleTemplateEngine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.tapestry5.ioc.Resource;
import org.codehaus.groovy.control.CompilationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corner.template.services.TemplateService;


/**
 * Groovy 实现的模板引擎
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class GroovyTemplateServiceImpl implements TemplateService {
	private Logger logger = LoggerFactory.getLogger(GroovyTemplateServiceImpl.class);
	/**
	 * 
	 * @see com.fepss.integration.common.services.TemplateService#output(java.lang.String, java.util.Map)
	 */
	@Override
	public String output(String template, Map<String,Object> parameters) {
		logger.debug("template:{}",template);
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		try {
			return engine.createTemplate(template).make(parameters)
					.toString();
		} catch (CompilationFailedException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String output(InputStream template, Map<String, Object> parameters) {
		StringBuffer buffer = new StringBuffer();
		String line;
		try {
			//针对流实现UTF8编码格式，防止读取内容为乱码。
			BufferedReader reader = new BufferedReader(new InputStreamReader(template,"utf-8"));
			line = reader.readLine(); // 读取第一行
    		while (line != null) { // 如果 line 为空说明读完了
    			buffer.append(line); // 将读到的内容添加到 buffer 中
    			buffer.append("\n"); // 添加换行符
    			line = reader.readLine(); // 读取下一行
    		}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
		return output(buffer.toString(),parameters);
	}

	@Override
	public String output(Resource resource, Map<String, Object> parameters) {
		try {
			return output(resource.openStream(),parameters);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
