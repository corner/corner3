/* 
 * Copyright 2008 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.payment.services.impl.view;

import java.util.Iterator;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.json.JSONObject;

import corner.payment.services.ViewHelper;

/**
 * 抽象的试图帮助类
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 2139 $
 * @since 0.0.2
 */
public abstract class AbstractViewHelper implements ViewHelper {

	private JSONObject mappings =new JSONObject();
	
	private MarkupWriter writer;

	private JSONObject options;

	
	public AbstractViewHelper(MarkupWriter writer,JSONObject options){
		this.writer = writer;
		this.options = options;
		//加入一些默认的mapping
		
	}
	/**
	 * 得到客户端传入值
	 * @return
	 * @since 0.0.2
	 */
	protected JSONObject getOptions(){
		return this.options;
	}
	protected String getOptionValue(String key){
		try{
			return this.options.getString(key);
		}catch(RuntimeException e){
			return null;
		}
		
	}
	/**
	 * 向前端输出一个隐藏字段
	 * @param fieldName 字段的名称。
	 * @param fieldValue 字段对应的值。
	 * @since 0.0.2
	 */
	protected void addField(String fieldName,String fieldValue){
		String keyMapped = getFieldNameMapped(fieldName);
		
		this.getWriter().element("input","type","hidden", "name",keyMapped,"value",fieldValue);
		this.getWriter().end();
		getWriter().write("\n");
	}
	protected  MarkupWriter getWriter(){
		return this.writer;
	}
	@Override
	public JSONObject getMapping() {
		return mappings;
	}
	/**
	 * 
	 * @see corner.payment.services.ViewHelper#getFieldNameMapped(java.lang.String)
	 */
	@Override
	public String getFieldNameMapped(String srcKey) {
		try{
			return this.getMapping().getString(srcKey);
		}catch(RuntimeException e){
			return srcKey;
		}
		
	}
	/**
	 * 
	 * @see corner.payment.services.ViewHelper#outputFieldInformation()
	 */
	@Override
	public void outputFieldInformation() {
		if(options == null){
			return;
		}
		for(Iterator<String> it = options.keys().iterator();it.hasNext();){
			String key = it.next();
			this.addField(key,options.getString(key));
		}
	}
}
