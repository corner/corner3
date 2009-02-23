/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: JAXBUtil.java 2424 2008-10-31 17:13:57Z d0ng $
 * created at:2008-10-08
 */

package corner.services.config.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * JAXB的工具类,用于加载和保存使用JAXB配置的对象
 * 
 * @author agilewang
 * 
 */
public class JAXBUtil {
	private static final String JAXB_ENCODING_NAME = "jaxb.encoding";
	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * 从一个输入流里加载clazz类型的对象
	 * 
	 * @param <T>
	 * @param in 操作完成后,会被关闭
	 * @param clazz
	 * @return
	 * @throws RuntimeException
	 *             在加载的过程出现异常,将抛出此异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T load(Reader in, Class<T> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller um = context.createUnmarshaller();
			return (T) um.unmarshal(in);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 将object写入到writer输出流中
	 * 
	 * @param object
	 * @param writer 操作完成后,会被关闭
	 * @return
	 * @throws RuntimeExcepiton
	 *             在序列化的过程出现异常将抛出此异常
	 */
	public static void save(Object object, Writer writer) {
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(JAXB_ENCODING_NAME, DEFAULT_ENCODING);
			m.setProperty("jaxb.formatted.output", Boolean.TRUE);
			m.marshal(object, writer);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
